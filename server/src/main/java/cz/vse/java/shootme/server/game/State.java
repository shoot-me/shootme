package cz.vse.java.shootme.server.game;

import cz.vse.java.shootme.common.net.EntityUpdate;
import cz.vse.java.shootme.common.net.StateUpdate;
import cz.vse.java.shootme.server.game.actions.Action;
import cz.vse.java.shootme.server.game.actions.KeyPressAction;
import cz.vse.java.shootme.server.game.actions.KeyReleaseAction;
import cz.vse.java.shootme.server.game.entities.Entity;
import cz.vse.java.shootme.server.game.entities.Player;
import cz.vse.java.shootme.server.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class State {

    protected Map<String, User> users;

    protected Map<String, Integer> kills;

    protected List<Entity> addedEntities;

    protected List<Entity> entities;

    protected List<Entity> removedEntities;

    protected int lifetime = 0;

    public State() {
        this.users = new HashMap<>();
        this.kills = new HashMap<>();
        this.addedEntities = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.removedEntities = new ArrayList<>();
    }

    public void applyActions(Game game, List<Action> actions) {
        for (Action action : actions) {
            if (action instanceof KeyPressAction) {
                action.player.applyKeyPressAction(game, (KeyPressAction) action);
            } else if (action instanceof KeyReleaseAction) {
                action.player.applyKeyReleaseAction(game, (KeyReleaseAction) action);
            }
        }
    }

    public synchronized void update(Game game) {
        if (!isRunning()) return;

        lifetime++;

        for (Entity entity : entities) {
            entity.update(game);
        }

        for (Entity entity : entities) {
            for (Entity another : entities) {
                if (entity != another && entity.intersects(another)) {
                    entity.collide(game, another);
                }
            }
        }

        entities.addAll(addedEntities);
        entities.removeAll(removedEntities);
    }

    public synchronized Map<String, User> getUsers() {
        return users;
    }

    public synchronized Map<String, Integer> getKills() {
        return kills;
    }

    public synchronized void addEntity(Entity entity) {
        addedEntities.add(entity);
    }

    public synchronized void removeEntity(Entity entity) {
        removedEntities.add(entity);
    }

    public boolean isRunning() {
        return lifetime < 1000;
    }

    public StateUpdate export() {
        List<EntityUpdate> entityUpdates = entities.stream().map(Entity::export).collect(Collectors.toList());
        List<String> playerInfo = users.keySet().stream()
                .map(name -> name + ": " + kills.getOrDefault(name, 0))
                .collect(Collectors.toList());

        return new StateUpdate(entityUpdates, playerInfo, isRunning());
    }
}
