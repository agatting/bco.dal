/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.bco.dal.remote.service;

/*
 * #%L
 * DAL Remote
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
import org.dc.bco.dal.lib.layer.service.operation.StandbyOperationService;
import org.dc.jul.exception.CouldNotPerformException;
import rst.homeautomation.service.ServiceTemplateType.ServiceTemplate.ServiceType;
import rst.homeautomation.state.StandbyStateType.StandbyState;

/**
 *
 * @author <a href="mailto:thuxohl@techfak.uni-bielefeld.com">Tamino Huxohl</a>
 */
public class StandbyServiceRemote extends AbstractServiceRemote<StandbyOperationService> implements StandbyOperationService {

    public StandbyServiceRemote() {
        super(ServiceType.STANDBY_SERVICE);
    }

    @Override
    public void setStandby(StandbyState state) throws CouldNotPerformException {
        for (StandbyOperationService service : getServices()) {
            service.setStandby(state);
        }
    }

    /**
     * Returns running if at least one of the standby services is running and
     * else standby.
     *
     * @return
     * @throws CouldNotPerformException
     */
    @Override
    public StandbyState getStandby() throws CouldNotPerformException {
        for (StandbyService service : getServices()) {
            if (service.getStandby().getValue() == StandbyState.State.RUNNING) {
                return StandbyState.newBuilder().setValue(StandbyState.State.RUNNING).build();
            }
        }
        return StandbyState.newBuilder().setValue(StandbyState.State.STANDBY).build();
    }
}
