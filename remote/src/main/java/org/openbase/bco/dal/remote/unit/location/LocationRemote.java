package org.openbase.bco.dal.remote.unit.location;

import com.google.protobuf.GeneratedMessage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Future;
import org.openbase.bco.dal.lib.layer.service.ServiceRemote;
import org.openbase.bco.dal.lib.layer.unit.UnitRemote;
import org.openbase.bco.dal.lib.layer.unit.location.Location;
import org.openbase.bco.dal.remote.service.ServiceRemoteManager;
import org.openbase.bco.dal.remote.unit.AbstractUnitRemote;
import org.openbase.bco.dal.remote.unit.Units;
import static org.openbase.bco.dal.remote.unit.Units.LOCATION;
import org.openbase.bco.registry.location.remote.CachedLocationRegistryRemote;
import org.openbase.bco.registry.remote.Registries;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.NotAvailableException;
import org.openbase.jul.extension.rsb.com.RPCHelper;
import org.openbase.jul.pattern.Observable;
import rsb.converter.DefaultConverterRepository;
import rsb.converter.ProtocolBufferConverter;
import rst.domotic.action.ActionConfigType;
import rst.domotic.action.SnapshotType.Snapshot;
import rst.domotic.service.ServiceTemplateType;
import rst.domotic.state.AlarmStateType;
import rst.domotic.state.BlindStateType;
import rst.domotic.state.BrightnessStateType;
import rst.domotic.state.ColorStateType;
import rst.domotic.state.MotionStateType;
import rst.domotic.state.PowerConsumptionStateType;
import rst.domotic.state.PowerStateType;
import rst.domotic.state.PresenceStateType;
import rst.domotic.state.SmokeStateType;
import rst.domotic.state.StandbyStateType;
import rst.domotic.state.TamperStateType;
import rst.domotic.state.TemperatureStateType;
import rst.domotic.unit.UnitConfigType.UnitConfig;
import rst.domotic.unit.UnitTemplateType;
import rst.domotic.unit.UnitTemplateType.UnitTemplate.UnitType;
import rst.domotic.unit.location.LocationDataType;
import rst.domotic.unit.location.LocationDataType.LocationData;
import rst.vision.ColorType;
import rst.vision.HSBColorType;
import rst.vision.RGBColorType;

/*
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
/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class LocationRemote extends AbstractUnitRemote<LocationData> implements Location {

    static {
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(LocationDataType.LocationData.getDefaultInstance()));
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(HSBColorType.HSBColor.getDefaultInstance()));
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(ColorStateType.ColorState.getDefaultInstance()));
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(ColorType.Color.getDefaultInstance()));
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(RGBColorType.RGBColor.getDefaultInstance()));
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(PowerStateType.PowerState.getDefaultInstance()));
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(AlarmStateType.AlarmState.getDefaultInstance()));
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(MotionStateType.MotionState.getDefaultInstance()));
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(PowerConsumptionStateType.PowerConsumptionState.getDefaultInstance()));
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(BlindStateType.BlindState.getDefaultInstance()));
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(SmokeStateType.SmokeState.getDefaultInstance()));
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(StandbyStateType.StandbyState.getDefaultInstance()));
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(TamperStateType.TamperState.getDefaultInstance()));
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(BrightnessStateType.BrightnessState.getDefaultInstance()));
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(TemperatureStateType.TemperatureState.getDefaultInstance()));
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(PresenceStateType.PresenceState.getDefaultInstance()));
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(ActionConfigType.ActionConfig.getDefaultInstance()));
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(Snapshot.getDefaultInstance()));
    }

    private final ServiceRemoteManager serviceRemoteManager;

    public LocationRemote() {
        super(LocationData.class);
        this.serviceRemoteManager = new ServiceRemoteManager(this) {
            @Override
            protected Set<ServiceTemplateType.ServiceTemplate.ServiceType> getManagedServiceTypes() throws NotAvailableException, InterruptedException {
                return getSupportedServiceTypes();
            }

            @Override
            protected void notifyServiceUpdate(Observable source, Object data) throws NotAvailableException, InterruptedException {
                // anything needed here?
            }
        };
    }

    @Override
    public UnitConfig applyConfigUpdate(UnitConfig config) throws CouldNotPerformException, InterruptedException {
        UnitConfig unitConfig = super.applyConfigUpdate(config);
        serviceRemoteManager.applyConfigUpdate(unitConfig.getLocationConfig().getUnitIdList());
        return unitConfig;
    }

    @Override
    public void activate() throws InterruptedException, CouldNotPerformException {
        // TODO is this wait for data realy needed? blocking activation method is some kind of bad behaviour.
        CachedLocationRegistryRemote.waitForData();
        serviceRemoteManager.activate();
        super.activate();
    }

    @Override
    public void deactivate() throws InterruptedException, CouldNotPerformException {
        serviceRemoteManager.deactivate();
        super.deactivate();
    }

    @Override
    public Future<Snapshot> recordSnapshot() throws CouldNotPerformException, InterruptedException {
        return RPCHelper.callRemoteMethod(this, Snapshot.class);
    }

    @Override
    public Future<Snapshot> recordSnapshot(UnitTemplateType.UnitTemplate.UnitType unitType) throws CouldNotPerformException, InterruptedException {
        return RPCHelper.callRemoteMethod(unitType, this, Snapshot.class);
    }

    @Override
    public Future<Void> restoreSnapshot(final Snapshot snapshot) throws CouldNotPerformException, InterruptedException {
        return RPCHelper.callRemoteMethod(snapshot, this, Void.class);
    }

    @Override
    public Future<Void> applyAction(ActionConfigType.ActionConfig actionConfig) throws CouldNotPerformException, InterruptedException {
        return RPCHelper.callRemoteMethod(actionConfig, this, Void.class);
    }

    @Override
    public ServiceRemote getServiceRemote(final ServiceTemplateType.ServiceTemplate.ServiceType serviceType) throws NotAvailableException {
        return serviceRemoteManager.getServiceRemote(serviceType);
    }

    /**
     *
     * @return
     * @throws CouldNotPerformException
     * @deprecated please use Registries.getLocationRegistry().getNeighborLocations(String locationId) instead.
     */
    @Override
    @Deprecated
    public List<String> getNeighborLocationIds() throws CouldNotPerformException {
        List<String> neighborIdList = new ArrayList<>();
        try {
            for (UnitConfig locationConfig : CachedLocationRegistryRemote.getRegistry().getNeighborLocations(getId())) {
                neighborIdList.add(locationConfig.getId());
            }
        } catch (InterruptedException ex) {
            throw new CouldNotPerformException("Could not get CachedLocationRegistryRemote!", ex);
        }
        return neighborIdList;
    }

    // TODO: move into interface as default implementation
    public List<LocationRemote> getNeighborLocationList(final boolean waitForData) throws CouldNotPerformException {
        List<LocationRemote> neighborList = new ArrayList<>();
        try {
            for (UnitConfig locationUnitConfig : CachedLocationRegistryRemote.getRegistry().getNeighborLocations(getId())) {
                neighborList.add(Units.getUnit(locationUnitConfig, waitForData, LOCATION));
            }
        } catch (InterruptedException ex) {
            throw new CouldNotPerformException("Could not get all neighbors!", ex);
        }
        return neighborList;
    }

    /**
     *
     * @return
     * @throws NotAvailableException
     * @throws InterruptedException
     * @deprecated please use getUnitMap() instead.
     */
    @Deprecated
    public Map<UnitType, List<UnitRemote>> getProvidedUnitMap() throws NotAvailableException, InterruptedException {
        return getUnitMap();
    }

    /**
     * Returns a Map of all units provided by this location sorted by their UnitType.
     *
     * @return the Map of provided units.
     * @throws org.openbase.jul.exception.NotAvailableException is thrown if the map is not available.
     * @throws java.lang.InterruptedException is thrown if the current thread was externally interrupted.
     */
    // TODO: move into interface as default implementation
    public Map<UnitType, List<UnitRemote>> getUnitMap() throws NotAvailableException, InterruptedException {
        try {
            final Map<UnitType, List<UnitRemote>> unitRemoteMap = new TreeMap<>();

            for (String unitId : getConfig().getLocationConfig().getUnitIdList()) {
                UnitRemote<? extends GeneratedMessage> unitRemote = Units.getUnit(unitId, false);
                if (!unitRemoteMap.containsKey(unitRemote.getType())) {
                    unitRemoteMap.put(unitRemote.getType(), new ArrayList<>());
                }
                unitRemoteMap.get(unitRemote.getType()).add(unitRemote);
            }
            return unitRemoteMap;

        } catch (CouldNotPerformException ex) {
            throw new NotAvailableException("Unit map of " + this);
        }
    }

    /**
     *
     * Method returns a list of all units filtered by the given unit type which are directly or recursively provided by this location.
     *
     * @param <UR> the unit remote class type.
     * @param unitType the unit type.
     * @param waitForData if this flag is set to true the current thread will block until all unit remotes are fully synchronized with the unit controllers.
     * @param unitRemoteClass the unit remote class.
     * @return a map of instance of the given remote class.
     * @throws CouldNotPerformException is thrown in case something went wrong.
     * @throws InterruptedException is thrown if the current thread was externally interrupted.
     */
    // TODO: move into interface as default implementation
    public <UR extends UnitRemote<?>> Collection<UR> getUnits(final UnitType unitType, final boolean waitForData, final Class<UR> unitRemoteClass) throws CouldNotPerformException, InterruptedException {
        return getUnits(unitType, waitForData, unitRemoteClass, true);
    }

    /**
     *
     * Method returns a list of all units filtered by the given unit type which are directly provided by this location.
     * In case the {@code recursive} flag is set to true than recursive related units are included as well.
     *
     * @param <UR> the unit remote class type.
     * @param unitType the unit type.
     * @param waitForData if this flag is set to true the current thread will block until all unit remotes are fully synchronized with the unit controllers.
     * @param unitRemoteClass the unit remote class.
     * @param recursive defines if recursive related unit should be included as well.
     * @return a map of instance of the given remote class.
     * @throws CouldNotPerformException is thrown in case something went wrong.
     * @throws InterruptedException is thrown if the current thread was externally interrupted.
     */
    // TODO: move into interface as default implementation
    public <UR extends UnitRemote<?>> Collection<UR> getUnits(final UnitType unitType, final boolean waitForData, final Class<UR> unitRemoteClass, final boolean recursive) throws CouldNotPerformException, InterruptedException {
        final List<UR> unitRemote = new ArrayList<>();
        Registries.waitForData();
        for (final UnitConfig unitConfig : Registries.getLocationRegistry().getUnitConfigsByLocation(unitType, getId())) {
            if (recursive || unitConfig.getPlacementConfig().getLocationId().equals(getId())) {
                unitRemote.add(Units.getUnit(unitConfig, waitForData, unitRemoteClass));
            }
        }
        return unitRemote;
    }
}
