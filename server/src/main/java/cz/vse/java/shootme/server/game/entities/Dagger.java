package cz.vse.java.shootme.server.game.entities;

import cz.vse.java.shootme.server.game.Game;
import cz.vse.java.shootme.server.game.util.Vector;

public class Dagger extends Entity {

    private final String owner;

    private int lifetime = 50;

    public Dagger(String owner, Vector pos, Vector dir) {
        super("img/items/dagger_1.png", pos, dir, new Vector(10, 10));

        this.owner = owner;
    }

    @Override
    public void update(Game game) {
        super.update(game);

        lifetime--;

        if (lifetime < 0) {
            game.removeEntity(this);
        }
    }

    @Override
    public void collide(Game game, Entity another) {
        if (another.id.equals(owner)) return;

        game.removeEntity(another);
    }

}
