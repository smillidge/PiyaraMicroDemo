/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.examples.payaramicro.humidity.grid.loader;

import fish.payara.cdi.jsr107.impl.NamedCache;
import fish.payara.examples.payaramicro.humidity.HumidityDataCollection;
import fish.payara.examples.payaramicro.humidity.HumidityMeasurement;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Steve Millidge <Payara Services Limited>
 */
@Stateless
@LocalBean
public class StorageBean {
    
    
    @Inject
    @NamedCache(cacheName = "HumidityGrid")
    Cache humidityGrid;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void storeMeasurement(HumidityMeasurement hm) throws NamingException{
            HumidityDataCollection measurements = (HumidityDataCollection) humidityGrid.get(hm.getSensorName());

            if (measurements == null) {
                measurements = new HumidityDataCollection();
            }
            measurements.addMeasurement(hm);
            humidityGrid.put(hm.getSensorName(), measurements);
            System.out.println("Stored " + hm);
    }
}
