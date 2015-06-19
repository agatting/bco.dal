/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.dal.hal.provider;

import de.citec.jul.exception.CouldNotPerformException;
import de.citec.jul.exception.ExceptionPrinter;
import de.citec.jul.exception.InvocationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rsb.Event;
import rsb.patterns.EventCallback;

/**
 *
 * @author thuxohl
 */
public interface TemperatureProvider extends Provider {

    public Double getTemperature() throws CouldNotPerformException;

    public class GetTemperatureCallback extends EventCallback {

        private static final Logger logger = LoggerFactory.getLogger(GetTemperatureCallback.class);

        private final TemperatureProvider provider;

        public GetTemperatureCallback(final TemperatureProvider provider) {
            this.provider = provider;
        }

        @Override
        public Event invoke(final Event request) throws Throwable {
            try {
                return new Event(Float.class, provider.getTemperature());
            } catch (Exception ex) {
                throw ExceptionPrinter.printHistory(logger, new InvocationFailedException(this, provider, ex));
            }
        }
    }
}
