package bgu.spl.net.StompObject.Server;

import bgu.spl.net.StompObject.StompMessage;

public class Message extends StompMessage {

    private String subscription;
    private String messageID;
    private String dest;
    private String message;

    public String getSubscription() {
        return subscription;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getDest() {
        return dest;
    }

    public String getMessage() {
        return message;
    }

    public Message(String subscription, String messageID, String dest, String message) {
        super("MESSAGE");
        this.subscription = subscription;
        this.messageID = messageID;
        this.dest = dest;
        this.message = message;
    }

    @Override
    public String toString() {
        return getType() + "\nsubscription:" + getSubscription() + "\nMessage-id:" + getMessageID() + "\ndestination:" + getDest() + "\n\n" + getMessage() + endOfStomp();
    }
}
