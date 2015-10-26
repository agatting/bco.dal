/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.dal.hal.unit;

import de.citec.dal.hal.device.Device;
import de.citec.dal.hal.service.ServiceFactory;
import de.citec.dal.hal.service.TargetTemperatureService;
import de.citec.jul.exception.CouldNotPerformException;
import de.citec.jul.exception.NotAvailableException;
import de.citec.jul.extension.protobuf.ClosableDataBuilder;
import rsb.converter.DefaultConverterRepository;
import rsb.converter.ProtocolBufferConverter;
import rst.homeautomation.unit.TemperatureControllerType.TemperatureController;
import rst.homeautomation.unit.UnitConfigType;

/**
 *
 * @author <a href="mailto:mpohling@cit-ec.uni-bielefeld.de">Divine Threepwood</a>
 */
public class TemperatureControllerController extends AbstractUnitController<TemperatureController, TemperatureController.Builder> implements TemperatureControllerInterface {

    static {
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(new ProtocolBufferConverter<>(TemperatureController.getDefaultInstance()));
    }

    private final TargetTemperatureService targetTemperatureService;

    public TemperatureControllerController(final UnitConfigType.UnitConfig config, final Device device, final TemperatureController.Builder builder) throws InstantiationException, CouldNotPerformException {
        this(config, device, builder, device.getDefaultServiceFactory());
    }

    public TemperatureControllerController(final UnitConfigType.UnitConfig config, final Device device, final TemperatureController.Builder builder, final ServiceFactory serviceFactory) throws InstantiationException, CouldNotPerformException {
        super(config, TemperatureControllerController.class, device, builder);
        this.targetTemperatureService = serviceFactory.newTargetTemperatureService(device, this);
    }

    @Override
    public void setTargetTemperature(final Double value) throws CouldNotPerformException {
        logger.debug("Set " + getType().name() + "[" + getLabel() + "] to target temperature [" + value + "]");
        targetTemperatureService.setTargetTemperature(value);
    }

    @Override
    public Double getTargetTemperature() throws CouldNotPerformException {
        try {
            return getData().getTargetTemperature();
        } catch (CouldNotPerformException ex) {
            throw new NotAvailableException("target temperature", ex);
        }
    }

    public void updateTargetTemperature(final Double value) throws CouldNotPerformException {
        logger.debug("Apply target temperature Update[" + value + "] for " + this + ".");

        try (ClosableDataBuilder<TemperatureController.Builder> dataBuilder = getDataBuilder(this)) {
            dataBuilder.getInternalBuilder().setTargetTemperature(value);
        } catch (Exception ex) {
            throw new CouldNotPerformException("Could not apply target temperature Update[" + value + "] for " + this + "!", ex);
        }
    }
    
    public void updateTemperature(final Double value) throws CouldNotPerformException {
        logger.debug("Apply actual temperature Update[" + value + "] for " + this + ".");

        try (ClosableDataBuilder<TemperatureController.Builder> dataBuilder = getDataBuilder(this)) {
            dataBuilder.getInternalBuilder().setActualTemperature(value);
        } catch (Exception ex) {
            throw new CouldNotPerformException("Could not apply actual temperature Update[" + value + "] for " + this + "!", ex);
        }
    }

    @Override
    public Double getTemperature() throws CouldNotPerformException {
        try {
            return getData().getActualTemperature();
        } catch (CouldNotPerformException ex) {
            throw new NotAvailableException("actual temperature", ex);
        }
    }
}