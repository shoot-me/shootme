package cz.vse.java.shootme.server.net.requests;

public class UpdateStatisticsRequest extends Request {


    public static String joinGame = "joinGame";

    public static String killed = "killed";

    public String type;

    public UpdateStatisticsRequest(String type){
        this.type = type;
    }
}
