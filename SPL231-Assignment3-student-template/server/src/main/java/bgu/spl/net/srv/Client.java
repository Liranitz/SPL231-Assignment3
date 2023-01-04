package bgu.spl.net.srv;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Client {
    private String name;
    private String password;
    private List<String> gamesSubscribedTo;
    //private HashMap<Integer , String>;
    private boolean logged_in;

    public Client(String name , String password){
        this.name = name;
        this.password = password;
        gamesSubscribedTo = new LinkedList<>();
        logged_in = true;
    }
    public String getName(){
        return name;
    }
    public String getPassword(){
        return password;
    }
    public boolean getStat(){
        return logged_in;
    }
    public void setStat(boolean newLogOrNot){
        logged_in = newLogOrNot;
    }
    public List<String> getGamesSubscribedTo(){
        return gamesSubscribedTo;
    }

}
