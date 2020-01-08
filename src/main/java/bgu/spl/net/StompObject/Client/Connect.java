package bgu.spl.net.StompObject.Client;

import bgu.spl.net.StompObject.StompMessage;

import java.util.ArrayList;
import java.util.Arrays;

public class Connect extends StompMessage implements ClientStompMessage {

    private String acceptVersion;
    private String host;
    private String login;
    private String passcode;

    public String getAcceptVersion() {
        return acceptVersion;
    }

    public String getHost() {
        return host;
    }

    public String getLogin() {
        return login;
    }

    public Connect(String completeMsg) {
        super("CONNECT");
        ArrayList<String> tempStringArray = new ArrayList<>();
        tempStringArray.addAll(Arrays.asList(completeMsg.split("\n")));
        acceptVersion = getAfterChar(tempStringArray.get(1), ':');
        host = getAfterChar(tempStringArray.get(2), ':');
        login = getAfterChar(tempStringArray.get(3), ':');
        passcode = getAfterChar(tempStringArray.get(4), ':');
    }

    public String getPasscode() {
        return passcode;
    }

    @Override
    public String toString() {
        return getType() + "\naccept-version:" + getAcceptVersion() + "\nhost:" + getHost() + "\nlogin:" + getLogin() + "\npasscode:" + getPasscode() + "\n";
    }

    @Override
    public String isValid(String s) {
        ArrayList<String> tempStringArray = toArrayList(s);
        if (!tempStringArray.get(1).startsWith("accept-version:")) return "Accept version is invalid";
        if (!tempStringArray.get(2).startsWith("host:")) return "Host is invalid";
        if (!tempStringArray.get(3).startsWith("login:")) return "login is invalid";
        if (!tempStringArray.get(4).startsWith("passcode:")) return "passcode is invalid";
        return "";
    }
}
