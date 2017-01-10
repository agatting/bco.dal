package org.openbase.bco.dal.lib.layer.unit;

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

import java.lang.reflect.Method;
import org.openbase.jul.exception.CouldNotPerformException;
import rst.domotic.service.ServiceTemplateType;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public interface UnitController extends Unit {

    /**
     * Returns the service state update method for the given service type.
     * @param serviceType
     * @param serviceArgumentClass
     * @return
     * @throws CouldNotPerformException
     */
    public Method getUpdateMethod(final ServiceTemplateType.ServiceTemplate.ServiceType serviceType, Class serviceArgumentClass) throws CouldNotPerformException;

    /**
     * Applies the given service state update for this unit.
     * @param serviceType
     * @param serviceArgument
     * @throws CouldNotPerformException
     */
    public void applyDataUpdate(final ServiceTemplateType.ServiceTemplate.ServiceType serviceType, Object serviceArgument) throws CouldNotPerformException;
}
