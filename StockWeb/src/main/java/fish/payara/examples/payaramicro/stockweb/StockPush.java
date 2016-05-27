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
package fish.payara.examples.payaramicro.stockweb;

import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author steve
 */
@ServerEndpoint("/graph")
public class StockPush {

    private static final long serialVersionUID = 1L;
    
    @Inject
    StockSessionManager sessionManager;
    
    private Session mySession;

    
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Opened Session" + session.getId());
        mySession = session;
        sessionManager.registerSession(session);
    }
    
    @OnClose
    public void onClose(Session session) {
        System.out.println("Closed Session" + session.getId());
        sessionManager.deregisterSession(session);
    }
    

    @OnMessage
    public String onMessage(String message, Session session) {
        return null;
    }


    @OnError
    public void onError(Throwable t) {
        System.out.println("Error ");
        t.printStackTrace();
        sessionManager.deregisterSession(mySession);
    }
    
      
}
