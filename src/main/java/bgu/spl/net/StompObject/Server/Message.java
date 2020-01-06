package bgu.spl.net.StompObject.Server;

import bgu.spl.net.StompObject.StompMessage;

import java.util.ArrayList;

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
}
