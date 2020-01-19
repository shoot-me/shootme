package cz.vse.java.shootme.server.game.entities;

import cz.vse.java.shootme.common.net.EntityUpdate;
import cz.vse.java.shootme.server.game.Game;
import cz.vse.java.shootme.common.util.Vector;

import java.util.Objects;
import java.util.UUID;

public abstract class Entity {

    public final String id = UUID.randomUUID().toString();

    protected String image;

    protected Vector pos;

    protected Vector dir;

    protected Vector speed;

    protected Vector size;

    protected int lifetime = 0;

    public Entity(String image, Vector pos, Vector dir, Vector speed, Vector size) {
        this.image = image;
        this.pos = pos;
        this.dir = dir;
        this.speed = speed;
        this.size = size;
        this.lifetime = 0;
    }

    public abstract String getType();

    public void update(Game game) {
        lifetime++;

        dir = dir.clampMin(new Vector(-1, -1));
        dir = dir.clampMax(new Vector(1, 1));

        pos = pos.add(dir.times(speed));

        pos = pos.clampMin(new Vector(0, 0));
        pos = pos.clampMax(new Vector(game.getConfiguration().getWidth() * 64 - size.x, game.getConfiguration().getHeight() * 64 - size.y));
    }

    public boolean intersects(Entity another) {
        if (pos.x >= another.pos.x && pos.x <= another.pos.x + another.getSize().x) {
            if (pos.y >= another.pos.y && pos.y <= another.pos.y + another.getSize().y) {
                return true;
            }
        }

        return false;
    }

    public void collide(Game game, Entity another) {

    }

    public EntityUpdate export() {
        return new EntityUpdate(getType(), id, pos, dir, speed, size, image);
    }

    public Vector getSize() {
        return size;
    }

    public Vector getCenter() {
        return new Vector(pos.x + size.x / 2, pos.y + size.y / 2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
