package bgu.spl.net.StompObject.Client;

import bgu.spl.net.StompObject.StompMessage;

import java.util.ArrayList;

public class Send extends StompMessage {
    private String dest;
    private String msg;

    public Send(String completeMsg) {
        super("SEND");
        ArrayList<String> tempStringArray = new ArrayList<>();
        for (String s : completeMsg.split("\n")){
            tempStringArray.add(s);
        }
        dest = getAfterChar(tempStringArray.get(1), ':');
        msg = "";
        i = 3;
        while (i < tempStringArray.size()){
            msg = msg + tempStringArray.get(i);
            if (tempStringArray.get(i).equals("^@")){
                break;
            }
            else {
                msg = msg + "\n";
            }
        }
    }


}
