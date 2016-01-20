/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.bco.dal.remote.control.action;

import org.dc.bco.dal.lib.layer.service.ServiceType;
import org.dc.jul.exception.CouldNotPerformException;
import rst.homeautomation.control.action.ActionConfigType;
import rst.homeautomation.service.ServiceConfigType;

/**
 *
 * @author Divine <a href="mailto:DivineThreepwood@gmail.com">Divine</a>
 */
public class Action implements ActionService {

    public enum ActionState {

        INITIATED,
        SCHEDULED,
        INITIALIZING,
        EXECUTING,
        FINISHING,
        FINISHED,
        ABORTING,
        ABORTED,
        REJECTED,
        FAILED
    }

    public enum ActionPriority {

        USER_LOW,
        USER_NORMAL,
        USER_HIGHT,
        SYSTEM_LOW,
        SYSTEM_NORMAL,
        SYSTEM_HIGH
    };

    // config
    private ActionPriority priority;
    private String origin;
    private long executionDelay;
    private long period;
    private ActionState state;

    private ActionConfigType.ActionConfig config;

    public Action(final ActionConfigType.ActionConfig config) {
        this.config = config;
    }



    @Override
    public void execute() throws CouldNotPerformException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ServiceType getServiceType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ServiceConfigType.ServiceConfig getServiceConfig() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}