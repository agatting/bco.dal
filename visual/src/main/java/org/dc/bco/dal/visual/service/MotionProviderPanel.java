package org.dc.bco.dal.visual.service;

/*
 * #%L
 * DAL Visualisation
 * %%
 * Copyright (C) 2014 - 2016 DivineCooperation
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

import org.dc.bco.dal.lib.layer.service.provider.MotionProviderService;
import org.dc.jul.exception.CouldNotPerformException;
import org.dc.jul.exception.printer.ExceptionPrinter;
import org.dc.jul.exception.InvalidStateException;
import org.dc.jul.exception.printer.LogLevel;
import org.dc.jul.processing.StringProcessor;
import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author mpohling
 */
public class MotionProviderPanel extends AbstractServicePanel<MotionProviderService> {

    private final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM);

    /**
     * Creates new form BrightnessService
     *
     * @throws org.dc.jul.exception.InstantiationException
     */
    public MotionProviderPanel() throws org.dc.jul.exception.InstantiationException {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        motionStatePanel = new javax.swing.JPanel();
        motionStatusLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lastMovementValueLabel = new javax.swing.JLabel();

        motionStatePanel.setBackground(new java.awt.Color(204, 204, 204));
        motionStatePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 5, true));
        motionStatePanel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        motionStatusLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        motionStatusLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        motionStatusLabel.setText("MotionState");
        motionStatusLabel.setFocusable(false);
        motionStatusLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout motionStatePanelLayout = new javax.swing.GroupLayout(motionStatePanel);
        motionStatePanel.setLayout(motionStatePanelLayout);
        motionStatePanelLayout.setHorizontalGroup(
            motionStatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(motionStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
        );
        motionStatePanelLayout.setVerticalGroup(
            motionStatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(motionStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
        );

        jLabel1.setText("Last Movement:");

        lastMovementValueLabel.setText("N/A");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(motionStatePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lastMovementValueLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(motionStatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lastMovementValueLabel))
                .addContainerGap(169, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lastMovementValueLabel;
    private javax.swing.JPanel motionStatePanel;
    private javax.swing.JLabel motionStatusLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    protected void updateDynamicComponents() {
        try {
            switch (getService().getMotion().getValue()) {
                case MOVEMENT:
                    motionStatusLabel.setForeground(Color.BLACK);
                    motionStatePanel.setBackground(Color.GREEN.darker());
                    break;
                case NO_MOVEMENT:
                    motionStatusLabel.setForeground(Color.BLACK);
                    motionStatePanel.setBackground(Color.LIGHT_GRAY);
                    break;
                case UNKNOWN:
                    motionStatusLabel.setForeground(Color.BLACK);
                    motionStatePanel.setBackground(Color.ORANGE.darker());
                    break;
                default:
                    throw new InvalidStateException("State[" + getService().getMotion().getValue() + "] is unknown.");
            }
            motionStatusLabel.setText(StringProcessor.transformUpperCaseToCamelCase(getService().getMotion().getValue().name()));
            try {
                lastMovementValueLabel.setText(dateFormat.format(new Date(getService().getMotion().getLastMovement().getTime())));
            } catch (Exception ex) {
                lastMovementValueLabel.setText("N/A");
                ExceptionPrinter.printHistory(new CouldNotPerformException("Could not format: ["+getService().getMotion().getLastMovement()+"]!",ex), logger, LogLevel.ERROR);
            }
        } catch (CouldNotPerformException ex) {
            ExceptionPrinter.printHistory(ex, logger, LogLevel.ERROR);
        }
    }
}
