package bgu.spl.net.srv.FramesForClient;

import bgu.spl.net.srv.Frame;

public class Subscribe extends Frame {
    private String destination;
    private int id;
    public Subscribe( String destination, int id ){
       this.id = id;
        this.destination = destination;
    }
    public int getId() {
        return id;
    }
    public String getDestination(){
        return destination;
    }
    @Override
    public String getType() {
        return "SUBSCRIBE";
    }

    @Override
    public String toString() {
        return "HELLO LIRAN \0";
    }

}

