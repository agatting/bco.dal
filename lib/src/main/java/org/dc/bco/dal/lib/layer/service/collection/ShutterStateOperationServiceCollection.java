/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.bco.dal.lib.layer.service.collection;

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

import java.util.Collection;
import org.dc.bco.dal.lib.layer.service.ShutterService;
import org.dc.jul.exception.CouldNotPerformException;
import rst.homeautomation.state.ShutterStateType.ShutterState;

/**
 *
 * @author <a href="mailto:thuxohl@techfak.uni-bielefeld.com">Tamino Huxohl</a>
 */
public interface ShutterStateOperationServiceCollection extends ShutterService {

    @Override
    default public void setShutter(ShutterState state) throws CouldNotPerformException {
        for (ShutterService service : getShutterStateOperationServices()) {
            service.setShutter(state);
        }
    }

    /**
     * Returns up if all shutter services are up and else the from up differing
     * state of the first shutter.
     *
     * @return
     * @throws CouldNotPerformException
     */
    @Override
    default public ShutterState getShutter() throws CouldNotPerformException {
        for (ShutterService service : getShutterStateOperationServices()) {
            switch (service.getShutter().getValue()) {
                case DOWN:
                    return ShutterState.newBuilder().setValue(ShutterState.State.DOWN).build();
                case STOP:
                    return ShutterState.newBuilder().setValue(ShutterState.State.STOP).build();
                case UP:
                default:
            }
        }
        return ShutterState.newBuilder().setValue(ShutterState.State.UP).build();
    }

    public Collection<ShutterService> getShutterStateOperationServices();
}