/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2016 Payara Foundation and/or its affiliates. All rights reserved.
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
package fish.payara.piyara.sensehat.ledmatrix;

import fish.payara.piyara.sensehat.impl.SenseHatImpl;
import fish.payara.piyara.sensehat.sensors.HTS221;
import fish.payara.piyara.sensehat.sensors.LPS25H;
import fish.payara.piyara.sensehat.sensors.LSM9DS1;

/**
 *
 * @author steve
 */
public class Test {
    
    private static final int I2C_BUS = 1;
    private static final int I2C_ADDRESS_SIZE = 7;
    private static final int I2C_FREQUENCY = -1;
    private static final int I2C_ACC_ADDRESS = 0x6A;
    private static final int I2C_MAG_ADDRESS = 0x1C;
    private static final int I2C_PRE_ADDRESS = 0x5C;
    private static final int I2C_HUM_ADDRESS = 0x5F;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello World");
        FrameBuffer buff = FrameBuffer.getFrameBuffer();
                    
        SenseHatImpl senseHat = new SenseHatImpl();
        HTS221 sensor = senseHat.getHumiditySensor(I2C_BUS, I2C_HUM_ADDRESS, I2C_ADDRESS_SIZE,I2C_FREQUENCY);
        LPS25H pressure = senseHat.getPressureSensor(I2C_BUS, I2C_PRE_ADDRESS, I2C_ADDRESS_SIZE,I2C_FREQUENCY);
        LSM9DS1 IMUSensor = senseHat.getIMUSensor(I2C_BUS, I2C_ACC_ADDRESS, I2C_MAG_ADDRESS, I2C_ADDRESS_SIZE,I2C_FREQUENCY);
        pressure.initDevice();
        sensor.initDevice();
        IMUSensor.initDevice(true, true, true);
        
        while (true) {
            buff.clearFrameBuffer();
            buff.setRotation(270);
            /*buff.showLetter("3", Colors.PAYARA_ORANGE, Colors.PAYARA_BLUE);
            Thread.currentThread().sleep(500);
            buff.showLetter("2", Colors.PAYARA_ORANGE, Colors.PAYARA_BLUE);
            Thread.currentThread().sleep(500);
            buff.showLetter("1", Colors.PAYARA_ORANGE, Colors.PAYARA_BLUE);
            Thread.currentThread().sleep(500);

            buff.showMessage("Hello Payara Micro", Colors.PAYARA_ORANGE, Colors.PAYARA_BLUE, 70);
            buff.setRotation(180);
            buff.showPayara();
            Thread.sleep(3000);*/

            
            System.out.println(pressure.getTemperature());
            Thread.currentThread().sleep(500);

        }

    }

}
