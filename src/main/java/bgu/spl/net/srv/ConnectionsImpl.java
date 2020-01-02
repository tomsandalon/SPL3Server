package bgu.spl.net.srv;

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

    public synchronized boolean send(int connectionId, T msg) {
        ConnectionHandler<T> client = activeConnections.get(connectionId);
        try {
            client.send(msg);
            return true;
        } catch (Exception e) { //TODO fix exception
            return false;
        }
    }

    private static class ConnectionHolder {
        private static ConnectionsImpl instance = new ConnectionsImpl<>();
    }
}
