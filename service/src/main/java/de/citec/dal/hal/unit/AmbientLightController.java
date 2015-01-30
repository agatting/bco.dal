/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.dal.hal.unit;

import de.citec.dal.data.transform.HSVColorTransformer;
import de.citec.dal.data.transform.PowerStateTransformer;
import de.citec.dal.exception.DALException;
import de.citec.dal.exception.RSBBindingException;
import de.citec.dal.hal.AbstractUnitController;
import de.citec.dal.hal.service.BrightnessService;
import de.citec.dal.hal.service.ColorService;
import de.citec.jul.exception.TypeNotSupportedException;
import de.citec.jul.rsb.RSBCommunicationService;
import rsb.Event;
import rsb.RSBException;
import rsb.converter.DefaultConverterRepository;
import rsb.converter.ProtocolBufferConverter;
import rsb.patterns.EventCallback;
import rsb.patterns.LocalServer;
import rst.homeautomation.AmbientLightType;
import rst.homeautomation.openhab.OpenhabCommandType.OpenhabCommand;
import rst.homeautomation.states.PowerType;
import rst.vision.HSVColorType.HSVColor;

/**
 *
 * @author nuc
 */
public class AmbientLightController extends AbstractUnitController<AmbientLightType.AmbientLight, AmbientLightType.AmbientLight.Builder> implements ColorService, BrightnessService {

    static {
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(
                new ProtocolBufferConverter<>(AmbientLightType.AmbientLight.getDefaultInstance()));
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(
                new ProtocolBufferConverter<>(HSVColor.getDefaultInstance()));
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(
                new ProtocolBufferConverter<>(PowerType.Power.getDefaultInstance()));
    }

    public AmbientLightController(String id, final String label, HardwareUnit hardwareUnit, AmbientLightType.AmbientLight.Builder builder) throws DALException {
        super(id, label, hardwareUnit, builder);
    }

    @Override
    public void registerMethods(final LocalServer server) throws RSBException {
        server.addMethod("setColor", new SetColorCallback());
        server.addMethod("setPowerState", new SetPowerStateCallback());
        server.addMethod("setBrightness", new SetBrightnessCallback());
    }

    public void updatePowerState(final PowerType.Power.PowerState state) {
        builder.getPowerStateBuilder().setState(state);
        notifyChange();
    }

    public void setPowerState(final PowerType.Power.PowerState state) throws TypeNotSupportedException, RSBBindingException {
        logger.debug("Setting [" + id + "] to PowerState [" + state.name() + "]");
        OpenhabCommand.Builder newBuilder = OpenhabCommand.newBuilder();
        newBuilder.setOnOff(PowerStateTransformer.transform(state)).setType(OpenhabCommand.CommandType.ONOFF);
        executeCommand(newBuilder);
    }

    public class SetPowerStateCallback extends EventCallback {

        @Override
        public Event invoke(final Event request) throws Throwable {
            try {
                AmbientLightController.this.setPowerState(((PowerType.Power) request.getData()).getState());
                return RSBCommunicationService.RPC_FEEDBACK_OK;
            } catch (Exception ex) {
                logger.warn("Could not invoke method [setPowerState] for [" + AmbientLightController.this.getId() + "]", ex);
                throw ex;
            }
        }
    }

    public void updateColor(final HSVColor color) {
        builder.setColor(color);
        notifyChange();
    }

    @Override
    public HSVColor getColor() {
        return builder.getColor();
    }
    
    @Override
    public void setColor(final HSVColor color) throws RSBBindingException, TypeNotSupportedException {
        logger.debug("Setting [" + id + "] to HSVColor[" + color.getHue() + "|" + color.getSaturation() + "|" + color.getValue() + "]");
        OpenhabCommand.Builder newBuilder = OpenhabCommand.newBuilder();
        newBuilder.setHsb(HSVColorTransformer.transform(color)).setType(OpenhabCommand.CommandType.HSB);
        executeCommand(newBuilder);
    }

    public class SetColorCallback extends EventCallback {

        @Override
        public Event invoke(final Event request) throws Throwable {
            try {
                AmbientLightController.this.setColor(((HSVColor) request.getData()));
                return RSBCommunicationService.RPC_FEEDBACK_OK;
            } catch (Exception ex) {
                logger.warn("Could not invoke method [setColor] for " + AmbientLightController.this, ex);
                throw ex;
            }
        }
    }
    
    @Override
    public double getBrightness() throws Exception {
        return builder.getColor().getValue();
    }

    @Override
    public void setBrightness(double brightness) throws RSBBindingException, TypeNotSupportedException {
        logger.debug("Setting [" + id + "] to Brightness[" + brightness + "]");
        setColor(cloneBuilder().getColorBuilder().setValue(brightness).build());
    }

    public class SetBrightnessCallback extends EventCallback {

        @Override
        public Event invoke(final Event request) throws Throwable {
            try {
                AmbientLightController.this.setBrightness(((double) request.getData()));
                return RSBCommunicationService.RPC_FEEDBACK_OK;
            } catch (Exception ex) {
                logger.warn("Could not invoke method [setBrightness] for " + AmbientLightController.this, ex);
                throw ex;
            }
        }
    }
}