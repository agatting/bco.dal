/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.bco.dal.remote.unit;

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

import org.dc.bco.dal.lib.layer.unit.SmokeDetectorInterface;
import org.dc.jul.exception.CouldNotPerformException;
import rsb.converter.DefaultConverterRepository;
import rsb.converter.ProtocolBufferConverter;
import rst.homeautomation.state.AlarmStateType.AlarmState;
import rst.homeautomation.state.SmokeStateType.SmokeState;
import rst.homeautomation.unit.SmokeDetectorType.SmokeDetector;

/**
 *
 * @author thuxohl
 */
public class SmokeDetectorRemote extends AbstractUnitRemote<SmokeDetector> implements SmokeDetectorInterface {

    static {
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(SmokeDetector.getDefaultInstance()));
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(AlarmState.getDefaultInstance()));
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(SmokeState.getDefaultInstance()));
    }

    @Override
    public void notifyUpdated(SmokeDetector data) throws CouldNotPerformException {
    }

    @Override
    public AlarmState getSmokeAlarmState() throws CouldNotPerformException {
        return getData().getSmokeAlarmState();
    }

    @Override
    public SmokeState getSmokeState() throws CouldNotPerformException {
        return getData().getSmokeState();
    }
}
