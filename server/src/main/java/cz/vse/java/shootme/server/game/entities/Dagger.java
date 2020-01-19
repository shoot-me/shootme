package cz.vse.java.shootme.server.game.entities;

import cz.vse.java.shootme.common.util.Vector;
import cz.vse.java.shootme.server.game.Game;

public class Dagger extends Entity {

    private final String owner;

    public Dagger(String owner, Vector pos, Vector dir) {
        super("img/items/dagger_1.png", pos, dir, new Vector(10, 10), new Vector(16, 16));

        this.owner = owner;
    }

    @Override
    public void update(Game game) {
        super.update(game);

        if (lifetime > 50) {
            game.getState().getRemovedEntities().add(this);
        }
    }

}