/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.citec.dal;

import de.citec.dal.data.Location;
import de.citec.dal.exception.RSBBindingException;
import de.citec.dal.hal.devices.fibaro.F_MotionSensorController;
import de.citec.dal.hal.devices.gira.GI_5133Controller;
import de.citec.dal.hal.devices.gira.GI_5142Controller;
import de.citec.dal.hal.devices.hager.HA_TYA606EController;
import de.citec.dal.hal.devices.homematic.HM_ReedSwitchController;
import de.citec.dal.hal.devices.homematic.HM_RotaryHandleSensorController;
import de.citec.dal.hal.devices.philips.PH_Hue_E27Controller;
import de.citec.dal.hal.devices.philips.PH_Hue_GU10Controller;
import de.citec.dal.hal.devices.plugwise.PW_PowerPlugController;
import de.citec.dal.service.HardwareManager;
import de.citec.dal.service.HardwareRegistry;
import java.util.logging.Level;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author thuxohl
 */
public class RSBBindingConnection implements RSBBindingInterface {
    
    private static final Logger logger = LoggerFactory.getLogger(RSBBindingConnection.class);
    
    private static RSBBindingConnection instance;
    private final RSBBindingInterface binding;
    
    private final HardwareRegistry registry;
    private final HardwareManager hardwareManager;

    public RSBBindingConnection(RSBBindingInterface binding) {
        this.binding = binding;
        this.instance = this;
        this.registry = HardwareRegistry.getInstance();
        this.hardwareManager = HardwareManager.getInstance();
        this.initDevices();
    }
    
    private void initDevices() {
        logger.info("Init devices...");
        Location outdoor = new Location("Outdoor");
        Location kitchen = new Location("kitchen");
        Location wardrobe = new Location("wardrobe");
        Location living = new Location("living");
        Location sports = new Location("sports");
        Location bath = new Location("bath");
        Location control = new Location("control");
        
        try {
            registry.register(new PW_PowerPlugController("PW_PowerPlug_000", "USBCharger_1", control));
            registry.register(new PW_PowerPlugController("PW_PowerPlug_001", "USBCharger_2", control));
            registry.register(new PW_PowerPlugController("PW_PowerPlug_002", "USBCharger_3", control));
            registry.register(new PW_PowerPlugController("PW_PowerPlug_003", "USBCharger_4", control));
            registry.register(new PW_PowerPlugController("PW_PowerPlug_004", "Fan", control));
            registry.register(new PW_PowerPlugController("PW_PowerPlug_005", "", control));
            registry.register(new PW_PowerPlugController("PW_PowerPlug_006", "", control));
            registry.register(new PW_PowerPlugController("PW_PowerPlug_007", "", control));
            registry.register(new PW_PowerPlugController("PW_PowerPlug_008", "", control));
            
            registry.register(new HM_ReedSwitchController("HM_ReedSwitch_000", "Entrance", wardrobe));
            registry.register(new HM_ReedSwitchController("HM_ReedSwitch_001", "1", kitchen));
            registry.register(new HM_ReedSwitchController("HM_ReedSwitch_002", "2", kitchen));
            registry.register(new HM_ReedSwitchController("HM_ReedSwitch_003", "3", bath));
            registry.register(new HM_ReedSwitchController("HM_ReedSwitch_004", "4", sports));
            registry.register(new HM_ReedSwitchController("HM_ReedSwitch_005", "5", sports));
            registry.register(new HM_ReedSwitchController("HM_ReedSwitch_006", "6", living));
            registry.register(new HM_ReedSwitchController("HM_ReedSwitch_007", "7", living));
            registry.register(new HM_ReedSwitchController("HM_ReedSwitch_008", "8", sports));
            
            registry.register(new HM_RotaryHandleSensorController("HM_RotaryHandleSensor_000", "WindowLeft", living));
            registry.register(new HM_RotaryHandleSensorController("HM_RotaryHandleSensor_001", "WindowRight", living));
            registry.register(new HM_RotaryHandleSensorController("HM_RotaryHandleSensor_002", "Window", sports));
            
            registry.register(new F_MotionSensorController("F_MotionSensor_000", "Entrance", wardrobe));
            registry.register(new F_MotionSensorController("F_MotionSensor_001", "Hallway", wardrobe));
            registry.register(new F_MotionSensorController("F_MotionSensor_002", "Couch", living));
            registry.register(new F_MotionSensorController("F_MotionSensor_003", "Media", living));
            registry.register(new F_MotionSensorController("F_MotionSensor_004", "Table", living));
            registry.register(new F_MotionSensorController("F_MotionSensor_005", "Control", living));
            registry.register(new F_MotionSensorController("F_MotionSensor_006", "Global", kitchen));
            registry.register(new F_MotionSensorController("F_MotionSensor_007", "Global", bath));
            registry.register(new F_MotionSensorController("F_MotionSensor_008", "Entrance", bath));
            registry.register(new F_MotionSensorController("F_MotionSensor_009", "Shower", bath));
            registry.register(new F_MotionSensorController("F_MotionSensor_010", "Sink", bath));
            registry.register(new F_MotionSensorController("F_MotionSensor_011", "Interaction", sports));
            registry.register(new F_MotionSensorController("F_MotionSensor_012", "Pathway", sports));
            registry.register(new F_MotionSensorController("F_MotionSensor_013", "Entrance", control));
            registry.register(new F_MotionSensorController("F_MotionSensor_014", "TestUnit_1", control));
            registry.register(new F_MotionSensorController("F_MotionSensor_015", "Entrance", outdoor));
            
            registry.register(new PH_Hue_E27Controller("PH_Hue_E27_000", "Hallway_0", wardrobe));
            registry.register(new PH_Hue_E27Controller("PH_Hue_E27_001", "Hallway_1", wardrobe));
            registry.register(new PH_Hue_E27Controller("PH_Hue_E27_002", "Table_0", living));
            registry.register(new PH_Hue_E27Controller("PH_Hue_E27_003", "Table_1", living));
            registry.register(new PH_Hue_E27Controller("PH_Hue_E27_004", "Couch", living));
            registry.register(new PH_Hue_E27Controller("PH_Hue_E27_005", "Media", living));
            registry.register(new PH_Hue_E27Controller("PH_Hue_E27_006", "Interaction_0", sports));
            registry.register(new PH_Hue_E27Controller("PH_Hue_E27_007", "Interaction_1", sports));
            registry.register(new PH_Hue_E27Controller("PH_Hue_E27_008", "TestUnit_0", control));
            registry.register(new PH_Hue_E27Controller("PH_Hue_E27_009", "TestUnit_1", control));
            
            registry.register(new PH_Hue_GU10Controller("PH_Hue_GU10_000", "Global_0", kitchen));
            registry.register(new PH_Hue_GU10Controller("PH_Hue_GU10_001", "Global_1", kitchen));
            registry.register(new PH_Hue_GU10Controller("PH_Hue_GU10_002", "Global_2", kitchen));
            registry.register(new PH_Hue_GU10Controller("PH_Hue_GU10_003", "Global_3", kitchen));
            registry.register(new PH_Hue_GU10Controller("PH_Hue_GU10_004", "Global_0", bath));
            registry.register(new PH_Hue_GU10Controller("PH_Hue_GU10_005", "Global_1", bath));
            registry.register(new PH_Hue_GU10Controller("PH_Hue_GU10_006", "Global_2", bath));
            registry.register(new PH_Hue_GU10Controller("PH_Hue_GU10_007", "Global_3", bath));
            
            registry.register(new HA_TYA606EController("HA_TYA606E_000", "1", control));
            registry.register(new HA_TYA606EController("HA_TYA606E_001", "2", control));
            registry.register(new HA_TYA606EController("HA_TYA606E_002", "3", control));
            registry.register(new HA_TYA606EController("HA_TYA606E_003", "4", control));
            registry.register(new HA_TYA606EController("HA_TYA606E_004", "5", control));
            registry.register(new HA_TYA606EController("HA_TYA606E_005", "6", control));
            registry.register(new HA_TYA606EController("HA_TYA606E_006", "7", control));
            registry.register(new HA_TYA606EController("HA_TYA606E_007", "8", control));
            registry.register(new HA_TYA606EController("HA_TYA606E_008", "9", control));
            registry.register(new HA_TYA606EController("HA_TYA606E_009", "10", control));
            registry.register(new HA_TYA606EController("HA_TYA606E_010", "11", control));
            
            String[] giraLabel0 = {"Button_1","Button_2","Button_3","Button_4"};
            registry.register(new GI_5142Controller("GI_5142_000", "Entrance", giraLabel0,  bath));
            registry.register(new GI_5142Controller("GI_5142_001", "Control", giraLabel0, living));
            registry.register(new GI_5142Controller("GI_5142_002", "Pathway", giraLabel0, sports));   
            
            String[] giraLabel1 = {"Button_1","Button_2","Button_3","Button_4","Button_5","Button_6"};
            registry.register(new GI_5133Controller("GI_5133_000", "Door", giraLabel1, kitchen));
            registry.register(new GI_5133Controller("GI_5133_001", "Entrance", giraLabel1, wardrobe));
            registry.register(new GI_5133Controller("GI_5133_002", "Hallway", giraLabel1, wardrobe));
            registry.register(new GI_5133Controller("GI_5133_005", "Media", giraLabel1, living));
            
            String[] giraLabel2 = {"Button_5","Button_6","Button_7","Button_8","Button_9","Button_10"};
            registry.register(new GI_5133Controller("GI_5133_004", "Control", giraLabel2, living));         
            registry.register(new GI_5133Controller("GI_5133_006", "Pathway", giraLabel2, sports));
            
            String[] giraLabel3 = {"Button_7","Button_8","Button_9","Button_10","Button_11","Button_12"};    
            registry.register(new GI_5133Controller("GI_5133_003", "Hallway", giraLabel3, wardrobe));
        } catch (RSBBindingException ex) {
            logger.warn("Could not initialize devices!", ex);
        }
    }
    
    public void activate() {
        logger.info("Activate " + getClass().getSimpleName() + "...");
        hardwareManager.activate();
    }

    public void deactivate() {
        logger.info("Deactivate " + getClass().getSimpleName() + "...");
        hardwareManager.deactivate();
    }
    
    @Override
    public void internalReceiveCommand(String itemName, Command command) {
        hardwareManager.internalReceiveCommand(itemName, command);
    }

    @Override
    public void internalReceiveUpdate(String itemName, State newState) {
        hardwareManager.internalReceiveUpdate(itemName, newState);
    }

    @Override
    public void postCommand(String itemName, Command command) throws RSBBindingException {
        binding.postCommand(itemName, command);
    }

    @Override
    public void sendCommand(String itemName, Command command) throws RSBBindingException {
        binding.sendCommand(itemName, command);
    }
    
    public static RSBBindingInterface getInstance() {
        while(instance == null) {
           logger.warn("WARN: Binding not ready yet!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(RSBBindingConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        return instance;
    }
}
