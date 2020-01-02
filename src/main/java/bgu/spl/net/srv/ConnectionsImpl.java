package bgu.spl.net.srv;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConnectionsImpl<T> implements Connections<T> {

    Map<Integer, ConnectionHandler<T>> activeConnections;

    private ConnectionsImpl() {
        activeConnections = new HashMap<>();
    }

    public ConnectionsImpl getInstance() {
        return ConnectionHolder.instance;
    }

    public boolean send(int connectionId, T msg) {
        ConnectionHandler<T> client = activeConnections.get(connectionId);
        try {
            client.send(msg);
            return true;
        } catch (Exception e) { //TODO fix exception
            return false;
        }
    }

    public void send(String channel, T msg) { //TODO implement this

    }

    public void disconnect(int connectionId) {
        ConnectionHandler<T> client = activeConnections.get(connectionId);
        activeConnections.remove(client);
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ConnectionHolder {
        private static ConnectionsImpl instance = new ConnectionsImpl<>();
    }
}
