package servermodel;

import mediator.ChatClientHandler;
import utility.observer.javaobserver.UnnamedPropertyChangeSubject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class Log {
    private ArrayList<LogLine> logs;
    private static Log log;
    private static Object lock = new Object();

    private Log() {
        this.logs = new ArrayList<>();
    }

    public static Log getInstance() {
        if (log == null) {
            synchronized (lock) {
                if (log == null) {
                    log = new Log();
                }
            }
        }
        return log;
    }

    public void addLog(Message message) {
        String text = message.getUsername() + ": " + message.getText();
        LogLine temp = new LogLine(text);
        logs.add(temp);
        System.out.println("Log: " + temp);
        addToFile(temp);
    }

    public ArrayList<LogLine> getAll() {
        return logs;
    }

    @Override
    public String toString() {
        return "Log{" + "logs=" + logs + '}';
    }

    private void addToFile(LogLine log) {
        if (log == null) {
            return;
        }
        BufferedWriter out = null;
        try {
            String filename = "Log-" + log.getDateTime().getSortableDate() + ".txt";
            out = new BufferedWriter(new FileWriter(filename, true));
            out.write(log + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
