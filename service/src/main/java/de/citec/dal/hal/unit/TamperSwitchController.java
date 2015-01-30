/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.dal.hal.unit;

import de.citec.dal.exception.DALException;
import de.citec.dal.hal.AbstractUnitController;
import rsb.converter.DefaultConverterRepository;
import rsb.converter.ProtocolBufferConverter;
import rst.homeautomation.TamperSwitchType;
import rst.homeautomation.TamperSwitchType.TamperSwitch;
import rst.homeautomation.states.TamperType;

/**
 *
 * @author thuxohl
 */
public class TamperSwitchController extends AbstractUnitController<TamperSwitch, TamperSwitch.Builder> {

    static {
        DefaultConverterRepository.getDefaultConverterRepository().addConverter(
                new ProtocolBufferConverter<>(TamperSwitchType.TamperSwitch.getDefaultInstance()));
    }

    public TamperSwitchController(String id, final String label, HardwareUnit hardwareUnit, TamperSwitch.Builder builder) throws DALException {
        super(id, label, hardwareUnit, builder);
    }

    public void updateTamperState(final TamperType.Tamper.TamperState state) {
        builder.getTamperStateBuilder().setState(state);
        notifyChange();
    }

}