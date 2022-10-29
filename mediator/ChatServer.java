package mediator;

//connection class

import java.io.IOException;
import java.net.ServerSocket;

public class ChatServer implements Runnable {
    private int port;
    private ServerSocket welcomeSocket;
    private int userCount;
    private BroadcastSupport support;

    public ChatServer(int port) throws IOException {
        this.port = port;
        welcomeSocket = new ServerSocket(port);
        userCount = 0;
        support = new BroadcastSupport();
    }

    public void close() throws IOException {
        welcomeSocket.close();
    }

    @Override
    public void run() {
        System.out.println("Starting the server...");
        System.out.println("Waiting for a clients...");
        while (true) {

            try {
                ChatClientHandler handler = new ChatClientHandler(welcomeSocket.accept(), this, support);
                Thread thread = new Thread(handler);
                thread.start();
                userCount++;
                System.out.println("Client successfully connected.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getUserCount() {
        return userCount;
    }
}
