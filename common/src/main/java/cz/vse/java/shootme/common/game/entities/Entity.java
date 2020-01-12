package cz.vse.java.shootme.common.game.entities;

import java.io.Serializable;
import java.util.UUID;

public abstract class Entity implements Serializable {

    public final String id = UUID.randomUUID().toString();

}
