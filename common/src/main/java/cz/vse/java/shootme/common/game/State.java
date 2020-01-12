package cz.vse.java.shootme.common.game;

import cz.vse.java.shootme.common.game.entities.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class State implements Serializable {

    protected List<Entity> entities;

    public State() {
        entities = new ArrayList<>();
    }

    public List<Entity> getEntities() {
        return entities;
    }

}
