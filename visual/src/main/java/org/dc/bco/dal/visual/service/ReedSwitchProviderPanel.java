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

import org.dc.bco.dal.lib.layer.service.provider.ReedSwitchProviderService;
import org.dc.jul.exception.CouldNotPerformException;
import org.dc.jul.exception.InvalidStateException;
import org.dc.jul.exception.printer.ExceptionPrinter;
import org.dc.jul.exception.printer.LogLevel;
import org.dc.jul.processing.StringProcessor;
import java.awt.Color;

/**
 *
 * @author kengelma
 */
public class ReedSwitchProviderPanel extends AbstractServicePanel<ReedSwitchProviderService> {

    /**
     * Creates new form ReedSwitchProviderPanel
     * @throws org.dc.jul.exception.InstantiationException can't instantiate
     */
    public ReedSwitchProviderPanel() throws org.dc.jul.exception.InstantiationException  {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        reedSwitchStatePanel = new javax.swing.JPanel();
        reedSwitchStateLabel = new javax.swing.JLabel();

        reedSwitchStatePanel.setBackground(new java.awt.Color(204, 204, 204));
        reedSwitchStatePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 5, true));
        reedSwitchStatePanel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        reedSwitchStateLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        reedSwitchStateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reedSwitchStateLabel.setText("ReedSwitchState");
        reedSwitchStateLabel.setFocusable(false);
        reedSwitchStateLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout reedSwitchStatePanelLayout = new javax.swing.GroupLayout(reedSwitchStatePanel);
        reedSwitchStatePanel.setLayout(reedSwitchStatePanelLayout);
        reedSwitchStatePanelLayout.setHorizontalGroup(
            reedSwitchStatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(reedSwitchStateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
        reedSwitchStatePanelLayout.setVerticalGroup(
            reedSwitchStatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(reedSwitchStateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        reedSwitchStateLabel.getAccessibleContext().setAccessibleName("ReedSwitchState");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(reedSwitchStatePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(reedSwitchStatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel reedSwitchStateLabel;
    private javax.swing.JPanel reedSwitchStatePanel;
    // End of variables declaration//GEN-END:variables

    @Override
    protected void updateDynamicComponents() {        
        try {
            switch (getService().getReedSwitch().getValue()) {
                case UNKNOWN:
                    reedSwitchStateLabel.setForeground(Color.DARK_GRAY);
                    reedSwitchStatePanel.setBackground(Color.ORANGE.darker());
                    break;
                case CLOSED:
                    reedSwitchStateLabel.setForeground(Color.WHITE);
                    reedSwitchStatePanel.setBackground(Color.BLUE);
                    break;
                case OPEN:
                    reedSwitchStateLabel.setForeground(Color.WHITE);
                    reedSwitchStatePanel.setBackground(Color.GREEN.darker());
                    break;
                default:
                    throw new InvalidStateException("State[" + getService().getReedSwitch().getValue() + "] is unknown.");
            }
            reedSwitchStateLabel.setText("Current ReedState = " + StringProcessor.transformUpperCaseToCamelCase(getService().getReedSwitch().getValue().name()));
        } catch (CouldNotPerformException ex) {
            ExceptionPrinter.printHistory(ex, logger, LogLevel.ERROR);
        }
    }
}
