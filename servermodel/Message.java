package servermodel;

// json the message/ text, user, request type if public or private
public class Message {
    private String text;
    private String username;
    private boolean isPrivateRequest;

    public Message(String text, String username) {  // constructor
        this.text = text;
        this.username = username;
        this.isPrivateRequest = false;  // always public
    }

    public Message(String text, String username, boolean isPrivateRequest) {  // again?
        this.text = text;
        this.username = username;
        this.isPrivateRequest = isPrivateRequest;
    }

    public boolean isPrivateRequest() {
        return isPrivateRequest;
    }

    public String getText() {
        return text;
    }

    public String getUsername() {
        return username;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return getUsername() + ": " + getText();
    }
}
