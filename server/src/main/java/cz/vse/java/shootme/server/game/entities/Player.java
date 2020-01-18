package cz.vse.java.shootme.server.game.entities;

import cz.vse.java.shootme.server.game.Game;
import cz.vse.java.shootme.server.game.actions.KeyPressAction;
import cz.vse.java.shootme.server.game.actions.KeyReleaseAction;
import cz.vse.java.shootme.server.game.util.Vector;

public class Player extends Entity {

    public final String name;

    public Player(String image, Vector pos, String name) {
        super(image, pos, new Vector(0, 0), new Vector(5, 5));

        this.name = name;
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
