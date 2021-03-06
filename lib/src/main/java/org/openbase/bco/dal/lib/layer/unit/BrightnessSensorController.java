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
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.InstantiationException;
import org.openbase.jul.exception.NotAvailableException;
import org.openbase.jul.extension.protobuf.ClosableDataBuilder;
import rsb.converter.DefaultConverterRepository;
import rsb.converter.ProtocolBufferConverter;
import rst.domotic.state.BrightnessStateType.BrightnessState;
import rst.domotic.state.IlluminanceStateType.IlluminanceState;
import rst.domotic.unit.dal.BrightnessSensorDataType.BrightnessSensorData;

/**
 * Is not supported anymore. Use LightSensorController instead.
 *
 * * @author <a href="mailto:pleminoq@openbase.org">Tamino Huxohl</a>
 */
@Deprecated
public class BrightnessSensorController extends AbstractDALUnitController<BrightnessSensorData, BrightnessSensorData.Builder> implements BrightnessSensor {

    static {
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(BrightnessSensorData.getDefaultInstance()));
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(BrightnessState.getDefaultInstance()));
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(IlluminanceState.getDefaultInstance()));
    }

    public BrightnessSensorController(final UnitHost unitHost, BrightnessSensorData.Builder builder) throws InstantiationException, CouldNotPerformException {
        super(BrightnessSensorController.class, unitHost, builder);
    }

    public void updateBrightnessStateProvider(final BrightnessState value) throws CouldNotPerformException {
        logger.debug("Apply brightnessState Update[" + value + "] for " + this + ".");

        try (ClosableDataBuilder<BrightnessSensorData.Builder> dataBuilder = getDataBuilder(this)) {
            dataBuilder.getInternalBuilder().setBrightnessState(value);
        } catch (Exception ex) {
            throw new CouldNotPerformException("Could not apply brightnessState Update[" + value + "] for " + this + "!", ex);
        }
    }

    @Deprecated
    @Override
    public BrightnessState getBrightnessState() throws NotAvailableException {
        try {
            return getData().getBrightnessState();
        } catch (CouldNotPerformException ex) {
            throw new NotAvailableException("brightnessState", ex);
        }
    }

    public void updateIlluminanceStateProvider(final IlluminanceState illuminanceState) throws CouldNotPerformException {
        logger.debug("Apply illuminanceState Update[" + illuminanceState + "] for " + this + ".");

        try (ClosableDataBuilder<BrightnessSensorData.Builder> dataBuilder = getDataBuilder(this)) {
            dataBuilder.getInternalBuilder().setIlluminanceState(illuminanceState);
        } catch (Exception ex) {
            throw new CouldNotPerformException("Could not apply illuminanceState Update[" + illuminanceState + "] for " + this + "!", ex);
        }
    }

    @Override
    public IlluminanceState getIlluminanceState() throws NotAvailableException {
        try {
            return getData().getIlluminanceState();
        } catch (CouldNotPerformException ex) {
            throw new NotAvailableException("illuminanceState", ex);
        }
    }
}
