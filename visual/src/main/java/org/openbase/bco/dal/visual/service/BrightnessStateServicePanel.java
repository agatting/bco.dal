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
import java.awt.Color;
import java.text.DecimalFormat;
import org.openbase.bco.dal.lib.layer.service.consumer.ConsumerService;
import org.openbase.bco.dal.lib.layer.service.operation.BrightnessStateOperationService;
import org.openbase.bco.dal.lib.layer.service.provider.BrightnessStateProviderService;
import org.openbase.bco.dal.lib.layer.unit.ColorableLight;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.openbase.jul.exception.printer.LogLevel;
import rst.homeautomation.state.BrightnessStateType.BrightnessState;

/**
 *
 * @author mpohling
 */
public class BrightnessStateServicePanel extends AbstractServicePanel<BrightnessStateProviderService, ConsumerService, BrightnessStateOperationService> {

    private final DecimalFormat numberFormat = new DecimalFormat("#.##");

    private final static float MAX_BRIGHTNESS = 32000f;
    private final static float MIN_BRIGHTNESS = 0f;

    /**
     * Creates new form BrightnessService
     *
     * @throws org.openbase.jul.exception.InstantiationException
     */
    public BrightnessStateServicePanel() throws org.openbase.jul.exception.InstantiationException {
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

        providerPanel = new javax.swing.JPanel();
        brightnessLevelLabelPanel = new javax.swing.JPanel();
        brightnessLevelLabel = new javax.swing.JLabel();
        operationPanel = new javax.swing.JPanel();
        brightnessBar = new javax.swing.JProgressBar();
        brightnessSlider = new javax.swing.JSlider();

        brightnessLevelLabelPanel.setBackground(new java.awt.Color(204, 204, 204));
        brightnessLevelLabelPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 5, true));
        brightnessLevelLabelPanel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        brightnessLevelLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        brightnessLevelLabel.setText("---");
        brightnessLevelLabel.setDoubleBuffered(true);
        brightnessLevelLabel.setFocusable(false);
        brightnessLevelLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout brightnessLevelLabelPanelLayout = new javax.swing.GroupLayout(brightnessLevelLabelPanel);
        brightnessLevelLabelPanel.setLayout(brightnessLevelLabelPanelLayout);
        brightnessLevelLabelPanelLayout.setHorizontalGroup(
            brightnessLevelLabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(brightnessLevelLabelPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(brightnessLevelLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        brightnessLevelLabelPanelLayout.setVerticalGroup(
            brightnessLevelLabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(brightnessLevelLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout providerPanelLayout = new javax.swing.GroupLayout(providerPanel);
        providerPanel.setLayout(providerPanelLayout);
        providerPanelLayout.setHorizontalGroup(
            providerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(brightnessLevelLabelPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        providerPanelLayout.setVerticalGroup(
            providerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(brightnessLevelLabelPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        brightnessBar.setStringPainted(true);

        brightnessSlider.setPaintLabels(true);
        brightnessSlider.setPaintTicks(true);
        brightnessSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                brightnessSliderStateChanged(evt);
            }
        });
        brightnessSlider.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                brightnessSliderPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout operationPanelLayout = new javax.swing.GroupLayout(operationPanel);
        operationPanel.setLayout(operationPanelLayout);
        operationPanelLayout.setHorizontalGroup(
            operationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(operationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(operationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(brightnessBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(brightnessSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
                .addContainerGap())
        );
        operationPanelLayout.setVerticalGroup(
            operationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(operationPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(brightnessBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(brightnessSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(providerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(operationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(providerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(operationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void brightnessSliderPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_brightnessSliderPropertyChange
    }//GEN-LAST:event_brightnessSliderPropertyChange

    private void brightnessSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_brightnessSliderStateChanged
        try {
            notifyActionProcessing(getOperationService().setBrightnessState(BrightnessState.newBuilder().setBrightness((double) brightnessSlider.getValue()).build()));
        } catch (CouldNotPerformException ex) {
            ExceptionPrinter.printHistory(new CouldNotPerformException("Could not set brightness value!", ex), logger);
        }
    }//GEN-LAST:event_brightnessSliderStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar brightnessBar;
    private javax.swing.JLabel brightnessLevelLabel;
    private javax.swing.JPanel brightnessLevelLabelPanel;
    private javax.swing.JSlider brightnessSlider;
    private javax.swing.JPanel operationPanel;
    private javax.swing.JPanel providerPanel;
    // End of variables declaration//GEN-END:variables

    @Override
    protected void updateDynamicComponents() {
        if (hasOperationService()) {
            try {
                brightnessBar.setValue((int) getProviderService().getBrightnessState().getBrightness());
            } catch (CouldNotPerformException ex) {
                ExceptionPrinter.printHistory(ex, logger);
            }
            operationPanel.setEnabled(true);
        } else {
            operationPanel.setEnabled(false);
        }

        if (hasProviderService()) {
            try {
                Double brightness = getProviderService().getBrightnessState().getBrightness();

                if (brightness < 0) {
                    brightnessLevelLabel.setForeground(Color.WHITE);
                    brightnessLevelLabelPanel.setBackground(Color.RED.darker());
                    brightnessLevelLabel.setText("UNKOWN");
                    return;
                }

                brightnessLevelLabel.setForeground(Color.BLACK);
                float colorValue = ((float) Math.max(Math.min(MAX_BRIGHTNESS, brightness), MIN_BRIGHTNESS)) / MAX_BRIGHTNESS;
                brightnessLevelLabelPanel.setBackground(Color.getHSBColor((float) 40 / 360, 1f - colorValue, colorValue));
                brightnessLevelLabel.setText(numberFormat.format(getProviderService().getBrightnessState().getBrightness()) + getProviderService().getBrightnessState().getBrightnessDataUnit());
            } catch (CouldNotPerformException ex) {
                ExceptionPrinter.printHistory(ex, logger, LogLevel.ERROR);
            }
            providerPanel.setEnabled(true);
        } else {
            providerPanel.setEnabled(false);
        }
    }
}
