package cz.vse.java.shootme.server;

import cz.vse.java.shootme.common.requests.*;
import cz.vse.java.shootme.server.handlers.*;
import cz.vse.java.shootme.common.EventBus;
import cz.vse.java.shootme.server.net.Server;
import jeda00.db.Migrations;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        EventBus.get().subscribe(JoinGameRequest.class, JoinGame::new);
        EventBus.get().subscribe(GameUpdateRequest.class, UpdateGame::new);

        Server.get().start();
    }
}
