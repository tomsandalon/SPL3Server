package bgu.spl.net.StompObject.Server;

import bgu.spl.net.StompObject.StompMessage;

import java.util.ArrayList;

public class Connected extends StompMessage {

    private String version;

    public String getVersion() {
        return version;
    }

    public Connected(String version){
        super("CONNECTED");
        this.version = version;
    }
}
