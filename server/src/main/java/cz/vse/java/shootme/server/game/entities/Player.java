package cz.vse.java.shootme.server.game.entities;

import cz.vse.java.shootme.server.game.Game;
import cz.vse.java.shootme.common.util.Vector;
import cz.vse.java.shootme.server.game.actions.KeyPressAction;
import cz.vse.java.shootme.server.game.actions.KeyReleaseAction;

public class Player extends Entity {

    public final String name;

    public int daggerCooldown = 0;

    public Player(String image, Vector pos, String name) {
        super(image, pos, new Vector(0, 0), new Vector(5, 5), new Vector(64, 64));

        this.name = name;
    }

    @Override
    public void update(Game game) {
        super.update(game);

        daggerCooldown += 1;
    }

    public void applyKeyPressAction(KeyPressAction action) {
        switch (action.code) {
            case "W":
                dir = dir.add(new Vector(0, -1));
                break;
            case "A":
                dir = dir.add(new Vector(-1, 0));
                break;
            case "S":
                dir = dir.add(new Vector(0, 1));
                break;
            case "D":
                dir = dir.add(new Vector(1, 0));
                break;
        }
    }

    public void applyKeyReleaseAction(KeyReleaseAction action) {
        switch (action.code) {
            case "W":
                dir = dir.add(new Vector(0, 1));
                break;
            case "A":
                dir = dir.add(new Vector(1, 0));
                break;
            case "S":
                dir = dir.add(new Vector(0, -1));
                break;
            case "D":
                dir = dir.add(new Vector(-1, 0));
                break;
        }
    }

}
