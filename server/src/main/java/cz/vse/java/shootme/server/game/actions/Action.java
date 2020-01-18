package cz.vse.java.shootme.server.game.actions;

import java.io.Serializable;
import java.util.UUID;

public abstract class Action implements Serializable {

    public final String id = UUID.randomUUID().toString();

}
