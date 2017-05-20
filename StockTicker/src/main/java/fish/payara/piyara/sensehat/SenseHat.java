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
package fish.payara.piyara.sensehat;

import fish.payara.piyara.sensehat.ledmatrix.FrameBuffer;
import fish.payara.piyara.sensehat.sensors.HTS221;
import fish.payara.piyara.sensehat.sensors.LPS25H;
import fish.payara.piyara.sensehat.sensors.LSM9DS1;
import fish.payara.piyara.sensehat.joystick.Joystick;

public interface SenseHat {

    public FrameBuffer getFrameBuffer();

    public Joystick getJoystick();

    public HTS221 getHumiditySensor(int bus, int address, int addressSize, int frequency);

    public LPS25H getPressureSensor(int bus, int address, int addressSize, int frequency);

    public LSM9DS1 getIMUSensor(int bus, int accAddress, int magAddress, int addressSize, int frequency);

}
