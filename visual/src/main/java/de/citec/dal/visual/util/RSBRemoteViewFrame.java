/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.dal.visual.util;

import org.dc.jul.extension.rsb.com.RSBRemoteService;
import com.google.protobuf.GeneratedMessage;
import org.dc.jul.exception.CouldNotPerformException;
import org.dc.jul.exception.NotAvailableException;
import org.dc.jul.pattern.Observable;
import org.dc.jul.pattern.Observer;

/**
 *
 * @author mpohling
 * @param <M>
 */
public abstract class RSBRemoteViewFrame<M extends GeneratedMessage> extends javax.swing.JFrame implements Observer {

    private final RSBRemoteService<M> remoteService;
    /**
     * Creates new form RSBViewService
     */
    public RSBRemoteViewFrame() {
        initComponents();
        remoteService = null;
    }
    
    public RSBRemoteViewFrame(RSBRemoteService<M> remoteService) {
        this.remoteService = remoteService;
        remoteService.addObserver(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(Observable source, Object data) {
        updateDynamicComponents();
    }
    
    public M getData() throws CouldNotPerformException {
        return remoteService.getData();
    }
    
    protected abstract void updateDynamicComponents();
}
