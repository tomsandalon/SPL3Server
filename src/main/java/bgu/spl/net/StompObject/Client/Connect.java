package bgu.spl.net.StompObject.Client;

import bgu.spl.net.StompObject.StompMessage;

public class Connect extends StompMessage {

    private String acceptVersion;
    private String host;
    private String login;
    private String password;

    public String getAcceptVersion() {
        return acceptVersion;
    }

    public String getHost() {
        return host;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Connect(String completeMessage){
        super("CONNECT");
        acceptVersion = getAfterChar(getHeaderAt(1), ':');
        host = getAfterChar(getHeaderAt(2), ':');
        login = getAfterChar(getHeaderAt(3), ':');
        password = getAfterChar(getHeaderAt(4), ':');
    }
}
