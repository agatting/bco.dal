package org.openbase.bco.dal.remote.service;

/*
 * #%L
 * DAL Remote
 * %%
 * Copyright (C) 2014 - 2016 openbase.org
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
import org.openbase.bco.dal.lib.layer.service.provider.TamperStateProviderService;
import java.util.Collection;
import org.openbase.bco.dal.lib.layer.service.collection.TamperStateProviderServiceCollection;
import rst.homeautomation.service.ServiceTemplateType.ServiceTemplate.ServiceType;

/**
 *
 * @author <a href="mailto:thuxohl@techfak.uni-bielefeld.com">Tamino Huxohl</a>
 */
public class TamperStateServiceRemote extends AbstractServiceRemote<TamperStateProviderService> implements TamperStateProviderServiceCollection {

    public TamperStateServiceRemote() {
        super(ServiceType.TAMPER_STATE_SERVICE);
    }

    @Override
    public Collection<TamperStateProviderService> getTamperStateProviderServices() {
        return getServices();
    }
}