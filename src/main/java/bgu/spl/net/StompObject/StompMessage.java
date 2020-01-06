package bgu.spl.net.StompObject;

import java.util.ArrayList;

public abstract class StompMessage {

    private String type;

    public String getType() {
        return type;
    }


    public StompMessage (String type){
        this.type = type;
    }

    public String getAfterChar(String s, char c){
        int loc = s.indexOf(c);
        return s.substring(loc);
    }

    @Override
    public abstract String toString();

}
