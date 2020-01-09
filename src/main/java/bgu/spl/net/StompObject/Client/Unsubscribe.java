package bgu.spl.net.StompObject.Client;

import bgu.spl.net.StompObject.StompMessage;

import java.util.ArrayList;
import java.util.Arrays;

public class Unsubscribe extends StompMessage {

    private String id;

    public Unsubscribe(String completeMsg) {
        super("UNSUBSCRIBE");
        ArrayList<String> tempStringArray = new ArrayList<>();
        tempStringArray.addAll(Arrays.asList(completeMsg.split("\n")));
        id = getAfterChar(tempStringArray.get(1), ':');
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return getType() + "\nid:" + getId() + "\n";
    }

    public static String isValid(String s) {
        ArrayList<String> tempStringArray = toArrayList(s);
        if (!tempStringArray.get(1).startsWith("id:")) return "id is invalid";
        return "";
    }
}
