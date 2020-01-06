package bgu.spl.net.StompObject.Client;

import bgu.spl.net.StompObject.Server.Receipt;
import bgu.spl.net.StompObject.StompMessage;

import java.util.ArrayList;

public class Disconnect extends StompMessage {

    private String receipt;

    public String getReceipt() {
        return receipt;
    }

    public Receipt(String completeMsg){
        super("DISCONNECT");
        ArrayList<String> tempStringArray = new ArrayList<>();
        for (String s : completeMsg.split("\n")){
            tempStringArray.add(s);
        }
        receipt = getAfterChar(tempStringArray.get(1), ':');
    }
}
