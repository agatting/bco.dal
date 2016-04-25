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
import org.dc.bco.dal.lib.layer.service.operation.TargetTemperatureOperationService;
import org.dc.jul.exception.CouldNotPerformException;
import rst.homeautomation.service.ServiceTemplateType.ServiceTemplate.ServiceType;

/**
 *
 * @author <a href="mailto:thuxohl@techfak.uni-bielefeld.com">Tamino Huxohl</a>
 */
public class TargetTemperatureServiceRemote extends AbstractServiceRemote<TargetTemperatureOperationService> implements TargetTemperatureOperationService {

    public TargetTemperatureServiceRemote() {
        super(ServiceType.TARGET_TEMPERATURE_SERVICE);
    }

    @Override
    public void setTargetTemperature(Double value) throws CouldNotPerformException {
        for (TargetTemperatureOperationService service : getServices()) {
            service.setTargetTemperature(value);
        }
    }

    /**
     * Returns the average target temperature value for a collection of target
     * temperature services.
     *
     * @return
     * @throws CouldNotPerformException
     */
    @Override
    public Double getTargetTemperature() throws CouldNotPerformException {
        Double average = 0d;
        for (TargetTemperatureService service : getServices()) {
            average += service.getTargetTemperature();
        }
        average /= getServices().size();
        return average;
    }
}
