package cz.vse.java.shootme.server.game;

import cz.vse.java.shootme.common.net.EntityUpdate;
import cz.vse.java.shootme.common.net.StateUpdate;
import cz.vse.java.shootme.server.game.actions.Action;
import cz.vse.java.shootme.server.game.actions.KeyPressAction;
import cz.vse.java.shootme.server.game.actions.KeyReleaseAction;
import cz.vse.java.shootme.server.game.entities.Entity;

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

    }

    public void update(Game game) {
        for (Entity entity : entities) {
            entity.update(game);

            for (Entity another : entities) {
                if (entity != another && entity.intersects(another)) {
                    entity.collide(game, another);
                }
            }
        }

        entities.addAll(addedEntities);
        entities.removeAll(removedEntities);
    }

    public List<Entity> getAddedEntities() {
        return addedEntities;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Entity> getRemovedEntities() {
        return removedEntities;
    }

    public StateUpdate export() {
        List<EntityUpdate> added = addedEntities.stream().map(Entity::export).collect(Collectors.toList());
        List<EntityUpdate> updated = entities.stream().map(Entity::export).collect(Collectors.toList());
        List<EntityUpdate> removed = removedEntities.stream().map(Entity::export).collect(Collectors.toList());

        return new StateUpdate(added, updated, removed);
    }

}
