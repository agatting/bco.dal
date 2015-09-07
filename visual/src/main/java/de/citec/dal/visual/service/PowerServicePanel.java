/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.dal.visual.service;

import de.citec.dal.hal.service.PowerService;
import de.citec.jul.exception.CouldNotPerformException;
import de.citec.jul.exception.printer.ExceptionPrinter;
import de.citec.jul.exception.InvalidStateException;
import de.citec.jul.processing.StringProcessor;
import java.awt.Color;
import java.util.concurrent.Callable;
import rst.homeautomation.state.PowerStateType;

/**
 *
 * @author mpohling
 */
public class PowerServicePanel extends AbstractServicePanel<PowerService> {

    /**
     * Creates new form BrightnessService
     */
    public PowerServicePanel() throws de.citec.jul.exception.InstantiationException {
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

        powerButton = new javax.swing.JButton();
        powerStatePanel = new javax.swing.JPanel();
        powerStatusLabel = new javax.swing.JLabel();

        powerButton.setText("PowerButton");
        powerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                powerButtonActionPerformed(evt);
            }
        });

        powerStatePanel.setBackground(new java.awt.Color(204, 204, 204));
        powerStatePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 5, true));
        powerStatePanel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        powerStatusLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        powerStatusLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        powerStatusLabel.setText("PowerState");
        powerStatusLabel.setFocusable(false);
        powerStatusLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout powerStatePanelLayout = new javax.swing.GroupLayout(powerStatePanel);
        powerStatePanel.setLayout(powerStatePanelLayout);
        powerStatePanelLayout.setHorizontalGroup(
            powerStatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(powerStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
        );
        powerStatePanelLayout.setVerticalGroup(
            powerStatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(powerStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(powerStatePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(powerButton, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(powerStatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(powerButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void powerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_powerButtonActionPerformed
        execute(new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                try {
                    switch (getService().getPower().getValue()) {
                        case ON:
                            getService().setPower(PowerStateType.PowerState.State.OFF);
                            break;
                        case OFF:
                        case UNKNOWN:
                            getService().setPower(PowerStateType.PowerState.State.ON);
                            break;
                        default:
                            throw new InvalidStateException("State[" + getService().getPower().getValue() + "] is unknown.");
                    }
                } catch (CouldNotPerformException ex) {
                    ExceptionPrinter.printHistory(logger, new CouldNotPerformException("Could not set power state!", ex));
                }
                return null;
            }
        });
    }//GEN-LAST:event_powerButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton powerButton;
    private javax.swing.JPanel powerStatePanel;
    private javax.swing.JLabel powerStatusLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    protected void updateDynamicComponents() {
        try {
            System.out.println("stae: " + getService().getPower().getValue().name());
            switch (getService().getPower().getValue()) {
                case ON:
                    powerStatusLabel.setForeground(Color.BLACK);
                    powerStatePanel.setBackground(Color.GREEN.darker());
                    powerButton.setText("Switch Off");
                    break;
                case OFF:
                    powerStatusLabel.setForeground(Color.LIGHT_GRAY);
                    powerStatePanel.setBackground(Color.GRAY.darker());
                    powerButton.setText("Switch On");
                    break;
                case UNKNOWN:
                    powerStatusLabel.setForeground(Color.BLACK);
                    powerStatePanel.setBackground(Color.ORANGE.darker());
                    powerButton.setText("Switch Off");
                    break;
                default:
                    throw new InvalidStateException("State[" + getService().getPower().getValue() + "] is unknown.");
            }
            powerStatusLabel.setText("Current PowerState = " + StringProcessor.transformUpperCaseToCamelCase(getService().getPower().getValue().name()));
        } catch (CouldNotPerformException ex) {
            ExceptionPrinter.printHistory(logger, ex);
        }
    }
}
