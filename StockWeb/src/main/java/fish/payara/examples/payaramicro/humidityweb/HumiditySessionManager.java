/*

 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

 Copyright (c) 2015 C2B2 Consulting Limited. All rights reserved.

 The contents of this file are subject to the terms of the Common Development
 and Distribution License("CDDL") (collectively, the "License").  You
 may not use this file except in compliance with the License.  You can
 obtain a copy of the License at
 https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 or packager/legal/LICENSE.txt.  See the License for the specific
 language governing permissions and limitations under the License.

 When distributing the software, include this License Header Notice in each
 file and include the License file at packager/legal/LICENSE.txt.
 */
package fish.payara.examples.payaramicro.humidityweb;

import fish.payara.examples.payaramicro.humidity.HumidityMeasurement;
import fish.payara.micro.cdi.Inbound;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.websocket.Session;

/**
 *
 * @author steve
 */
@ApplicationScoped
public class HumiditySessionManager {
    
    private HashSet<Session> sessions;
    
    @PostConstruct 
    public void postConstruct() {
       sessions = new HashSet<>();
    }
    
    void registerSession(Session session) {
        sessions.add(session);
    }
    
    void deregisterSession(Session session) {
        sessions.remove(session);
    }
    
    public void observer(@Observes @Inbound HumidityMeasurement stock) {
        try {
            for (Session session : sessions) {
                 System.out.println("Recieved " + stock.toString() + " writing to " + session.getId());
                session.getBasicRemote().sendText(stock.toString());                
            }
        } catch (IOException ex) {
            Logger.getLogger(HumidityPush.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
