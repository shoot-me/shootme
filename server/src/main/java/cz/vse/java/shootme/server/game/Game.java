package cz.vse.java.shootme.server.game;

import cz.vse.java.shootme.server.game.entities.Player;
import cz.vse.java.shootme.server.net.GameServer;
import cz.vse.java.shootme.server.net.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {

    protected GameServer gameServer;

    protected ScheduledExecutorService executor;

    protected Configuration configuration;

    protected State state;

    protected Map<String, Player> players;

    public Game(Configuration configuration) throws IOException {
        this.gameServer = new GameServer(this, Server.get().getGameServerPort());
        this.executor = Executors.newScheduledThreadPool(1);
        this.configuration = configuration;
        this.state = new State();
        this.players = new HashMap<>();
    }

    public void update() {
        state.applyActions(this, gameServer.consumeActions());

        state.update(this);

        gameServer.pushStateUpdate(state.export());

        state.addedEntities.clear();
        state.removedEntities.clear();
    }

    public void start() {
        gameServer.start();

        executor.scheduleAtFixedRate(this::update, 0, 20, TimeUnit.MILLISECONDS);
    }

    public void addPlayer(Player player) {
        players.put(player.name, player);
        state.addEntity(player);
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

    public boolean isRunning() {
        return state.isRunning();
    }
}
