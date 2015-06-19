/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.dal.visual.unit;

import com.google.protobuf.GeneratedMessage;
import de.citec.dal.remote.unit.DALRemoteService;
import de.citec.dal.visual.service.AbstractServicePanel;
import de.citec.dal.visual.util.RSBRemoteView;
import de.citec.jul.exception.CouldNotPerformException;
import de.citec.jul.exception.ExceptionPrinter;
import de.citec.jul.pattern.Observable;
import de.citec.jul.pattern.Observer;
import de.citec.jul.processing.StringProcessor;
import de.citec.jul.visual.layout.LayoutGenerator;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rst.homeautomation.service.ServiceTypeHolderType.ServiceTypeHolder.ServiceType;
import rst.homeautomation.unit.UnitConfigType.UnitConfig;

/**
 *
 * @author mpohling
 */
public class GenericUnitPanel extends RSBRemoteView {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final Observer<UnitConfig> uniConfigObserver;

    /**
     * Creates new form AmbientLightView
     */
    public GenericUnitPanel() {
        super();
        this.uniConfigObserver = new Observer<UnitConfig>() {

            @Override
            public void update(Observable<UnitConfig> source, UnitConfig data) throws Exception {
                updateUnitConfig(data);
            }
        };
        initComponents();
    }

    public Observer<UnitConfig> getUnitConfigObserver() {
        return uniConfigObserver;
    }

    private void updateUnitConfig(UnitConfig unitConfig) throws CouldNotPerformException, InterruptedException {
        setUnitRemote(unitConfig);
        
        contextPanel.removeAll();
        List<JComponent> componentList = new ArrayList<>();
        JPanel servicePanel;

        for (ServiceType serviceType : unitConfig.getTemplate().getServiceTypeList()) {
            try {
                servicePanel = new JPanel();
                servicePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(StringProcessor.transformUpperCaseToCamelCase(serviceType.name())));
                servicePanel.add(instantiatServicePanel(loadServicePanelClass(serviceType), getRemoteService()));
                componentList.add(servicePanel);
            } catch (CouldNotPerformException ex) {
                ExceptionPrinter.printHistory(logger, new CouldNotPerformException("Could not load service panel for ServiceType[" + serviceType.name() + "]",ex ));
            }
        }
        
        String remoteLabel = StringProcessor.transformUpperCaseToCamelCase(unitConfig.getTemplate().getType().name())
                    + "[" + unitConfig.getLabel() + "]"
                    + " @ " + unitConfig.getPlacementConfig().getLocationId();
        
        setBorder(BorderFactory.createTitledBorder("Remote Control - "+remoteLabel));
        
        LayoutGenerator.designList(contextPanel, componentList);
        validate();
        revalidate();
    }

    private Class<? extends AbstractServicePanel> loadServicePanelClass(final ServiceType serviceType) throws CouldNotPerformException {
        String remoteClassName = AbstractServicePanel.class.getPackage().getName() + "." + StringProcessor.transformUpperCaseToCamelCase(serviceType.name()) + "Panel";
        try {
            return (Class<? extends AbstractServicePanel>) getClass().getClassLoader().loadClass(remoteClassName);
        } catch (Exception ex) {
            throw new CouldNotPerformException("Could not detect service panel class for ServiceType[" + serviceType.name() + "]", ex);
        }
    }

    private AbstractServicePanel instantiatServicePanel(Class<? extends AbstractServicePanel> servicePanelClass, DALRemoteService remoteService) throws de.citec.jul.exception.InstantiationException {
        try {
            AbstractServicePanel instance = servicePanelClass.newInstance();
            instance.initService(remoteService, remoteService);
            return instance;
        } catch (Exception ex) {
            throw new de.citec.jul.exception.InstantiationException("Could not instantiate service panel out of ServicePanelClass[" + servicePanelClass.getSimpleName() + "]!", ex);
        }
    }

    @Override
    protected void updateDynamicComponents(GeneratedMessage data) {

//               remoteView.setEnabled(false);
//        remoteView.setUnitConfig(unitConfig);
//        remoteView.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        contextPanel = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Remote Control"));

        jScrollPane1.setBorder(null);

        javax.swing.GroupLayout contextPanelLayout = new javax.swing.GroupLayout(contextPanel);
        contextPanel.setLayout(contextPanelLayout);
        contextPanelLayout.setHorizontalGroup(
            contextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 473, Short.MAX_VALUE)
        );
        contextPanelLayout.setVerticalGroup(
            contextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 389, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(contextPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contextPanel;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
