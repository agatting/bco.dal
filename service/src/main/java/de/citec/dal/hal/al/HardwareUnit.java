/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.dal.hal.al;

import de.citec.dal.data.Location;

/**
 *
 * @author Divine <DivineThreepwood@gmail.com>
 */
public interface HardwareUnit {

    public String getId();

    public Location getLocation();

    public String getHardware_id();

    public String getInstance_id();
}
