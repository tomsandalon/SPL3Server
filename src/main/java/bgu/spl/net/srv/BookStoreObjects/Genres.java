package bgu.spl.net.srv.BookStoreObjects;

import java.util.HashMap;
import java.util.Map;

public class Genres {

    private Map<String, Genre> genresList;

    private Genres() {
        genresList = new HashMap<>();
    }

    public static Genres getInstance() {
        return Genres.genreListHolder.instance;
    }

    public void addGenreIfNew(String name) {
        boolean found = false;
        for (String s : genresList.keySet()) {
            if (s.equals(name)) {
                found = true;
                break;
            }
        }
        if (!found) {
            genresList.put(name, new Genre(name));
        }
    }

    public void subToGenre(User u, String name) {
        addGenreIfNew(name);
        Genre g = genresList.get(name);
        g.subUser(u);
    }

    public void unsubToGenre(User u, Genre g) {
        g.unsubUser(u);
    }

    public void unsubToAll(User u) {
        for (Genre g : genresList.values()) {
            g.unsubUser(u);
        }
    }

    private static class genreListHolder {
        private static Genres instance = new Genres();
    }
}
