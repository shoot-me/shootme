package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.game.Configuration;
import cz.vse.java.shootme.server.models.Friend;
import cz.vse.java.shootme.server.models.User;
import cz.vse.java.shootme.server.net.requests.GetFriendsRequest;
import cz.vse.java.shootme.server.net.responses.GetFriendsResponse;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class GetFriends {

    public GetFriends(GetFriendsRequest request){
       Database.transaction(request, em ->{
            User user = request.getConnection().getUser();
            user = em.merge(user);

            List<Friend> friends = user.getFriends();

           GetFriendsResponse response = new GetFriendsResponse();

           for(Friend friend : friends) {
               response.friends.put(friend.getId(), friend.getFriend().getUsername());
           }

           request.respond(response);
        });
    }

}
