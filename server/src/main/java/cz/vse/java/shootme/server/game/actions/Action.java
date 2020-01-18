package cz.vse.java.shootme.server.game.actions;

import cz.vse.java.shootme.server.game.entities.Player;

import java.io.Serializable;
import java.util.UUID;

public abstract class Action implements Serializable {

    public final String id = UUID.randomUUID().toString();

    public Player player;

}
