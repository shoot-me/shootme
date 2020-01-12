package cz.vse.java.shootme.server.game;

import cz.vse.java.shootme.common.game.State;

public class Game {

    protected State state;

    protected String name;

    public Game(String name) {
        this.name = name;

        state = new State();
    }

    public State getState() {
        return state;
    }

    public String getName() {
        return name;
    }

}
