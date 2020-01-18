package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.net.requests.OverviewRequest;
import cz.vse.java.shootme.server.net.responses.OverviewResponse;
import cz.vse.java.shootme.server.models.User;

public class GetOverview {

    public GetOverview(OverviewRequest request) {
        User user = request.getConnection().getUser();

        request.respond(new OverviewResponse(user.getUsername()));
    }

}
