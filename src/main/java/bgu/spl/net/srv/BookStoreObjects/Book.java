package bgu.spl.net.srv.BookStoreObjects;

import java.util.Stack;

public class Book {
    private String name;
    private Stack<User> owners;
    private User originalOwner;

    public Book(String name, User originalOwner) {
        this.name = name;
        owners = new Stack<>();
        this.originalOwner = originalOwner;
        owners.push(originalOwner);
    }

    public String getName() {
        return name;
    }

    public void setHolder(User u) {
        owners.push(u);
    }

    public void returnToPreviousHolder() {
        if (owners.peek() != originalOwner)
            owners.pop();
    }
}
