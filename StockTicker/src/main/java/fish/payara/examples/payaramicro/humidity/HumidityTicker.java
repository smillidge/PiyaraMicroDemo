/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.examples.payaramicro.humidity;

import fish.payara.micro.cdi.Outbound;
import fish.payara.piyara.sensehat.impl.SenseHatImpl;
import fish.payara.piyara.sensehat.sensors.HTS221;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Schedule;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 *
 * @author Steve Millidge (Payara Foundation)
 */
@Singleton
public class HumidityTicker {
    
    private static final int I2C_BUS = 1;
    private static final int I2C_ADDRESS_SIZE = 7;
    private static final int I2C_FREQUENCY = -1;
    private static final int I2C_ACC_ADDRESS = 0x6A;
    private static final int I2C_MAG_ADDRESS = 0x1C;
    private static final int I2C_PRE_ADDRESS = 0x5C;
    private static final int I2C_HUM_ADDRESS = 0x5F;
    
    @Inject
    @Outbound(loopBack = true)
    Event<HumidityMeasurement> humidityEvents;
    
    private HTS221 sensor;
    
    @PostConstruct
    public void postConstruct() {
        SenseHatImpl senseHat = new SenseHatImpl();
        sensor = senseHat.getHumiditySensor(I2C_BUS, I2C_HUM_ADDRESS, I2C_ADDRESS_SIZE,I2C_FREQUENCY);
        sensor.initDevice();
    }

    @Schedule(hour = "*", minute="*", second = "*/1", persistent = false)
    public void measureHumidity() {

        HumidityMeasurement humidity = new HumidityMeasurement(sensor.getHumidity());
        System.out.println(humidity);
        humidityEvents.fire(humidity);
        
    }

}
