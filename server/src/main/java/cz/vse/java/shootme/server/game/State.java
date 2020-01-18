package cz.vse.java.shootme.server.game;

import cz.vse.java.shootme.server.game.actions.Action;
import cz.vse.java.shootme.server.game.entities.Entity;
import cz.vse.java.shootme.server.game.entities.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class State implements Serializable {

    public final String id = UUID.randomUUID().toString();

    protected List<Entity> entities;

    public State() {
        entities = new ArrayList<>();
    }

    public void applyActions(List<Action> actions) {

    }

    public void update() {
        for (Entity entity : entities) {
            entity.pos.add(entity.speed);
        }
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public Player getPlayer(String name) {
        for (Entity entity : entities) {
            if (entity instanceof Player) {
                if (((Player) entity).name.equals(name)) {
                    return (Player) entity;
                }
            }
        }

        return null;
    }

}
