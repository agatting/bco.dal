/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.bco.dal.lib.layer.service.provider;

/*
 * #%L
 * DAL Library
 * %%
 * Copyright (C) 2014 - 2016 DivineCooperation
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import org.dc.jul.exception.CouldNotPerformException;
import org.dc.jul.exception.printer.ExceptionPrinter;
import org.dc.jul.exception.InvocationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rsb.Event;
import rsb.patterns.EventCallback;
import rst.homeautomation.state.AlarmStateType.AlarmState;

/**
 *
 * @author thuxohl
 */
public interface TemperatureAlarmStateProvider extends Provider {
    
    public AlarmState getTemperatureAlarmState() throws CouldNotPerformException;

    public class GetTemperatureAlarmStateCallback extends EventCallback {

        private static final Logger logger = LoggerFactory.getLogger(GetTemperatureAlarmStateCallback.class);

        private final TemperatureAlarmStateProvider provider;

        public GetTemperatureAlarmStateCallback(final TemperatureAlarmStateProvider provider) {
            this.provider = provider;
        }

        @Override
        public Event invoke(final Event request) throws UserCodeException {
            try {
                return new Event(AlarmState.class, provider.getTemperatureAlarmState());
            } catch (Exception ex) {
                throw ExceptionPrinter.printHistoryAndReturnThrowable(new UserCodeException(new InvocationFailedException(this, provider, ex)), logger);
            }
        }
    }
}
