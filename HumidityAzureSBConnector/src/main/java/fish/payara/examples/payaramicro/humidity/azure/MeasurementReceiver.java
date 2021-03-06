/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2017 Payara Foundation and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://github.com/payara/Payara/blob/master/LICENSE.txt
 * See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * The Payara Foundation designates this particular file as subject to the "Classpath"
 * exception as provided by the Payara Foundation in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package fish.payara.examples.payaramicro.humidity.azure;

import com.microsoft.windowsazure.services.servicebus.models.BrokeredMessage;
import fish.payara.cloud.connectors.azuresb.api.AzureSBConnection;
import fish.payara.cloud.connectors.azuresb.api.AzureSBConnectionFactory;
import fish.payara.examples.payaramicro.humidity.HumidityMeasurement;
import fish.payara.micro.cdi.ClusteredCDIEventBus;
import fish.payara.micro.cdi.Inbound;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.resource.ConnectionFactoryDefinition;
import javax.resource.spi.TransactionSupport;

/**
 *
 * @author Steve Millidge (Payara Foundation)
 */
@Singleton
@Startup
@ConnectionFactoryDefinition(name = "java:comp/env/AzureSBConnectionFactory",
        description = "Azure SB Conn Factory",
        interfaceName = "fish.payara.cloud.connectors.azuresb.api.AzureSBConnectionFactory",
        resourceAdapter = "AzureSBRAR-0.1.0-SNAPSHOT",
        minPoolSize = 2, maxPoolSize = 2,
        transactionSupport = TransactionSupport.TransactionSupportLevel.NoTransaction,
        properties = {"nameSpace=payara",
            "sasKeyName=RootManageSharedAccessKey",
            "sasKey=${ENV=sasKey}"
        })
public class MeasurementReceiver {

    @Resource(lookup = "java:comp/env/AzureSBConnectionFactory")
    AzureSBConnectionFactory factory;

    @Inject
    ClusteredCDIEventBus eventBus;

    @PostConstruct
    public void postConstruct() {
        eventBus.initialize();
    }

    public void receiveMeasurement(@Observes @Inbound HumidityMeasurement measurement) {
        try (AzureSBConnection connection = factory.getConnection(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // Serialize the measurement to a byte array.
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(measurement);
            connection.sendMessage("humidityq", new BrokeredMessage(baos.toByteArray()));
            System.out.println("Sent " + measurement + " to humidityq on Azure Service Bus");
        } catch (Exception e) {
        }
    }
}
