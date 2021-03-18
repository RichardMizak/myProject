package sk.kosickakademia.mizak.soloproject;

public class Game {
private String game;
private String genre;
private int id;


    public String getGame() {
        return game;
    }

    public String getGenre() {
        return genre;
    }

    public int getId() {
        return id;
    }

    public Game(String game, String genre, int id) {
        this.game = game;
        this.genre = genre;
        this.id = id;

    }
}
