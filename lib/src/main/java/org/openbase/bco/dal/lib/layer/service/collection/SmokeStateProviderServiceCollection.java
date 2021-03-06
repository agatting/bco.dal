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
import org.openbase.bco.dal.lib.layer.service.provider.SmokeStateProviderService;
import org.openbase.jul.exception.NotAvailableException;
import rst.domotic.state.SmokeStateType.SmokeState;
import rst.domotic.unit.UnitTemplateType.UnitTemplate.UnitType;

/**
 *
 * * @author <a href="mailto:pleminoq@openbase.org">Tamino Huxohl</a>
 */
public interface SmokeStateProviderServiceCollection extends SmokeStateProviderService {

    /**
     * Computes the average smoke level and the state as smoke if at least one underlying services detects smoke.
     * If no service detects smoke and at least one detects some smoke then that is set and else no smoke.
     *
     * @return
     * @throws NotAvailableException
     */
    @Override
    public SmokeState getSmokeState() throws NotAvailableException;

    /**
     * Computes the average smoke level and the state as smoke if at least one underlying services detects smoke.
     * If no service detects smoke and at least one detects some smoke then that is set and else no smoke.
     * Only used the smokeStateProvider with given unitType for the evaluation.
     *
     * @param unitType
     * @return
     * @throws NotAvailableException
     */
    public SmokeState getSmokeState(final UnitType unitType) throws NotAvailableException;
}
