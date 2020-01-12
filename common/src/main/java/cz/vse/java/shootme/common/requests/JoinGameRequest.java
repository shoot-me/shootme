package cz.vse.java.shootme.common.requests;

public class JoinGameRequest extends Request {

    public final String gameName;

    public final String playerName;

    public JoinGameRequest(String gameName, String playerName) {
        this.gameName = gameName;
        this.playerName = playerName;

        System.out.println(this);
    }

    @Override
    public String toString() {
        return "JoinGameRequest{" +
                "gameName='" + gameName + '\'' +
                ", playerName='" + playerName + '\'' +
                '}';
    }
}
