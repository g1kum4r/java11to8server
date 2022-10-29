package mediator;

//do work class; thread
//gson is here

import com.google.gson.Gson;
import servermodel.Log;
import servermodel.Message;  // where json is

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.Socket;

public class ChatClientHandler implements Runnable, PropertyChangeListener {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Gson gson;
    private ChatServer server;
    private BroadcastSupport support;

    public ChatClientHandler(Socket socket, ChatServer server, BroadcastSupport support)
            throws IOException {
        this.socket = socket;
        this.server = server;
        gson = new Gson();  // instance
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        this.support = support;
        support.addListener(this);
    }

    public void run() {
        while (true) {
            try {
                String reply = in.readLine();
                if (reply != null) {
                    Message receivedMessage = gson.fromJson(reply, Message.class);  // Message class for json action
                    System.out.println(
                            "ChatClientHandler: " + receivedMessage.getUsername() + ": " + receivedMessage.getText());
                    //Debugging^^
                    if (receivedMessage.isPrivateRequest()) {
                        Message privateRequest = new Message(
                                "This is the number of users: " + server.getUserCount(),
                                "SERVER", true);
                        String toJson = gson.toJson(privateRequest);
                        out.println(toJson);
                    } else {
                        if (Log.getInstance() == null) {
                            System.out.println("ChatClientHandler: Log is null.");
                        }
                        Log.getInstance().addLog(receivedMessage);
                        support.sendBroadcast(receivedMessage);
                    }
                }
            } catch (IOException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                //System.out.println("Trouble in ChatClientHandler.");
                //We are not doing anything as we have to wait for answer all the time,
                //but this throws exceptions.
            }
        }
    }

    public void close() throws IOException {
        socket.close();
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Message message = ((Message) evt.getNewValue());
        String toJson = gson.toJson(message);
        out.println(toJson);
    }
}
