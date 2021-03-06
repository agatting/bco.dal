package org.openbase.bco.dal.lib.layer.service.collection;

/*
 * #%L
 * BCO DAL Library
 * %%
 * Copyright (C) 2014 - 2017 openbase.org
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
import java.util.concurrent.Future;
import org.openbase.bco.dal.lib.layer.service.operation.ActivationStateOperationService;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.NotAvailableException;
import rst.domotic.state.ActivationStateType.ActivationState;
import rst.domotic.unit.UnitTemplateType.UnitTemplate.UnitType;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public interface ActivationStateOperationServiceCollection extends ActivationStateOperationService {

    public Future<Void> setActivationState(final ActivationState activationState, final UnitType unitType) throws CouldNotPerformException;

    /**
     * Returns on if at least one of the power services is on and else off.
     *
     * @return
     * @throws NotAvailableException
     */
    @Override
    public ActivationState getActivationState() throws NotAvailableException;

    /**
     * Returns on if at least one of the power services is on and else off.
     *
     * @param unitType the unit type to filter.
     * @return
     * @throws NotAvailableException
     */
    public ActivationState getActivationState(final UnitType unitType) throws NotAvailableException;
}
