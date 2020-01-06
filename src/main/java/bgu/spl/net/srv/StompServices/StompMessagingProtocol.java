package bgu.spl.net.srv.StompServices;

import bgu.spl.net.StompObject.Client.*;
import bgu.spl.net.api.MessagingProtocol;

public class StompMessagingProtocol implements MessagingProtocol<String> {

    private boolean shouldTerminate = false;

    @Override
    public String process(String msg) {
        String type = msg.substring(msg.indexOf('\n'));
        String result;
        switch (type) {
            case "CONNECT":
                result = process(new Connect(msg));
                //TODO
                break;
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
        return null;
    }

    private String process(Disconnect disconnect) {
        return null;
    }

    private String process(Send send) {
        return null;
    }

    private String process(Subscribe subscribe) {
        return null;
    }

    private String process(Unsubscribe unsubscribe) {
        return null;
    }
}
