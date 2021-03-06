package org.openbase.bco.dal.visual;

/*
 * #%L
 * BCO DAL Visualisation
 * %%
 * Copyright (C) 2014 - 2017 openbase.org
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
import org.openbase.bco.dal.remote.unit.PowerConsumptionSensorRemote;
import org.openbase.bco.dal.visual.unit.GenericUnitPanel;
import org.openbase.bco.dal.visual.util.StatusPanel;
import org.openbase.jps.core.JPService;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.InitializationException;
import org.openbase.jul.exception.NotAvailableException;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.openbase.jul.exception.printer.LogLevel;
import org.openbase.jul.pattern.Observer;
import org.openbase.jul.schedule.TriggerFilter;
import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import javax.swing.SwingUtilities;
import org.openbase.jul.pattern.Observable;
import org.openbase.jul.schedule.GlobalCachedExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rst.domotic.unit.dal.PowerConsumptionSensorDataType.PowerConsumptionSensorData;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class DalKNXActuatorCheck extends javax.swing.JFrame {

    private static final double MINIMAL_CONSUMPTION = 1d;
    private static final long UPDATE_DELAY = 1000;

    protected static final Logger LOGGER = LoggerFactory.getLogger(DalKNXActuatorCheck.class);

    private TriggerFilter verifyConsumptionTrigger;

    /**
     * Creates new form DalKNXActuatorCheck
     *
     * @throws org.openbase.jul.exception.InstantiationException
     */
    public DalKNXActuatorCheck() throws org.openbase.jul.exception.InstantiationException {
        try {
            initComponents();
        } catch (Exception ex) {
            throw new org.openbase.jul.exception.InstantiationException(this, ex);
        }
    }
    
    /**
     * 
     * @throws InitializationException 
     */
    public void init() throws InitializationException, InterruptedException {
        try {
            verifyConsumptionTrigger = new TriggerFilter("VerifyConsumptionTrigger", UPDATE_DELAY) {

                @Override
                public void internalTrigger() {
                    verifyConsumption();
                }
            };

            colorPanel.setBackground(Color.BLUE);
            stateLabel.setText("Loading Remotes");

            genericUnitCollectionPanel.init();

            List<String> unitLabelList = new ArrayList<>();
            unitLabelList.add("A1C2Consumption");
            unitLabelList.add("A2C2Consumption");
            unitLabelList.add("A3C2Consumption");
            unitLabelList.add("A4C1Consumption");
            unitLabelList.add("A5C4Consumption");
            unitLabelList.add("A6C4Consumption");
            unitLabelList.add("A7C4Consumption");
//        unitLabelList.add("A8C3Consumption");
            unitLabelList.add("A9C5Consumption");

            Observer<PowerConsumptionSensorData> consumptionUpdateObserver = (Observable<PowerConsumptionSensorData> source, PowerConsumptionSensorData data) -> {
                verifyConsumptionTrigger.trigger();
            };

            for (GenericUnitPanel unitPanel : (Collection<GenericUnitPanel>) genericUnitCollectionPanel.add(unitLabelList)) {
                unitPanel.getRemoteService().addDataObserver(consumptionUpdateObserver);
            }

            verifyConsumptionTrigger.start();
            verifyConsumptionTrigger.trigger();

        } catch (InitializationException | NotAvailableException ex) {
            throw new InitializationException(this, ex);
        }
    }

    private synchronized void verifyConsumption() {
        Future<Void> future = GlobalCachedExecutorService.submit(new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                try {
                    String minimalActuator = "";
                    double consumption;
                    double minimalConsumption = Double.MAX_VALUE;
                    for (Entry<String, GenericUnitPanel<PowerConsumptionSensorRemote>> entry : (Set<Entry<String, GenericUnitPanel<PowerConsumptionSensorRemote>>>) genericUnitCollectionPanel.getUnitPanelMap().entrySet()) {
                        try {
                            consumption = entry.getValue().getRemoteService().getData().getPowerConsumptionState().getConsumption();
                            if (minimalConsumption > consumption) {
                                minimalConsumption = consumption;
                                minimalActuator = entry.getValue().getRemoteService().getData().getLabel();
                            }
                        } catch (NotAvailableException ex) {
                            throw new CouldNotPerformException("Could not detect power consumption of Unit[" + entry.getKey() + "]!");
                        }
                    }

                    final double minConsumption = minimalConsumption;
                    final String minActuator = minimalActuator;
                    SwingUtilities.invokeAndWait(new Runnable() {

                        @Override
                        public void run() {
                            if (minConsumption < MINIMAL_CONSUMPTION) {
                                colorPanel.setBackground(Color.RED);
                                stateLabel.setText("KNX failure detected for Actuator[" + minActuator + "]");
                            } else {
                                colorPanel.setBackground(Color.GREEN);
                                stateLabel.setText("KNX OK");
                            }
                        }
                    });

                } catch (CouldNotPerformException | InvocationTargetException ex) {
                    ExceptionPrinter.printHistory(ex, LOGGER, LogLevel.ERROR);
                    colorPanel.setBackground(Color.RED);
                    stateLabel.setText("Error during consumption verification!");
                }
                return null;
            }
        });

        statusPanel1.setStatus("Verify Consumption", StatusPanel.StatusType.INFO, future);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        statusPanel1 = new org.openbase.bco.dal.visual.util.StatusPanel();
        colorPanel = new javax.swing.JPanel();
        stateLabel = new javax.swing.JLabel();
        genericUnitCollectionPanel = new org.openbase.bco.dal.visual.unit.GenericUnitCollectionPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        statusPanel1.setBackground(new java.awt.Color(204, 204, 204));

        colorPanel.setBackground(new java.awt.Color(0, 102, 51));
        colorPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        stateLabel.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        stateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stateLabel.setText("Init GUI");
        stateLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout colorPanelLayout = new javax.swing.GroupLayout(colorPanel);
        colorPanel.setLayout(colorPanelLayout);
        colorPanelLayout.setHorizontalGroup(
            colorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(colorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(stateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        colorPanelLayout.setVerticalGroup(
            colorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(colorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(stateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 959, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(colorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(genericUnitCollectionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(colorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(genericUnitCollectionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            ExceptionPrinter.printHistory(ex, LOGGER, LogLevel.ERROR);
            System.exit(1);
        }
        //</editor-fold>
        //</editor-fold>

        JPService.setApplicationName("dal-knx-actuator-ckeck");
        JPService.parseAndExitOnError(args);

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                final DalKNXActuatorCheck dalKNXActuatorCheck = new DalKNXActuatorCheck();
                dalKNXActuatorCheck.setVisible(true);
                dalKNXActuatorCheck.pack();

                new Thread() {
                    @Override
                    public void run() {
                        try {
                            dalKNXActuatorCheck.init();
                            dalKNXActuatorCheck.pack();
                        } catch (InitializationException | InterruptedException ex) {
                            ExceptionPrinter.printHistory(ex, LOGGER, LogLevel.ERROR);
                            System.exit(1);
                        }
                    }
                }.start();

            } catch (Exception ex) {
                ExceptionPrinter.printHistory(ex, LOGGER, LogLevel.ERROR);
                System.exit(1);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel colorPanel;
    private org.openbase.bco.dal.visual.unit.GenericUnitCollectionPanel genericUnitCollectionPanel;
    private javax.swing.JLabel stateLabel;
    private org.openbase.bco.dal.visual.util.StatusPanel statusPanel1;
    // End of variables declaration//GEN-END:variables
}
