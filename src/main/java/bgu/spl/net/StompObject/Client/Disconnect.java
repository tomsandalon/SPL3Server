package bgu.spl.net.StompObject.Client;

import bgu.spl.net.StompObject.StompMessage;

import java.util.ArrayList;
import java.util.Arrays;

public class Disconnect extends StompMessage {

    private String receipt;

    public String getReceipt() {
        return receipt;
    }

    public Disconnect(String completeMsg) {
        super("DISCONNECT");
        ArrayList<String> tempStringArray = new ArrayList<>();
        tempStringArray.addAll(Arrays.asList(completeMsg.split("\n")));
        receipt = getAfterChar(tempStringArray.get(1), ':');
    }

    @Override
    public String toString() {
        return getType() + "\nreceipt:" + getReceipt() + "\n";
    }

    public static String isValid(String s) {
        ArrayList<String> tempStringArray = toArrayList(s);
        if (!tempStringArray.get(1).startsWith("receipt:")) return "Invalid receipt";
        return "";
    }
}
