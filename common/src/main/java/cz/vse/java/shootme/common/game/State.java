package cz.vse.java.shootme.common.game;

import cz.vse.java.shootme.common.game.entities.Entity;

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

    public State(State state) {
        entities = new ArrayList<>(state.entities);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    @Override
    public String toString() {
        return "State{" +
                "id='" + id + '\'' +
                ", entities=" + entities +
                '}';
    }
}
