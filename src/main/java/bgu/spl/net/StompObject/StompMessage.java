package bgu.spl.net.StompObject;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class StompMessage {

    private String type;

    public String getType() {
        return type;
    }


    public StompMessage (String type){
        this.type = type;
    }

    public String getAfterChar(String s, char c) {
        int loc = s.indexOf(c) + 1;
        return s.substring(loc);
    }

    @Override
    public abstract String toString();

    public static ArrayList<String> toArrayList(String s) {
        return new ArrayList<>(Arrays.asList(s.split("\n")));
    }
}
