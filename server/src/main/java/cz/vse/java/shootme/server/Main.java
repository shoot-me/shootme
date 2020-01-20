package cz.vse.java.shootme.server;

import cz.vse.java.shootme.server.net.requests.*;
import cz.vse.java.shootme.server.handlers.*;
import cz.vse.java.shootme.common.EventBus;
import cz.vse.java.shootme.server.net.Server;
import jeda00.db.Migrations;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting server...");

        Migrations migrations = new Migrations(Database.getConnection());
        migrations.runMigration("create_users_table", "" +
                "CREATE TABLE users (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    username TEXT NOT NULL UNIQUE,\n" +
                "    password TEXT NOT NULL,\n" +
                "    token TEXT\n" +
                ");");

        EventBus.get().subscribe(RegisterRequest.class, RegisterUser::new);
        EventBus.get().subscribe(LoginRequest.class, LoginUser::new);

        EventBus.get().subscribe(OverviewRequest.class, GetOverview::new);

        EventBus.get().subscribe(NewGameRequest.class, NewGame::new);
        EventBus.get().subscribe(JoinGameRequest.class, JoinGame::new);

        Server.get().start();
    }
}
