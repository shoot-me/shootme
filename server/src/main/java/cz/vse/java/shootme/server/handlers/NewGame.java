package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.game.Configuration;
import cz.vse.java.shootme.server.game.Game;
import cz.vse.java.shootme.server.net.Server;
import cz.vse.java.shootme.server.net.requests.NewGameRequest;
import cz.vse.java.shootme.server.net.responses.NewGameResponse;

public class NewGame {

    public NewGame(NewGameRequest request) {
        Configuration configuration = new Configuration(request.name, 30, 30);
        Game game = new Game(configuration);

        Server.get().getGames().put(request.name, game);

        game.start();

        request.respond(new NewGameResponse());
    }

}
