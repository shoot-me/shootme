package cz.vse.java.shootme.server;

import cz.vse.java.shootme.server.net.requests.*;
import cz.vse.java.shootme.server.handlers.*;
import cz.vse.java.shootme.common.EventBus;
import cz.vse.java.shootme.server.net.Server;

import javax.xml.crypto.Data;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting server...");

        Database.init();
        Database.migrate();

        EventBus.get().subscribe(RegisterRequest.class, RegisterUser::new);
        EventBus.get().subscribe(LoginRequest.class, LoginUser::new);
        EventBus.get().subscribe(ChangeUsernameRequest.class, ChangeUsername::new);
        EventBus.get().subscribe(ChangePasswordRequest.class, ChangePassword::new);

        EventBus.get().subscribe(OverviewRequest.class, GetOverview::new);

        EventBus.get().subscribe(NewGameRequest.class, NewGame::new);
        EventBus.get().subscribe(JoinGameRequest.class, JoinGame::new);

        Server.get().start();
    }
}
