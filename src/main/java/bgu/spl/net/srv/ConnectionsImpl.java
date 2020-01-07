package bgu.spl.net.srv;

import bgu.spl.net.StompObject.Client.Unsubscribe;
import jdk.internal.net.http.common.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionsImpl<T> implements Connections<T> {

    private final String ACCEPT_VERSION = "1.2";
    private Map<Pair<String, String>, String> connectionSubId = new HashMap<>();
    private Map<String, String> userCredentials = new HashMap<>();
    private List<String> loggedUsers = new ArrayList<>();
    private List<Pair<String, Integer>> userConnectionId = new ArrayList<>();
    private Map<Integer, ConnectionHandler<T>> connectionHandlerId = new HashMap<>();
    private int msgSent = 0;

    public List<Pair<String, Integer>> getUserConnectionId() {
        return userConnectionId;
    }

    public synchronized int getAndIncMsgCounter() {
        this.msgSent++;
        return msgSent - 1;
    }

    @Override
    public boolean send(int connectionId, T msg) {
        ConnectionHandler<T> connectionHandler = connectionHandlerId.get(connectionId);
        try {
            connectionHandler.send(msg);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void send(String channel, T msg) { //we shouldn't use this function because it doesn't insert the subscription id into the msg
        for (Pair<String, String> pair : getConnectionSubId().keySet()) {
            if (pair.second.equals(channel)) {
                for (Pair<String, Integer> connectionIds : getUserConnectionId()) {
                    if (connectionIds.first.equals(pair.first)) {
                        send(pair.second, msg);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void disconnect(int connectionId) {
        String user = "";
        for (Pair<String, Integer> userConnectionIds : userConnectionId) {
            if (userConnectionIds.second == connectionId) {
                user = userConnectionIds.first;
                break;
            }
        }
        loggedUsers.remove(user);
        ConnectionHandler<T> connectionHandler = connectionHandlerId.get(connectionId);
        try {
            connectionHandler.close();
            connectionHandlerId.remove(connectionId);
        } catch (Exception ignore) {
        } //TODO
    }

    @Override
    public Map<Pair<String, String>, String> getConnectionSubId() {
        return connectionSubId;
    }

    @Override
    public String tryConnect(String acceptVersion, String username, String passcode, int connectionId) {
        if (!this.ACCEPT_VERSION.equals(acceptVersion)) return "Wrong accept-version";
        for (String user : userCredentials.keySet()) {
            if (user.equals(username)) {
                if (userCredentials.get(user).equals(passcode)) {
                    if (loggedUsers.contains(username)) {
                        return "User already logged in";
                    } else {
                        loggedUsers.add(username);
                        userConnectionId.add(new Pair<>(username, connectionId));
                        return "CONNECTED";
                    }
                } else {
                    return "Wrong password";
                }
            }
        }
        userCredentials.put(username, passcode);
        loggedUsers.add(username);
        userConnectionId.add(new Pair<>(username, connectionId));
        return "CONNECTED";
    }

    @Override
    public void subscribe(String dest, String id, int connectionId) {

    }

    @Override
    public void unsubscribe(Unsubscribe unsubscribe) {

    }

    @Override
    public Map<Integer, ConnectionHandler<T>> getConnectionHandlerId() {
        return connectionHandlerId;
    }
}
