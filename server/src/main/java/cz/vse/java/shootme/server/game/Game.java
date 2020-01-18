package cz.vse.java.shootme.server.game;

import cz.vse.java.shootme.common.game.State;

import java.util.UUID;

public class Game {

    public final String id = UUID.randomUUID().toString();

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
