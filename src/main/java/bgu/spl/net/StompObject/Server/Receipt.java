package bgu.spl.net.StompObject.Server;

import bgu.spl.net.StompObject.StompMessage;

import java.util.ArrayList;

public class Receipt extends StompMessage {

    private String receiptID;

    public Receipt(String receiptID){
        super("RECEIPT");
        this.receiptID = receiptID;
    }

    public String getReceiptID() {
        return receiptID;
    }
}
