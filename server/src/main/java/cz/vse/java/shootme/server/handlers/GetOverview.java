package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.common.requests.OverviewRequest;
import cz.vse.java.shootme.common.responses.OverviewResponse;
import cz.vse.java.shootme.server.models.User;

public class GetOverview {

    public GetOverview(OverviewRequest request) {
        User user = User.query().where("token", request.token).first();

        request.respond(new OverviewResponse(user.getUsername()));
    }

}
