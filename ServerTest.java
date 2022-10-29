import mediator.ChatServer;

import java.io.IOException;

public class ServerTest {
    public static void main(String[] args) {
        try {
            ChatServer server = new ChatServer(1122);
            Thread sT = new Thread(server);
            sT.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
