package bgu.spl.net.srv.BookStoreObjects;

import java.util.LinkedList;
import java.util.List;

public class Users { //singleton class holding the list of the current users

    List<User> usersList;

    private Users() {
        usersList = new LinkedList<>();
    }

    public static Users getInstance() {
        return userListHolder.instance;
    }

    public List<User> getUsersList() {
        return usersList;
    }

    public void addUser(User u) {
        usersList.add(u);
    }

    private static class userListHolder {
        private static Users instance = new Users();
    }
}
