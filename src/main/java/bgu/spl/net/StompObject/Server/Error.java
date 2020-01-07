package bgu.spl.net.StompObject.Server;

import bgu.spl.net.StompObject.StompMessage;

public class Error extends StompMessage{

    private String receiptID;
    private String message;
    private StompMessage stompMessage;
    private String messageDesc;

    public String getReceiptID() {
        return receiptID;
    }

    public String getMessage() {
        return message;
    }

    public StompMessage getStompMessage() {
        return stompMessage;
    }

    public String getMessageDesc() {
        return messageDesc;
    }

    public Error(String receiptID, String message, StompMessage stompMessage, String messageDesc) {
        super("ERROR");
        this.receiptID = receiptID;
        this.message = message;
        this.stompMessage = stompMessage;
        this.messageDesc = messageDesc;
    }

    @Override
    public String toString() {
        return getType() + "\nreceipt-id: " + getReceiptID() + "\nmessage: " + getMessage() + "\n\nThe message:\n-----\n" + stompMessage.toString() + "\n-----\n" + getMessageDesc();
    }
}
