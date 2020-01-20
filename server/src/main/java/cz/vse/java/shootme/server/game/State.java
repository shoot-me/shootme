package cz.vse.java.shootme.server.game;

import cz.vse.java.shootme.common.net.EntityUpdate;
import cz.vse.java.shootme.common.net.StateUpdate;
import cz.vse.java.shootme.server.game.actions.Action;
import cz.vse.java.shootme.server.game.actions.KeyPressAction;
import cz.vse.java.shootme.server.game.actions.KeyReleaseAction;
import cz.vse.java.shootme.server.game.entities.Entity;
import cz.vse.java.shootme.server.game.entities.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class State {

    protected List<Entity> addedEntities;

    protected List<Entity> entities;

    protected List<Entity> removedEntities;

    public State() {
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

    public synchronized void addEntity(Entity entity) {
        addedEntities.add(entity);
    }

    public synchronized void removeEntity(Entity entity) {
        removedEntities.add(entity);
    }

    public StateUpdate export() {
        List<EntityUpdate> entityUpdates = entities.stream().map(Entity::export).collect(Collectors.toList());
        List<String> playerInfo = entities.stream()
                .filter(e -> e instanceof Player)
                .map(e -> (Player) e)
                .map(p -> p.name + ": " + p.getKills())
                .collect(Collectors.toList());

        return new StateUpdate(entityUpdates, playerInfo);
    }
}
