package cz.vse.java.shootme.server.game.entities;

import cz.vse.java.shootme.server.game.Game;
import cz.vse.java.shootme.server.game.actions.KeyPressAction;
import cz.vse.java.shootme.server.game.actions.KeyReleaseAction;
import cz.vse.java.shootme.server.game.util.Vector;

public class Player extends Entity {

    public final String name;

    public int daggerCooldown = 0;

    public Player(String image, Vector pos, String name) {
        super(image, pos, new Vector(0, 0), new Vector(5, 5));

        this.name = name;
    }

    @Override
    public void update(Game game) {
        super.update(game);

        daggerCooldown += 1;
    }

    public void handleKeyPressAction(Game game, KeyPressAction action) {
        switch (action.code) {
            case "W":
                dir.y += -1;
                break;
            case "A":
                dir.x += -1;
                break;
            case "S":
                dir.y += 1;
                break;
            case "D":
                dir.x += 1;
                break;
            case "UP":
                if (daggerCooldown > 0) {
                    game.addEntity(new Dagger(id, getCenter(), new Vector(0, -1)));
                    daggerCooldown = 0;
                }
                break;
            case "LEFT":
                if (daggerCooldown > 0) {
                    game.addEntity(new Dagger(id, getCenter(), new Vector(-1, 0)));
                    daggerCooldown = 0;
                }
                break;
            case "DOWN":
                if (daggerCooldown > 0) {
                    game.addEntity(new Dagger(id, getCenter(), new Vector(0, 1)));
                    daggerCooldown = 0;
                }
                break;
            case "RIGHT":
                if (daggerCooldown > 0) {
                    game.addEntity(new Dagger(id, getCenter(), new Vector(1, 0)));
                    daggerCooldown = 0;
                }
                break;
        }
    }

    public void handleKeyReleaseAction(Game game, KeyReleaseAction action) {
        switch (action.code) {
            case "W":
                dir.y -= -1;
                break;
            case "A":
                dir.x -= -1;
                break;
            case "S":
                dir.y -= 1;
                break;
            case "D":
                dir.x -= 1;
                break;
        }
    }

}
