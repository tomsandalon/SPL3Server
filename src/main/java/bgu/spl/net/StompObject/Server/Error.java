package bgu.spl.net.StompObject.Server;

import bgu.spl.net.StompObject.StompMessage;

public class Error extends StompMessage{

    private String receiptID;
    private String message;
    private String fullMessage;
    private String messageDesc;

    public String getReceiptID() {
        return receiptID;
    }

    public String getMessage() {
        return message;
    }

    public Error(String receiptID, String message, String fullMessage, String messageDesc) {
        super("ERROR");
        this.receiptID = receiptID;
        this.message = message;
        this.fullMessage = fullMessage;
        this.messageDesc = messageDesc;
    }

    public String getMessageDesc() {
        return messageDesc;
    }

    @Override
    public String toString() {
        return getType() + "\nreceipt-id: " + getReceiptID() + "\nmessage: " + getMessage() + "\n\nThe message:\n-----\n" + fullMessage + "\n-----\n" + getMessageDesc();
    }
}
