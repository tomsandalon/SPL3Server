package bgu.spl.net.srv.StompServices;

import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerData {

    private final String acceptVersion = "1.2";
    private ArrayList<Pair<String, String>> userCredentials;
    private ArrayList<String> loggedUsers;
    private HashMap<String, ArrayList<String>> destSubscribers;

    private ServerData() {
        userCredentials = new ArrayList<>();
        destSubscribers = new HashMap<>();
    }

    public static ServerData getInstance() {
        return ServerDataHolder.instance;
    }

    public String login(String acceptVersion, String username, String passcode) {
        if (!this.acceptVersion.equals(acceptVersion)) return "Wrong accept-version";
        for (Pair<String, String> user : userCredentials) {
            if (user.fst.equals(username)) {
                if (user.snd.equals(passcode)) {
                    if (loggedUsers.contains(username)) {
                        return "User already logged in";
                    } else {
                        loggedUsers.add(username);
                        return "CONNECTED";
                    }
                } else {
                    return "Wrong password";
                }
            }
        }
        userCredentials.add(new Pair<String, String>(username, passcode));
        loggedUsers.add(username);
        return "CONNECTED";
    }

    private static class ServerDataHolder {
        private static ServerData instance = new ServerData();
    }
}
