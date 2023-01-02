package bgu.spl.net.srv;

import java.util.LinkedList;
import java.util.List;

public class Client {
    private String name;
    private String password;
    private List<String> gamesSubscribedTo;
    enum Stat{
        LOGGED_IN,
        NOT_LOGGED_IN
    }

    public Client(String name , String password){
        this.name = name;
        this.password = password;
        gamesSubscribedTo = new LinkedList<>();
        Stat = LOGGED_IN;
    }
    public String getName(){
        return name;
    }
    public String getPassword(){
        return password;
    }

    public List<String> getGamesSubscribedTo(){
        return gamesSubscribedTo;
    }

}
