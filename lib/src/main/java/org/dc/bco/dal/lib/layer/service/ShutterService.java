/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.bco.dal.lib.layer.service;

import org.dc.bco.dal.lib.layer.service.provider.ShutterProvider;
import org.dc.jul.exception.CouldNotPerformException;
import org.dc.jul.exception.printer.ExceptionPrinter;
import org.dc.jul.exception.InvocationFailedException;
import org.dc.jul.extension.rsb.com.RSBCommunicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rsb.Event;
import rsb.patterns.EventCallback;
import rst.homeautomation.state.ShutterStateType.ShutterState;

/**
 *
 * @author thuxohl
 */
public interface ShutterService extends Service, ShutterProvider {

    public void setShutter(ShutterState.State state) throws CouldNotPerformException;

    public class SetShutterCallback extends EventCallback {

        private static final Logger logger = LoggerFactory.getLogger(SetShutterCallback.class);

        private final ShutterService service;

        public SetShutterCallback(final ShutterService service) {
            this.service = service;
        }

        @Override
        public Event invoke(final Event request) throws Throwable {
            try {
                service.setShutter(((ShutterState) request.getData()).getValue());
            } catch (Exception ex) {
                throw ExceptionPrinter.printHistoryAndReturnThrowable(new InvocationFailedException(this, service, ex), logger);
            }
            return new Event(Void.class);
        }
    }
}