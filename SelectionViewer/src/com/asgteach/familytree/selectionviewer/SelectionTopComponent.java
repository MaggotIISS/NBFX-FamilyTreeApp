/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.selectionviewer;

import java.util.Collection;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.StatusDisplayer;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//org.asgteach.familytree.selectionviewer//Selection//EN",
autostore = false)
@TopComponent.Description(preferredID = "SelectionTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "output", openAtStartup = true)
@ActionID(category = "Window", id = "com.asgteach.familytree.selectionviewer.SelectionTopComponent")
@ActionReference(path = "Menu/Window" /*
 * , position = 333
 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_SelectionAction",
preferredID = "SelectionTopComponent")
@Messages({
    "CTL_SelectionAction=Selection Trail",
    "CTL_SelectionTopComponent=Selection Trail",
    "HINT_SelectionTopComponent=This is a Selection Trail window"
})
public final class SelectionTopComponent extends TopComponent implements
        LookupListener {

    private Lookup.Result<Node> result;

    public SelectionTopComponent() {
        initComponents();
        setName(Bundle.CTL_SelectionTopComponent());
        setToolTipText(Bundle.HINT_SelectionTopComponent());
        jScrollPane1.getViewport().setOpaque(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        selectionDisplay = new javax.swing.JTextArea();

        jScrollPane1.setOpaque(false);

        selectionDisplay.setColumns(20);
        selectionDisplay.setRows(5);
        selectionDisplay.setWrapStyleWord(true);
        selectionDisplay.setOpaque(false);
        jScrollPane1.setViewportView(selectionDisplay);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea selectionDisplay;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened() {
        result = Utilities.actionsGlobalContext().lookupResult(Node.class);
        result.addLookupListener(this);
    }

    @Override
    public void componentClosed() {
        result.removeLookupListener(this);
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Collection<? extends Node> nodes = result.allInstances();
        // only get the selection if there is one
        // otherwise leave the selection unchanged!
        
        // If you want the selection window to clear
        // when the user clicks on a non-node thingy,
        // then remove the test for isEmpty
        if (!nodes.isEmpty()) {
            StringBuilder status = new StringBuilder();
            StringBuilder sb = new StringBuilder();
            for (Node node : nodes) {
                sb.append(" [").append(node.getDisplayName()).append("]\n");
                status.append(" [").append(node.getDisplayName()).append("] ");
            }  
            StatusDisplayer.getDefault().setStatusText(status.toString());            
            selectionDisplay.setText(sb.insert(0, selectionDisplay.getText()).toString());
        }
    }
}
