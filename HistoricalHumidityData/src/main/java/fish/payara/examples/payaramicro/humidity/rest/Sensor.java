/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.examples.payaramicro.humidity.rest;

import fish.payara.cdi.jsr107.impl.NamedCache;
import fish.payara.examples.payaramicro.humidity.HumidityDataCollection;
import javax.cache.Cache;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Steve Millidge <Payara Services Limited>
 */
@Path("sensors")
@RequestScoped
public class Sensor {

    @Context
    private UriInfo context;
    
    @Inject
    @NamedCache(cacheName = "HumidityGrid")
    Cache cache;

    /**
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@QueryParam("sensor")String sensorName ) {
        return ((HumidityDataCollection) cache.get(sensorName)).toString();
        
    }

}
