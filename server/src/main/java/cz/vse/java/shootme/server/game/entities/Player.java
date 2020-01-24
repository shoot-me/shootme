package cz.vse.java.shootme.server.game.entities;

import cz.vse.java.shootme.server.game.Game;
import cz.vse.java.shootme.common.util.Vector;
import cz.vse.java.shootme.server.game.actions.KeyPressAction;
import cz.vse.java.shootme.server.game.actions.KeyReleaseAction;
import cz.vse.java.shootme.server.models.User;

import javax.jnlp.UnavailableServiceException;


public class Player extends Entity {

    public final String name;

    protected int kills = 0;

    protected int daggerCooldown = 0;

    private User user;

    public Player(String image, Vector pos, String name, User user) {
        super(image, name, pos, new Vector(0, 0), new Vector(5, 5), new Vector(64, 80));

        this.name = name;
        this.user = user;
    }

    @Override
    public String getType() {
        return "s";
    }

    @Override
    public void update(Game game) {
        super.update(game);

        daggerCooldown += 1;
    }

    public void shoot(Game game, Vector dir) {
        if (daggerCooldown > 20) {
            game.getState().addEntity(new Dagger(this, getCenter(), dir));

            daggerCooldown = 0;
        }
    }

    public void applyKeyPressAction(Game game, KeyPressAction action) {
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
            case "UP":
                shoot(game, new Vector(0, -1));
                break;
            case "LEFT":
                shoot(game, new Vector(-1, 0));
                break;
            case "DOWN":
                shoot(game, new Vector(0, 1));
                break;
            case "RIGHT":
                shoot(game, new Vector(1, 0));
                break;
        }
    }

    public void applyKeyReleaseAction(Game game, KeyReleaseAction action) {
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

    public int getKills() {
        return kills;
    }

    public void kills(Player player) {
        kills++;
    }

    public User getUser() {
        return user;
    }
}
