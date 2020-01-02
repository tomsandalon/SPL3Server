package bgu.spl.net.srv.BookStoreObjects;

import java.util.LinkedList;
import java.util.List;

public class Genre {
    private String name;
    private List<User> subs;
    private List<Book> books;


    public Genre(String name) {
        this.name = name;
        this.subs = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public synchronized void subUser(User u) {
        if (containsSubUser(u))
            subs.add(u);
    }

    public synchronized boolean containsSubUser(User u) {
        return subs.contains(u);
    }

    public synchronized void unsubUser(User u) {
        if (containsSubUser(u))
            subs.remove(u);
    }

    //TODO implement the borrow book command

    public void returnBook(Book b) {
        b.returnToPreviousHolder();
    }
}
