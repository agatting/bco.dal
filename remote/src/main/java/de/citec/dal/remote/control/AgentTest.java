/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.dal.remote.control;

import de.citec.jul.exception.CouldNotPerformException;
import de.citec.jul.exception.InstantiationException;
import java.util.ArrayList;
import java.util.List;
import rst.homeautomation.state.PowerStateType;
import rst.vision.HSVColorType.HSVColor;

/**
 *
 * @author Divine <DivineThreepwood@gmail.com>
 */
public class AgentTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InstantiationException, InterruptedException, CouldNotPerformException {

        List<HSVColor> colorList = new ArrayList<>();
        colorList.add(HSVColor.newBuilder().setHue(259).setSaturation(100).setValue(100).build());
        colorList.add(HSVColor.newBuilder().setHue(290).setSaturation(100).setValue(100).build());
        colorList.add(HSVColor.newBuilder().setHue(120).setSaturation(100).setValue(100).build());
//        ColorControl colorControl = new ColorControl("Chillerstrasse", colorList);

//        colorControl.activate();

        PowerControl powerControl = new PowerControl("Chillerstrasse", PowerStateType.PowerState.State.OFF);
        powerControl.activate();

    }

}