package bgu.spl.net.StompObject.Client;

import bgu.spl.net.StompObject.StompMessage;

import java.util.ArrayList;
import java.util.Arrays;

public class Subscribe extends StompMessage {

    private String dest;
    private String id;

    public Subscribe(String completeMsg) {
        super("SUBSCRIBE");
        ArrayList<String> tempStringArray = new ArrayList<>();
        tempStringArray.addAll(Arrays.asList(completeMsg.split("\n")));
        dest = getAfterChar(tempStringArray.get(1), ':');
        id = getAfterChar(tempStringArray.get(2), ':');
    }

    public String getDest() {
        return dest;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return getType() + "\ndestination:" + getDest() + "\nid:" + getId() + "\n";
    }

    public static String isValid(String s) {
        ArrayList<String> tempStringArray = toArrayList(s);
        if (!tempStringArray.get(1).startsWith("destination:")) return "destination is invalid";
        if (!tempStringArray.get(2).startsWith("id:")) return "id is invalid";
        return "";
    }
}