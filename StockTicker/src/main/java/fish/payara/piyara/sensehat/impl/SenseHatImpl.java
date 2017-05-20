/*******************************************************************************
 * Copyright (c) 2011, 2016 Eurotech and/or its affiliates
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech
 *******************************************************************************/
package fish.payara.piyara.sensehat.impl;

import fish.payara.piyara.sensehat.SenseHat;
import fish.payara.piyara.sensehat.ledmatrix.FrameBuffer;
import fish.payara.piyara.sensehat.sensors.HTS221;
import fish.payara.piyara.sensehat.sensors.LPS25H;
import fish.payara.piyara.sensehat.sensors.LSM9DS1;
import fish.payara.piyara.sensehat.joystick.Joystick;
import java.util.logging.Logger;

public class SenseHatImpl implements SenseHat {

    private static final Logger s_logger = Logger.getLogger(SenseHatImpl.class.getName());

    protected void activate() {
        s_logger.info("Activate SenseHat Service.");
    }

    protected void deactivate() {
        s_logger.info("Deactivate SenseHat Service.");
        FrameBuffer.closeFrameBuffer();
        Joystick.closeJoystick();
        HTS221.closeDevice();
        LPS25H.closeDevice();
        LSM9DS1.closeDevice();
    }

    @Override
    public FrameBuffer getFrameBuffer() {
        return FrameBuffer.getFrameBuffer();
    }

    @Override
    public Joystick getJoystick() {
        return Joystick.getJoystick();
    }

    @Override
    public HTS221 getHumiditySensor(int bus, int address, int addressSize, int frequency) {
        return HTS221.getHumiditySensor(bus, address, addressSize, frequency);
    }

    @Override
    public LPS25H getPressureSensor(int bus, int address, int addressSize, int frequency) {
        return LPS25H.getPressureSensor(bus, address, addressSize, frequency);
    }

    @Override
    public LSM9DS1 getIMUSensor(int bus, int accAddress, int magAddress, int addressSize, int frequency) {
        return LSM9DS1.getIMUSensor(bus, accAddress, magAddress, addressSize, frequency);
    }

}
