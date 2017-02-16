package myapp.anhtu.com.freakingmath.entity;

/**
 * Created by anhtu on 2/15/2017.
 */

public class Player {
    private String name;
    private int score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Player(String name, int score) {

        this.name = name;
        this.score = score;
    }
}
