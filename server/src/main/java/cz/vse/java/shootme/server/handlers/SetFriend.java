package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.models.Friend;
import cz.vse.java.shootme.server.models.User;
import cz.vse.java.shootme.server.net.requests.SetFriendRequest;

import javax.persistence.Query;
import java.time.LocalDate;
import java.util.Date;

public class SetFriend {


    public SetFriend(SetFriendRequest request) {

        boolean ok = Database.transaction(request, em -> {
            User user = request.getConnection().getUser();
            user = em.merge(user);

            Query query = em.createQuery("from User where username = :username", User.class);
            query.setParameter("username", request.username);
            User userFriend = (User) query.getSingleResult();


            Friend friend = new Friend();
            friend.setUser(user);
            friend.setFriend(userFriend);
            friend.setTimestamp(LocalDate.now());
            em.persist(friend);
        });

        if(ok){
            request.respondSuccess("User added to friendlist");
        } else {
            request.respondError("Could not add user as a friend");
        }

    }
}
