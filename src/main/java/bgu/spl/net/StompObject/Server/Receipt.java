package bgu.spl.net.StompObject.Server;

import bgu.spl.net.StompObject.StompMessage;

public class Receipt extends StompMessage {

    private String receiptID;

    public Receipt(String receiptID) {
        super("RECEIPT");
        this.receiptID = receiptID;
    }

    public String getReceiptID() {
        return receiptID;
    }

    @Override
    public String toString() {
        return getType() + "\nreceipt-id:" + getReceiptID() + "\n" + endOfStomp();
    }
}
