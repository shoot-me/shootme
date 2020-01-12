package cz.vse.java.shootme.common.game.entities;

public class Player extends Entity {

    public String name;

    public int hp;

    public int x;

    public int y;

    public Player(String name) {
        this.name = name;

        hp = 100;
        x = 0;
        y = 0;
    }

}
