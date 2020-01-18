package cz.vse.java.shootme.server.net.requests;

import cz.vse.java.shootme.server.game.actions.Action;

import java.util.ArrayList;
import java.util.List;

public class GameUpdateRequest extends Request {

    public final List<Action> actions;

    public GameUpdateRequest(List<Action> actions) {
        this.actions = new ArrayList<>(actions);
    }

}
