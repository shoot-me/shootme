package cz.vse.java.shootme.server.game;

import cz.vse.java.shootme.server.game.actions.Action;
import cz.vse.java.shootme.server.game.actions.KeyPressAction;
import cz.vse.java.shootme.server.game.actions.KeyReleaseAction;
import cz.vse.java.shootme.server.game.entities.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class State implements Serializable {

    public final String id;

    protected List<Entity> entities;

    public State() {
        id = UUID.randomUUID().toString();
        entities = Collections.synchronizedList(new ArrayList<>());
    }

    public State(String id, List<Entity> entities) {
        this.id = id;
        this.entities = entities;
    }

    public void applyActions(Game game, List<Action> actions) {
        for (Action action : actions) {
            if (action instanceof KeyPressAction) {
                action.player.handleKeyPressAction(game, (KeyPressAction) action);
            } else if (action instanceof KeyReleaseAction) {
                action.player.handleKeyReleaseAction(game, (KeyReleaseAction) action);
            }
        }
    }

    public void update(Game game) {
        entities.addAll(game.getAddedEntities());

        for (Entity entity : entities) {
            entity.update(game);

            for (Entity another : entities) {
                if (entity != another && entity.intersects(another)) {
                    entity.collide(game, another);
                }
            }
        }

        entities.removeAll(game.getRemovedEntities());
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public synchronized State copy() {
        return new State(id, new ArrayList<>(entities));
    }

}
