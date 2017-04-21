package org.openbase.bco.dal.remote.service;

/*-
 * #%L
 * BCO DAL Remote
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

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import org.openbase.bco.dal.lib.layer.service.collection.PresenceStateProviderServiceCollection;
import org.openbase.bco.dal.lib.layer.service.provider.PresenceStateProviderService;
import org.openbase.bco.dal.remote.unit.UnitRemote;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.NotAvailableException;
import org.openbase.jul.pattern.Observer;
import org.openbase.jul.pattern.Remote;
import rst.domotic.service.ServiceTemplateType.ServiceTemplate.ServiceType;
import rst.domotic.state.PresenceStateType.PresenceState;
import rst.domotic.unit.UnitTemplateType.UnitTemplate.UnitType;

/**
 *
 * @author <a href="mailto:pleminoq@openbase.org">Tamino Huxohl</a>
 */
public class PresenceStateServiceRemote extends AbstractServiceRemote<PresenceStateProviderService, PresenceState> implements PresenceStateProviderServiceCollection {

    public PresenceStateServiceRemote() {
        super(ServiceType.PRESENCE_STATE_SERVICE, PresenceState.class);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     * @throws CouldNotPerformException {@inheritDoc}
     */
    @Override
    protected PresenceState computeServiceState() throws CouldNotPerformException {
        return getPresenceState(UnitType.UNKNOWN);
    }

    @Override
    public PresenceState getPresenceState() throws NotAvailableException {
        return getServiceState();
    }

    @Override
    public PresenceState getPresenceState(final UnitType unitType) throws NotAvailableException {
        PresenceState.Builder builder = PresenceState.newBuilder().setValue(PresenceState.State.ABSENT);
        builder.getLastPresenceBuilder().setTime(0);
        for (PresenceStateProviderService provider : getServices(unitType)) {
            if (!((UnitRemote) provider).isDataAvailable()) {
                continue;
            }

            if (provider.getPresenceState().getValue() == PresenceState.State.PRESENT) {
                builder.setValue(PresenceState.State.PRESENT).build();
                builder.getLastPresenceBuilder().setTime(Math.max(builder.getLastPresence().getTime(), provider.getPresenceState().getLastPresence().getTime()));
            }
        }
        return builder.build();
    }

    public Collection<PresenceStateProviderService> getPresenceStateProviderServices() throws CouldNotPerformException {
        return getServices();
    }

    /////////////
    // START DEFAULT INTERFACE METHODS
    /////////////
    public void activate(boolean waitForData) throws CouldNotPerformException, InterruptedException {
        activate();
        waitForData();
    }

    public CompletableFuture<PresenceState> requestData() throws CouldNotPerformException {
        return requestData(true);
    }

    public void addConnectionStateObserver(Observer<ConnectionState> observer) {
        for (Remote remote : getInternalUnits()) {
            remote.addConnectionStateObserver(observer);
        }
    }

    public ConnectionState getConnectionState() {
        boolean disconnectedRemoteDetected = false;
        boolean connectedRemoteDetected = false;

        for (final Remote remote : getInternalUnits()) {
            switch (remote.getConnectionState()) {
                case CONNECTED:
                    connectedRemoteDetected = true;
                    break;
                case CONNECTING:
                case DISCONNECTED:
                    disconnectedRemoteDetected = true;
                    break;
                default:
                    //ignore unknown connection state";
            }
        }

        if (disconnectedRemoteDetected && connectedRemoteDetected) {
            return ConnectionState.CONNECTING;
        } else if (disconnectedRemoteDetected) {
            return ConnectionState.DISCONNECTED;
        } else if (connectedRemoteDetected) {
            return ConnectionState.CONNECTED;
        } else {
            return ConnectionState.UNKNOWN;
        }
    }

    public void removeConnectionStateObserver(Observer<ConnectionState> observer) {
        for (final Remote remote : getInternalUnits()) {
            remote.removeConnectionStateObserver(observer);
        }
    }
    /////////////
    // END DEFAULT INTERFACE METHODS
    /////////////
}
