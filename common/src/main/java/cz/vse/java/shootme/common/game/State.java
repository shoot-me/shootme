package cz.vse.java.shootme.common.game;

import cz.vse.java.shootme.common.game.entities.Entity;
import cz.vse.java.shootme.common.game.entities.Player;

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

    public State clone() {
        State state = new State();

        for (Entity entity : entities) {
            state.entities.add(entity.clone());
        }

        return state;
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

    @Override
    public String toString() {
        return "State{" +
                "id='" + id + '\'' +
                ", entities=" + entities +
                '}';
    }
}
