package cz.vse.java.shootme.server.game.entities;

import cz.vse.java.shootme.common.util.Vector;
import cz.vse.java.shootme.server.game.Game;
import cz.vse.java.shootme.server.handlers.UpdateStatistics;
import cz.vse.java.shootme.server.net.Connection;
import cz.vse.java.shootme.server.net.Server;
import cz.vse.java.shootme.server.net.requests.Request;
import cz.vse.java.shootme.server.net.requests.UpdateStatisticsRequest;
import cz.vse.java.shootme.server.statisticService.StatKilled;
import cz.vse.java.shootme.server.statisticService.StatKills;

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

        if (another instanceof Player) {
            owner.kills((Player) another);
            new StatKilled(((Player) another).getUser().getStatistics());
            new StatKills(owner.getUser().getStatistics());
        }

        game.getState().removeEntity(another);
        game.getState().removeEntity(this);
    }

}
