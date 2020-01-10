package bgu.spl.net.srv.StompServices;

import bgu.spl.net.StompObject.Client.*;
import bgu.spl.net.StompObject.Server.Connected;
import bgu.spl.net.StompObject.Server.Error;
import bgu.spl.net.StompObject.Server.Message;
import bgu.spl.net.StompObject.Server.Receipt;
import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.srv.Connections;
import bgu.spl.net.srv.Utils.Pair;
import org.apache.commons.lang3.StringUtils;

public class StompMessagingProtocolImpl implements StompMessagingProtocol {

    private Connections<String> connections;
    private int connectionId;

    private boolean shouldTerminate = false;

    public static Message generateMessage(Message message, String subscriptionId) {
        return new Message(message.getSubscription(), message.getMessageID(), subscriptionId, message.getMessage());
    }

    public void start(int connectionId, Connections<String> connections) {
        this.connectionId = connectionId;
        this.connections = connections;
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }

    @Override
    public void process(String msg) {
        String type = msg.substring(0,msg.indexOf('\n'));
        String valid;
        String result;
        String receipt = StringUtils.substringBefore(StringUtils.substringAfter(msg, "receipt:"), "\n");
        switch (type) {
            case "CONNECT":
                result = process(new Connect(msg));
                connections.send(connectionId, result);
                break;
            case "DISCONNECT":
                valid = Disconnect.isValid(msg);
                if (valid.equals("")) {
                    result = process(new Disconnect(msg));
                    connections.send(connectionId, result);
                    shouldTerminate = true;
                    connections.disconnect(connectionId);
                } else {
                    sendError(valid, receipt, msg);
                }
                break;
            case "SEND":
                valid = Send.isValid(msg);
                if (valid.equals("")) {
                    process(new Send(msg));
                    if (msg.contains("receipt:")) {
                        connections.send(connectionId, new Receipt(receipt).toString());
                    }
                } else {
                    sendError(valid, receipt, msg);
                }
                break;
            case "SUBSCRIBE":
                valid = Subscribe.isValid(msg);
                if (valid.equals("")) {
                    process(new Subscribe(msg));
                    if (msg.contains("receipt:")) {
                        connections.send(connectionId, new Receipt(receipt).toString());
                    }
                } else {
                    sendError(valid, receipt, msg);
                }
                break;
            case "UNSUBSCRIBE":
                valid = Unsubscribe.isValid(msg);
                if (valid.equals("")) {
                    process(new Unsubscribe(msg));
                    if (msg.contains("receipt:")) {
                        connections.send(connectionId, new Receipt(receipt).toString());
                    }
                } else {
                    sendError(valid, receipt, msg);
                }
                break;
            default:
                //Error
                //TODO
        }
    }

    private void sendError(String valid, String receipt, String msg) {
        Error error = new Error(receipt, valid, msg, valid);
        connections.send(connectionId, error.toString());
    }

    private String process(Connect connect) {
        String s = connections.tryConnect(connect.getAcceptVersion(), connect.getLogin(), connect.getPasscode(), connectionId);
        if (s.equals("CONNECTED")) {
            Connected connected = new Connected(connect.getAcceptVersion());
            return connected.toString();
        } else {
            Error error = new Error("Connect has no receipt id", s, connect.toString(), "s");
            return error.toString();
        }
    }

    private String process(Disconnect disconnect) {
        return (new Receipt(disconnect.getReceipt())).toString();
    }

    private void process(Send send) {
        for (Pair<String, String> pair : connections.getConnectionSubId().keySet()) {
            if (pair.second.equals(send.getDest())) {
                for (Pair<String, Integer> connectionIds : connections.getUserConnectionId()) {
                    if (connectionIds.first.equals(pair.first)) {
                        Message m = new Message(connections.getConnectionSubId().get(pair), String.valueOf(connections.getAndIncMsgCounter()), send.getDest(), send.getMsg());
                        connections.send(connectionIds.second, m.toString());
                        break;
                    }
                }
            }
        }
    }

    private void process(Subscribe subscribe) {
        connections.subscribe(subscribe.getDest(), subscribe.getId(), connectionId);
    }

    private void process(Unsubscribe unsubscribe) {
        connections.unsubscribe(unsubscribe.getId(), connectionId);
    }
}