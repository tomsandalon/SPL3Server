package bgu.spl.net.srv.StompServices;

import bgu.spl.net.StompObject.Client.*;
import bgu.spl.net.StompObject.Server.Connected;
import bgu.spl.net.StompObject.Server.Error;
import bgu.spl.net.api.MessagingProtocol;

public class StompMessagingProtocol implements MessagingProtocol<String> {

    private boolean shouldTerminate = false;

    @Override
    public String process(String msg) {
        String type = msg.substring(msg.indexOf('\n'));
        String result;
        switch (type) {
            case "CONNECT":
                return process(new Connect(msg));
            case "DISCONNECT":
                shouldTerminate = true;
                result = process(new Disconnect(msg));
                //TODO
                break;
            case "SEND":
                result = process(new Send(msg));
                //TODO
                break;
            case "SUBSCRIBE":
                result = process(new Subscribe(msg));
                //TODO
                break;
            case "UNSUBSCRIBE":
                result = process(new Unsubscribe(msg));
                //TODO
                break;
            default:
                //Error
                //TODO
        }
        return null;
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }

    private String process(Connect connect) {
        String s = ServerData.getInstance().login(connect.getAcceptVersion(), connect.getLogin(), connect.getPasscode());
        if (s.equals("CONNECTED")) {
            Connected connected = new Connected(connect.getAcceptVersion());
            return connected.toString();
        } else {
            Error error = new Error("Connect has no receipt id", s, connect, "s");
            return error.toString();
        }
    }

    private String process(Disconnect disconnect) { //TODO
        return null;
    }

    private String process(Send send) { //TODO
        return null;
    }

    private String process(Subscribe subscribe) { //TODO
        return null;
    }

    private String process(Unsubscribe unsubscribe) { //TODO
        return null;
    }
}
