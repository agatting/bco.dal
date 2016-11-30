package org.openbase.bco.dal.visual.service;

/*
 * #%L
 * DAL Visualisation
 * %%
 * Copyright (C) 2014 - 2016 openbase.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
import org.openbase.bco.dal.lib.layer.service.provider.DoorStateProviderService;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.openbase.jul.exception.InvalidStateException;
import org.openbase.jul.exception.printer.LogLevel;
import org.openbase.jul.processing.StringProcessor;
import java.awt.Color;
import org.openbase.bco.dal.lib.layer.service.consumer.ConsumerService;
import org.openbase.bco.dal.lib.layer.service.operation.OperationService;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class DoorStateServicePanel extends AbstractServicePanel<DoorStateProviderService, ConsumerService, OperationService> {

    /**
     * Creates new form BrightnessService
     *
     * @throws org.openbase.jul.exception.InstantiationException
     */
    public DoorStateServicePanel() throws org.openbase.jul.exception.InstantiationException {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        standbyStatePanel = new javax.swing.JPanel();
        doorStatusLabel = new javax.swing.JLabel();

        standbyStatePanel.setBackground(new java.awt.Color(204, 204, 204));
        standbyStatePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 5, true));
        standbyStatePanel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        doorStatusLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        doorStatusLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        doorStatusLabel.setText("DoorState");
        doorStatusLabel.setFocusable(false);
        doorStatusLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout standbyStatePanelLayout = new javax.swing.GroupLayout(standbyStatePanel);
        standbyStatePanel.setLayout(standbyStatePanelLayout);
        standbyStatePanelLayout.setHorizontalGroup(
            standbyStatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(doorStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
        );
        standbyStatePanelLayout.setVerticalGroup(
            standbyStatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(doorStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(standbyStatePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(standbyStatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel doorStatusLabel;
    private javax.swing.JPanel standbyStatePanel;
    // End of variables declaration//GEN-END:variables

    @Override
    protected void updateDynamicComponents() {
        try {
            switch (getProviderService().getDoorState().getValue()) {
                case OPEN:
                case IN_BETWEEN:
                    doorStatusLabel.setForeground(Color.BLACK);
                    standbyStatePanel.setBackground(Color.GREEN.darker());
                    break;
                case CLOSED:
                    doorStatusLabel.setForeground(Color.BLACK);
                    standbyStatePanel.setBackground(Color.LIGHT_GRAY);
                    break;
                case UNKNOWN:
                    doorStatusLabel.setForeground(Color.BLACK);
                    standbyStatePanel.setBackground(Color.ORANGE.darker());
                    break;
                default:
                    throw new InvalidStateException("State[" + getProviderService().getDoorState().getValue() + "] is unknown.");
            }
            doorStatusLabel.setText("Current DoorState = " + StringProcessor.transformUpperCaseToCamelCase(getProviderService().getDoorState().getValue().name()));
        } catch (CouldNotPerformException ex) {
            ExceptionPrinter.printHistory(ex, logger, LogLevel.ERROR);
        }
    }
}
