package bgu.spl.net.StompObject.Client;

import bgu.spl.net.StompObject.StompMessage;

import java.util.ArrayList;
import java.util.Arrays;

public class Subscribe extends StompMessage implements ClientStompMessage {

    private String dest;
    private String id;

    public Subscribe(String completeMsg) {
        super("SUBSCRIBE");
        ArrayList<String> tempStringArray = new ArrayList<>();
        tempStringArray.addAll(Arrays.asList(completeMsg.split("\n")));
        dest = getAfterChar(tempStringArray.get(1), ':');
        id = getAfterChar(tempStringArray.get(1), ':');
    }

    public String getDest() {
        return dest;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return getType() + "\ndestination:" + getDest() + "\nid:" + getId() + "\n" + endOfStomp();
    }

    @Override
    public boolean isValid(String s) {
        ArrayList<String> tempStringArray = toArrayList(s);
        if (!tempStringArray.get(1).startsWith("destination:")) return false;
        if (!tempStringArray.get(2).startsWith("id:")) return false;
        if (!tempStringArray.get(3).equals("")) return false;
        return tempStringArray.get(4).equals(endOfStomp());
    }
}