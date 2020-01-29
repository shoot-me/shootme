package cz.vse.java.shootme.server.game;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.game.entities.Player;
import cz.vse.java.shootme.server.models.Result;
import cz.vse.java.shootme.server.models.Statistic;
import cz.vse.java.shootme.server.models.User;
import cz.vse.java.shootme.server.net.GameServer;
import cz.vse.java.shootme.server.net.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {

    private static final Logger logger = LogManager.getLogger(Game.class);

    protected GameServer gameServer;

    protected ScheduledExecutorService executor;

    protected Configuration configuration;

    protected State state;

    public Game(Configuration configuration) throws IOException {
        this.gameServer = new GameServer(this, Server.get().getGameServerPort());
        this.executor = Executors.newScheduledThreadPool(1);
        this.configuration = configuration;
        this.state = new State();
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

    public void save() {
        logger.info("Saving game {}", configuration.name);

        Result result = Database.transaction(em -> {
            Result r = new Result();
            r.setName(configuration.name);
            r.setDateTime(LocalDateTime.now());
            em.persist(r);

            return r;
        }).get();


        for (User user : state.getUsers().values()) {
            Database.transaction(em -> {
                em.merge(user);

                int kills = state.getKills().getOrDefault(user.getUsername(), 0);

                Statistic statistic = new Statistic();
                statistic.setUser(user);
                statistic.setResult(result);
                statistic.setKills(kills);
                em.persist(statistic);
            });
        }
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
