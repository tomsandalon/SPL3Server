package bgu.spl.net.srv;

public interface Connections<T> {

    //new
    Connections<T> getInstance(); //made it singleton

    boolean send(int connectionId, T msg);

    void send(String channel, T msg);

    void disconnect(int connectionId);
}
