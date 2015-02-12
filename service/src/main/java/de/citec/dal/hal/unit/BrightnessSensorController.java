/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.dal.hal.unit;

import de.citec.dal.hal.device.DeviceInterface;
import de.citec.jul.exception.CouldNotPerformException;
import de.citec.jul.exception.InstantiationException;
import rsb.converter.DefaultConverterRepository;
import rsb.converter.ProtocolBufferConverter;
import rst.homeautomation.BrightnessSensorType;
import rst.homeautomation.BrightnessSensorType.BrightnessSensor;

/**
 *
 * @author thuxohl
 */
public class BrightnessSensorController extends AbstractUnitController<BrightnessSensor, BrightnessSensor.Builder> implements BrightnessSensorInterface {

	static {
		DefaultConverterRepository.getDefaultConverterRepository().addConverter(
				new ProtocolBufferConverter<>(BrightnessSensorType.BrightnessSensor.getDefaultInstance()));
	}

	public BrightnessSensorController(final String label, DeviceInterface hardwareUnit, BrightnessSensor.Builder builder) throws InstantiationException {
		super(BrightnessSensorController.class, label, hardwareUnit, builder);
	}

	public void updateBrightness(final float brightness) {
		data.setBrightness(brightness);
		notifyChange();
	}

	@Override
	public Double getBrightness() throws CouldNotPerformException {
		return (double) data.getBrightness();
	}
}
