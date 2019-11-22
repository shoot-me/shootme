package cz.vse.java.shootme.server;

import cz.vse.java.shootme.common.requests.LoginRequest;
import cz.vse.java.shootme.common.requests.RegisterRequest;
import cz.vse.java.shootme.server.handlers.LoginUser;
import jeda00.db.Migrations;
import cz.vse.java.shootme.common.EventBus;
import cz.vse.java.shootme.server.handlers.RegisterUser;
import cz.vse.java.shootme.server.net.Server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Migrations migrations = new Migrations(Database.getConnection());
        migrations.runMigrations();

        EventBus.get().subscribe(RegisterRequest.class, RegisterUser::new);
        EventBus.get().subscribe(LoginRequest.class, LoginUser::new);

        Server server = new Server(8080);

        server.start();
    }
}
