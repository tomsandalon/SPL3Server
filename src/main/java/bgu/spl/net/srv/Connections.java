package bgu.spl.net.srv;

import bgu.spl.net.srv.Utils.Pair;

import java.util.List;
import java.util.Map;

public interface Connections<T> {

    boolean send(int connectionId, T msg);

    void send(String channel, T msg);

    void disconnect(int connectionId);

    Map<Pair<String, String>, String> getConnectionSubId();

    String tryConnect(String acceptVersion, String username, String passcode, int connectionId);

    void subscribe(String dest, String id, int connectionId);

    void unsubscribe(String id, int connectionId);

    Map<Integer, ConnectionHandler<T>> getConnectionHandlerId();

    int getAndIncMsgCounter();

    int getAndIncConnectionCounter();

    List<Pair<String, Integer>> getUserConnectionId();
}
