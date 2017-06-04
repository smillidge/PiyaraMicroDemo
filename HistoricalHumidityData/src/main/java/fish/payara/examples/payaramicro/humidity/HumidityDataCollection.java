/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.examples.payaramicro.humidity;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Steve Millidge <Payara Services Limited>
 */
public class HumidityDataCollection implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private final LinkedList<HumidityMeasurement> measurements;

    public HumidityDataCollection() {
        measurements = new LinkedList<>();
    }
    
    public void addMeasurement(HumidityMeasurement measurement) {
        measurements.add(measurement);
    }

    public LinkedList<HumidityMeasurement> getMeasurements() {
        return measurements;
    }

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer();
        buff.append("{\"measurements\":[");
        for (HumidityMeasurement measurement : measurements) {
            buff.append(measurement.toString());
            buff.append(",");
        }
        buff.deleteCharAt(buff.length()-1);
        buff.append("]}");
        return buff.toString();
    }
    
    
    
}
