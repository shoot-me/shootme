package cz.vse.java.shootme.server.game;

import cz.vse.java.shootme.common.util.Vector;
import cz.vse.java.shootme.server.game.entities.Dagger;
import cz.vse.java.shootme.server.net.GameServer;
import cz.vse.java.shootme.server.net.Server;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {

    protected GameServer gameServer;

    protected ScheduledExecutorService executor;

    protected Configuration configuration;

    protected State state;

    protected int x = 600;

    protected int dir = 1;

    public Game(Configuration configuration) throws IOException {
        this.gameServer = new GameServer(Server.get().getGameServerPort());
        this.executor = Executors.newScheduledThreadPool(1);
        this.configuration = configuration;
        this.state = new State();
    }

    public void update() {
        if (x > 800 || x < 400) {
            dir = dir * -1;
        }

        x += 20 * dir;

        state.addedEntities.add(new Dagger("", new Vector(x, 0), new Vector(0, 1)));

        state.update(this);

        gameServer.pushStateUpdate(state.export());

        state.addedEntities.clear();
        state.removedEntities.clear();
    }

    public void start() {
        gameServer.start();

        executor.scheduleAtFixedRate(this::update, 0, 20, TimeUnit.MILLISECONDS);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public State getState() {
        return state;
    }

    public int getPort() {
        return gameServer.getPort();
    }
}
