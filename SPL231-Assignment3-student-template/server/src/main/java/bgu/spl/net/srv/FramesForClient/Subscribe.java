package bgu.spl.net.srv.FramesForClient;

import bgu.spl.net.srv.Frame;

public class Subscribe extends Frame {
    private String destination;
    private int id;
    private int receiptId;
    public Subscribe(String destination , int id){
        this.destination = destination;
        this.id = id;
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

}

