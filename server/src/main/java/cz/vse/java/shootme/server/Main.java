package cz.vse.java.shootme.server;

import cz.vse.java.shootme.server.net.requests.*;
import cz.vse.java.shootme.server.handlers.*;
import cz.vse.java.shootme.common.EventBus;
import cz.vse.java.shootme.server.net.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Starting server");

        Database.init();
        Database.migrate();

        EventBus.get().subscribe(RegisterRequest.class, RegisterUser::new);
        EventBus.get().subscribe(LoginRequest.class, LoginUser::new);
        EventBus.get().subscribe(ChangeUsernameRequest.class, ChangeUsername::new);
        EventBus.get().subscribe(ChangePasswordRequest.class, ChangePassword::new);
        EventBus.get().subscribe(UpdateSkinRequest.class, UpdateSkin::new);
        EventBus.get().subscribe(GetStatisticsRequest.class, GetStatistics::new);
        EventBus.get().subscribe(CreateConfigRequest.class, CreateConfig::new);
        EventBus.get().subscribe(GetConfigurationsRequest.class, GetConfigurations::new);

        EventBus.get().subscribe(OverviewRequest.class, GetOverview::new);

        EventBus.get().subscribe(NewGameRequest.class, NewGame::new);
        EventBus.get().subscribe(JoinGameRequest.class, JoinGame::new);

        Server.get().start();
    }
}
