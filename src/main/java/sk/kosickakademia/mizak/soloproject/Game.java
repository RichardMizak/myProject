package sk.kosickakademia.mizak.soloproject;

import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Game {
private String game;
private String genre;
private int id;

    public Game(String game, String genre) {
        this.game = game;
        this.genre = genre;

    }

    public Game(int id, String game, String genre) {
        this.game = game;
        this.genre = genre;
        this.id = id;
    }

    public String getGame() {
        return game;
    }

    public String getGenre() {
        return genre;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return id+" "+game+" "+genre;
    }
}
