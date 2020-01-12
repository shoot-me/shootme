package cz.vse.java.shootme.client.game.entities;

import cz.vse.java.shootme.client.game.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Dagger extends Entity {

    private double speedX;

    private double speedY;

    public Dagger(Map map, double x, double y, double speedX, double speedY) {
        super(map, new Image("img/items/dagger_1.png"));

        setX(x);
        setY(y);

        this.speedX = speedX;
        this.speedY = speedY;
    }

    @Override
    public void update() {
        super.update();

        if(getLifetime() > 200) {
            //map.removeEntity(this);
        }

        setX(getX() + speedX);
        setY(getY() + speedY);
    }

    @Override
    public ImageView render() {
        ImageView imageView = super.render();

        if (speedX > 0) {
            imageView.setRotate(90);
        } else if (speedX < 0) {
            imageView.setRotate(270);
        } else if (speedY > 0) {
            imageView.setRotate(180);
        } else {
            imageView.setRotate(0);
        }

        return imageView;
    }

}
