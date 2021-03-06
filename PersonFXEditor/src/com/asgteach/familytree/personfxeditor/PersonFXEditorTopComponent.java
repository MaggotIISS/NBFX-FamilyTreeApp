/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asgteach.familytree.personfxeditor;

import com.asgteach.familytree.capabilityinterfaces.SavablePersonCapability;
import com.asgteach.familytree.editorinterfaces.PersonEditor;
import com.asgteach.familytree.editorinterfaces.PersonEditorManager;
import com.asgteach.familytree.model.FamilyTreeManager;
import com.asgteach.familytree.model.Person;
import com.asgteach.familytree.model.Picture;
import com.asgteach.familytree.utilities.PersonCapability;
import com.asgteach.familytree.utilities.PersonCapabilityNode;
import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javax.swing.SwingWorker;
import org.netbeans.api.settings.ConvertAsProperties;
import org.netbeans.spi.actions.AbstractSavable;
import org.openide.DialogDisplayer;
import org.openide.LifecycleManager;
import org.openide.NotifyDescriptor;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ServiceProvider;
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//com.asgteach.familytree.personfxeditor//PersonFXEditor//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "PersonFXEditorTopComponent",
        iconBase = "com/asgteach/familytree/utilities/personIcon.png",
        persistenceType = TopComponent.PERSISTENCE_NEVER)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@Messages({
    "CTL_PersonFXEditorTopComponent=PersonEditor Window",
    "HINT_PersonFXEditorTopComponent=This is a PersonFXEditor window",
    "CTL_PersonFXEditorSaveDialogTitle=Unsaved Data",
    "# {0} - person",
    "CTL_PersonFXEditorSaveDialogMsg=Person {0} has Unsaved Data. \nSave Person?"
})
@ServiceProvider(service = PersonEditor.class, 
        supersedes = "com.asgteach.familytree.personeditor.PersonEditorTopComponent.class")
public final class PersonFXEditorTopComponent extends TopComponent implements
        PersonEditor, PropertyChangeListener {

    private PersonCapability personCapability;
    private Person person;
    private PersonCapabilityNode personNode;
    private static JFXPanel fxPanel;
    private PersonFXEditorController controller;
    private FamilyTreeManager ftm;
    private boolean readyToListen = false;
    private boolean noUpdate;
    InstanceContent ic = new InstanceContent();
    private static final Logger logger = Logger.getLogger(PersonFXEditorTopComponent.class.getName());

    public PersonFXEditorTopComponent() {
        initComponents();
        setName(Bundle.CTL_PersonFXEditorTopComponent());
        setToolTipText(Bundle.HINT_PersonFXEditorTopComponent());

        associateLookup(new AbstractLookup(ic));
        setLayout(new BorderLayout());
        init();
    }

    private void init() {
        fxPanel = new JFXPanel();
        add(fxPanel, BorderLayout.CENTER);
        Platform.setImplicitExit(false);
        Platform.runLater(this::createScene);
    }

    private void createScene() {
        try {
            URL location = getClass().getResource("PersonFXEditor.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

            Parent root = (Parent) fxmlLoader.load(location.openStream());
            Scene scene = new Scene(root);
            fxPanel.setScene(scene);
            String cssURL = "personfxEditorStyleSheet.css";
            String css = this.getClass().getResource(cssURL).toExternalForm();
            scene.getStylesheets().add(css);
            controller = (PersonFXEditorController) fxmlLoader.getController();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
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
        setName(person.toString());
        this.ftm = Lookup.getDefault().lookup(FamilyTreeManager.class);
        if (ftm == null) {
            logger.log(Level.SEVERE, "Cannot get FamilyTreeManager object");
            LifecycleManager.getDefault().exit();
        }
        ftm.addPropertyChangeListener(this);
        controller.addPropertyChangeListener(this);
    }

    @Override
    public void componentClosed() {
        controller.removePropertyChangeListener(this);
        ftm.removePropertyChangeListener(this);
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
    public void setNode(Node node) {
        if (node instanceof PersonCapabilityNode) {
            this.personNode = (PersonCapabilityNode) node;
            personCapability = personNode.getLookup().lookup(PersonCapability.class);
            this.person = personNode.getLookup().lookup(Person.class);
            // Add personNode to the lookup
            ic.add(personNode);
            logger.log(Level.FINER, "Editing Person {0}", person);
            updateForm();
            updatePicture(person);
        }
    }

    @Override
    public TopComponent getTopComponent() {
        return PersonFXEditorTopComponent.this;
    }

    private void modify() {
        if (getLookup().lookup(PersonFXEditorTopComponent.SavableViewCapability.class) == null) {
            ic.add(new PersonFXEditorTopComponent.SavableViewCapability());
        }
    }

    private void updateForm() {
        logger.log(Level.FINER, "updateForm");
        this.readyToListen = false;
        this.noUpdate = true;
        clearSaveCapability();
        // Invoke a method in the controller using the JavaFX
        // Application thread and wait
        final CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Update the form in the controller
            // Must be in JavaFX Application Thread
            try {
                controller.updateForm(person);
            } finally {
                latch.countDown();
            }
        });
        try {
            latch.await();
            // when done do this:
            this.noUpdate = false;
            this.readyToListen = true;
        } catch (InterruptedException ex) {
            logger.log(Level.WARNING, null, ex);
        }
    }

    private void updatePicture(final Person person) {
        Platform.runLater(() -> {
            controller.updatePicture(person);
        });

    }

    private void updatePerson() {
        if (this.noUpdate) {
            return;
        }
        // Invoke a method in the controller in the JavaFX Application
        // thread to read the controls and update person
        // wait for it to finish
        final CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // update the form the controller
            // must be in JavaFX Application Thread
            try {
                person = controller.updatePerson(person);
            } finally {
                latch.countDown();
            }
        });
        try {
            latch.await();
            // when done do this:
            setName(person.toString());
        } catch (InterruptedException ex) {
            logger.log(Level.WARNING, null, ex);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        switch (pce.getPropertyName()) {
            case FamilyTreeManager.PROP_PERSON_UPDATED:
                // we only care about this to update the tab name
                if (pce.getOldValue() != null
                        && Objects.equals(((Person) pce.getOldValue()).getId(), person.getId())) {
                    person = (Person) pce.getOldValue();
                    WindowManager.getDefault().invokeWhenUIReady(() -> {
                        setName(person.toString());
                    });
                }   break;
            case FamilyTreeManager.PROP_IMAGE_STORED:
                // Update the person's image, it has changed
                if (pce.getOldValue() != null
                        && Objects.equals(((Picture) pce.getOldValue()).getPerson().getId(), person.getId())) {
                    updatePicture(person);
            }   break;
            case FamilyTreeManager.PROP_PERSON_DESTROYED:
                if (pce.getOldValue() != null
                        && Objects.equals(((Person) pce.getOldValue()).getId(), person.getId())) {
                    logger.log(Level.FINER, "detected Person destroyed for {0}", person);
                    clearSaveCapability();
                    PersonEditorManager pem = Lookup.getDefault().lookup(PersonEditorManager.class);
                    if (pem != null) {
                        pem.unregisterEditor(person);
                        PersonFXEditorTopComponent.shutdown(this);
                }
            }   break;
            case PersonFXEditorController.PROP_PERSONEDITOR_MODIFIED:
                if (readyToListen) {
                modify();
            }   break;
        }
    }

    @Override
    public boolean canClose() {
        PersonFXEditorTopComponent.SavableViewCapability savable = getLookup().lookup(PersonFXEditorTopComponent.SavableViewCapability.class);
        if (savable == null) {
            return true;
        }
        String msg = Bundle.CTL_PersonFXEditorSaveDialogMsg(person.toString());
        NotifyDescriptor nd = new NotifyDescriptor.Confirmation(msg, NotifyDescriptor.YES_NO_CANCEL_OPTION);
        nd.setTitle(Bundle.CTL_PersonFXEditorSaveDialogTitle());
        Object result = DialogDisplayer.getDefault().notify(nd);
        if (result == NotifyDescriptor.CANCEL_OPTION || result == NotifyDescriptor.CLOSED_OPTION) {
            return false;
        }
        if (result == NotifyDescriptor.NO_OPTION) {
            clearSaveCapability();
            return true;
        }
        try {
            clearSaveCapability();
            savable.handleSave();
            return true;
        } catch (IOException ex) {
            logger.log(Level.WARNING, null, ex);
            return false;
        }
    }

    private void clearSaveCapability() {
        SavableViewCapability svc = getLookup().lookup(SavableViewCapability.class);
        while (svc != null) {
            svc.removeSavable();
            this.ic.remove(svc);
            svc = this.getLookup().lookup(SavableViewCapability.class);
        }
    }

    private class SavableViewCapability extends AbstractSavable {

        SavableViewCapability() {
            register();
        }

        public void removeSavable() {
            unregister();
        }

        @Override
        protected String findDisplayName() {
            return person.toString();
//            return firstTF.getText() + " " + lastTF.getText();
        }

        @Override
        protected void handleSave() throws IOException {
            final SavablePersonCapability saveable =
                    personCapability.getLookup().lookup(SavablePersonCapability.class);
            tc().ic.remove(this);
            updatePerson();

            SwingWorker<Person, Void> worker = new SwingWorker<Person, Void>() {
                @Override
                public Person doInBackground() {
                    try {
                        saveable.save(person);
                    } catch (Exception e) {
                        logger.log(Level.WARNING, null, e);
                    }
                    return person;
                }

                @Override
                public void done() {
                    updateForm();
                }
            };
            worker.execute();
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof SavableViewCapability) {
                SavableViewCapability sv = (SavableViewCapability) other;
                return tc() == sv.tc();
            }
            return false;
        }

        @Override
        public int hashCode() {
            return tc().hashCode();
        }

        PersonFXEditorTopComponent tc() {
            return PersonFXEditorTopComponent.this;
        }
    }

    public static void shutdown(final TopComponent tc) {
        WindowManager.getDefault().invokeWhenUIReady(tc::close);
    }
}
