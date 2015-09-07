/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.dal.hal.provider;

import de.citec.jul.exception.CouldNotPerformException;
import de.citec.jul.exception.printer.ExceptionPrinter;
import de.citec.jul.exception.InvocationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rsb.Event;
import rsb.patterns.EventCallback;
import rst.homeautomation.state.ButtonStateType.ButtonState;

/**
 *
 * @author thuxohl
 */
public interface ButtonProvider extends Provider {

    public ButtonState getButton() throws CouldNotPerformException;

    public class GetButtonCallback extends EventCallback {

        private static final Logger logger = LoggerFactory.getLogger(GetButtonCallback.class);

        private final ButtonProvider provider;

        public GetButtonCallback(final ButtonProvider provider) {
            this.provider = provider;
        }

        @Override
        public Event invoke(final Event request) throws Throwable {
            try {
                return new Event(ButtonState.class, provider.getButton());
            } catch (Exception ex) {
                throw ExceptionPrinter.printHistoryAndReturnThrowable(logger, new InvocationFailedException(this, provider, ex));
            }
        }
    }
}
