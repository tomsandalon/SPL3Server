package bgu.spl.net.srv;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConnectionsImpl<T> implements Connections<T> {

    Map<Integer, ConnectionHandler<T>> activeConnections;
    Map<String, List<Integer>> topicSubscribers;

    private ConnectionsImpl() {
        activeConnections = new HashMap<>();
        topicSubscribers = new HashMap<>();
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

    public void send(String channel, T msg) {
        List<Integer> subscribersId = topicSubscribers.get(channel);
        if (subscribersId != null) {
            for (Integer id : subscribersId) {
                send(id, msg);
            }
        }
    }

    public void disconnect(int connectionId) {
        ConnectionHandler<T> client = activeConnections.get(connectionId);
        activeConnections.remove(client);
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally { //now we remove the client from all of it's topics
            List<Integer> id = new LinkedList<>();
            id.add(connectionId);
            for (List<Integer> ids : topicSubscribers.values()) {
                ids.removeAll(id);
            }
        }
    }

    private static class ConnectionHolder {
        private static ConnectionsImpl instance = new ConnectionsImpl<>();
    }
}
