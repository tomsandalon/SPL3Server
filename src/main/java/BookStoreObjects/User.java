package BookStoreObjects;

import java.util.List;

public class User {

    private String username;
    private String password;
    private boolean isConnected;

    private User(String username, String password) {
        this.username = username;
        this.password = password;
        Users u = Users.getInstance();
        u.addUser(this);
    }

    public boolean isConnected() {
        return isConnected;
    }

    public User getUser(String username, String password) throws Exception {
        Users u = Users.getInstance();
        List<User> users = u.getUsersList();
        for (User user : users) { //check each existing user
            if (user.username.equals(username)) { //if the username equals
                if (user.password.equals(password)) { //if the password equals
                    if (user.isConnected()) {
                        throw new Exception("“User already logged in”");
                    }
                    user.Login(); //if successful
                    return user;
                } else
                    throw new Exception("Wrong password");
            }
        }
        //if no matching user if found
        User newUser = new User(username, password);
        newUser.Login();
        return newUser;
    }

    private void Login() {
        this.isConnected = true;
    }

    private void Logout() {
        this.isConnected = false;
        Genres.getInstance().unsubToAll(this);
    }

}
