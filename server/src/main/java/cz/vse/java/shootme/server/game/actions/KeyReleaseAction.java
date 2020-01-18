package cz.vse.java.shootme.server.game.actions;

public class KeyReleaseAction extends Action {

    public final String code;

    public KeyReleaseAction(String code) {
        this.code = code.toUpperCase();
    }

}
