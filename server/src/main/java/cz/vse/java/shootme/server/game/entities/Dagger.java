package cz.vse.java.shootme.server.game.entities;

import cz.vse.java.shootme.common.util.Vector;
import cz.vse.java.shootme.server.game.Game;

public class Dagger extends Entity {

    private final Player owner;

    public Dagger(Player owner, Vector pos, Vector dir) {
        super("img/items/dagger_1.png", "", pos, dir, new Vector(10, 10), new Vector(16, 16));

        this.owner = owner;
    }

    @Override
    public String getType() {
        return "r";
    }

    @Override
    public void update(Game game) {
        super.update(game);

        if (lifetime > 100) {
            game.getState().removeEntity(this);
        }
    }

    @Override
    public void collide(Game game, Entity another) {
        if (another.id.equals(owner.id)) return;

        int kills = game.getState().getKills().getOrDefault(owner.name, 0);
        game.getState().getKills().put(owner.name, kills + 1);

        game.getState().removeEntity(another);
        game.getState().removeEntity(this);
    }

}
