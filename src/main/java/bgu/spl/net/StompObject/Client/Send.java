package bgu.spl.net.StompObject.Client;

import bgu.spl.net.StompObject.StompMessage;

import java.util.ArrayList;
import java.util.Arrays;

public class Send extends StompMessage implements ClientStompMessage {
    private String dest;
    private String msg;

    public String getDest() {
        return dest;
    }

    public String getMsg() {
        return msg;
    }

    public Send(String completeMsg) {
        super("SEND");
        ArrayList<String> tempStringArray = new ArrayList<>();
        tempStringArray.addAll(Arrays.asList(completeMsg.split("\n")));
        dest = getAfterChar(tempStringArray.get(1), ':');
        msg = "";
        int i = 3;
        while (i < tempStringArray.size()){
            msg = msg + tempStringArray.get(i);
            if (tempStringArray.get(i).equals("^@")) {
                break;
            } else {
                msg = msg + "\n";
            }
        }
    }

    @Override
    public String toString() {
        return getType() + "\ndestination" + getDest() + "\n\n" + getMsg() + endOfStomp();
    }

    @Override
    public boolean isValid(String s) {
        ArrayList<String> tempStringArray = toArrayList(s);
        if (!tempStringArray.get(1).startsWith("destination:")) return false;
        if (!tempStringArray.get(2).equals("")) return false;
        return tempStringArray.get(tempStringArray.size() - 1).equals(endOfStomp());
    }
}