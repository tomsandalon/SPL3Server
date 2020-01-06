package bgu.spl.net.StompObject.Client;

import bgu.spl.net.StompObject.StompMessage;

import java.util.ArrayList;

public class Subscribe extends StompMessage {

    private String dest;
    private String id;

    public Subscribe(String completeMsg){
        super("SUBSCRIBE");
        ArrayList<String> tempStringArray = new ArrayList<>();
        for (String s : completeMsg.split("\n")){
            tempStringArray.add(s);
        }
        dest = getAfterChar(tempStringArray.get(1), ':');
        id = getAfterChar(tempStringArray.get(1), ':');
    }

    public String getDest() {
        return dest;
    }

    public String getId() {
        return id;
    }
}
