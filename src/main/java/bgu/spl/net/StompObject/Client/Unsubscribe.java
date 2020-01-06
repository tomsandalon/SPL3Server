package bgu.spl.net.StompObject.Client;

import bgu.spl.net.StompObject.StompMessage;

import java.util.ArrayList;

public class Unsubscribe extends StompMessage {

    private String id;

    public Unsubscribe(String completeMsg){
        super("UNSUBSCRIBE");
        ArrayList<String> tempStringArray = new ArrayList<>();
        for (String s : completeMsg.split("\n")){
            tempStringArray.add(s);
        }
        id = getAfterChar(tempStringArray.get(1), ':');
    }

    public String getId() {
        return id;
    }
}
