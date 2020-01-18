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

    protected Configuration configuration;

    protected State state;

    protected List<Connection> connections;

    protected List<Action> actions;

    public Game(Configuration configuration) {
        this.executor = Executors.newScheduledThreadPool(1);
        this.configuration = configuration;
        this.state = new State();
        this.connections = new ArrayList<>();
        this.actions = new ArrayList<>();
    }

    @Override
    public void run() {
        state.applyActions(this, actions);

        state.update(this);

        actions.clear();
    }

    public void start() {
        executor.scheduleAtFixedRate(this, 0, 10, TimeUnit.MILLISECONDS);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public State getState() {
        return state;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public List<Action> getActions() {
        return actions;
    }
}
