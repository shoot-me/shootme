package cz.vse.java.shootme.server.game;

import cz.vse.java.shootme.server.game.actions.Action;
import cz.vse.java.shootme.server.game.actions.KeyPressAction;
import cz.vse.java.shootme.server.game.actions.KeyReleaseAction;
import cz.vse.java.shootme.server.game.entities.Entity;
import cz.vse.java.shootme.server.game.entities.Player;
import cz.vse.java.shootme.server.game.util.Vector;

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
        for (Action action : actions) {
            if (action instanceof KeyPressAction) {
                if (((KeyPressAction) action).code.equals("W")) {
                    action.player.speed.add(new Vector(0, -5));
                }

                if (((KeyPressAction) action).code.equals("A")) {
                    action.player.speed.add(new Vector(-5, 0));
                }

                if (((KeyPressAction) action).code.equals("S")) {
                    action.player.speed.add(new Vector(0, 5));
                }

                if (((KeyPressAction) action).code.equals("D")) {
                    action.player.speed.add(new Vector(5, 0));
                }
            } else if (action instanceof KeyReleaseAction) {
                if (((KeyReleaseAction) action).code.equals("W")) {
                    action.player.speed.add(new Vector(0, 5));
                }

                if (((KeyReleaseAction) action).code.equals("A")) {
                    action.player.speed.add(new Vector(5, 0));
                }

                if (((KeyReleaseAction) action).code.equals("S")) {
                    action.player.speed.add(new Vector(0, -5));
                }

                if (((KeyReleaseAction) action).code.equals("D")) {
                    action.player.speed.add(new Vector(-5, 0));
                }
            }
        }
    }

    public void update() {
        for (Entity entity : entities) {
            entity.update();
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
