package mediator;

import servermodel.Message;
import utility.observer.javaobserver.UnnamedPropertyChangeSubject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class BroadcastSupport implements UnnamedPropertyChangeSubject {
    private PropertyChangeSupport property;

    public BroadcastSupport() {
        property = new PropertyChangeSupport(this);
    }

    public void sendBroadcast(Message message) {
        property.firePropertyChange("Message", null, message);
    }

    @Override
    public void addListener(PropertyChangeListener listener) {
        property.addPropertyChangeListener(listener);
    }

    @Override
    public void removeListener(PropertyChangeListener listener) {
        property.removePropertyChangeListener(listener);
    }
}
