package cz.vse.java.shootme.client.gui.controllers;

import cz.vse.java.shootme.common.util.Vector;

public abstract class Controller {

    public void created() {

    }

    public void mounted() {

    }

    public void unmounted() {

    }

    public Vector getWindowSize() {
        return new Vector(1280, 720);
    }

}
