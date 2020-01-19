package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.game.Configuration;
import cz.vse.java.shootme.server.game.Game;
import cz.vse.java.shootme.server.net.Server;
import cz.vse.java.shootme.server.net.requests.NewGameRequest;
import cz.vse.java.shootme.server.net.responses.NewGameResponse;

import java.io.IOException;

public class NewGame {

    public NewGame(NewGameRequest request) {
        try {
            Configuration configuration = new Configuration(request.name, 30, 30);

            Game game = new Game(configuration);

            Server.get().getGames().put(request.name, game);

            game.start();

            request.respond(new NewGameResponse());
        } catch (IOException e) {
            e.printStackTrace();

            request.respondError("Error creating new game");
        }
    }
}
