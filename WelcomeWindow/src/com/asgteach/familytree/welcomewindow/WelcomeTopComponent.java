/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.welcomewindow;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
    dtd = "-//com.asgteach.familytree.welcomewindow//Welcome//EN",
autostore = false)
@TopComponent.Description(
    preferredID = "WelcomeTopComponent",
iconBase = "com/asgteach/familytree/welcomewindow/TimelineIcon.png",
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "com.asgteach.familytree.welcomewindow.WelcomeTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
    displayName = "#CTL_WelcomeAction",
preferredID = "WelcomeTopComponent")
@Messages({
    "CTL_WelcomeAction=Welcome",
    "CTL_WelcomeTopComponent=Welcome",
    "HINT_WelcomeTopComponent=This is a Welcome window"
})
public final class WelcomeTopComponent extends TopComponent  {

    private JFXPanel fxPanel;
    private WelcomeController controller;
    private static final Logger logger = Logger.getLogger(WelcomeTopComponent.class.getName());

    public WelcomeTopComponent() {
        initComponents();
        setName(Bundle.CTL_WelcomeTopComponent());
        setToolTipText(Bundle.HINT_WelcomeTopComponent());
        setLayout(new BorderLayout());

        init();

    }

    public void init() {
        fxPanel = new JFXPanel();
        add(fxPanel, BorderLayout.CENTER);
        Platform.setImplicitExit(false);
        Platform.runLater(this::createScene);
    }

    private void createScene() {
        try {
            URL location = getClass().getResource("Welcome.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

            Parent root = (Parent) fxmlLoader.load(location.openStream());
            Scene scene = new Scene(root);
            fxPanel.setScene(scene);
            String cssURL = "welcomeStyleSheet.css";
            String css = this.getClass().getResource(cssURL).toExternalForm();
            scene.getStylesheets().add(css);
            controller = (WelcomeController) fxmlLoader.getController();

        } catch (IOException ex) {
            logger.log(Level.WARNING, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        
    }

    @Override
    public void componentClosed() {
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

   
}
