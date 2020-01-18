package cz.vse.java.shootme.server.game;

import cz.vse.java.shootme.server.game.actions.Action;
import cz.vse.java.shootme.server.net.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game implements Runnable {

    protected ScheduledExecutorService executor;

    protected State state;

    protected String name;

    protected List<Connection> connections;

    protected List<Action> actions;

    public Game(String name) {
        this.executor = Executors.newScheduledThreadPool(1);
        this.name = name;
        this.state = new State();
        this.connections = new ArrayList<>();
        this.actions = new ArrayList<>();
    }

    @Override
    public void run() {
        state.applyActions(actions);

        state.update();

        actions.clear();
    }

    public void start() {
        executor.scheduleAtFixedRate(this, 0, 10, TimeUnit.MILLISECONDS);
    }

    public State getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public List<Action> getActions() {
        return actions;
    }
}
