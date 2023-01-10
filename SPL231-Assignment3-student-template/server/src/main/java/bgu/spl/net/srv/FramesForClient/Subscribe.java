package bgu.spl.net.srv.FramesForClient;

import bgu.spl.net.srv.Frame;

public class Subscribe extends Frame {
    private String destination;
    private int subId;
    private int recId;
    
    public Subscribe( String destination, int desId, int receiptd){
       this.destination = destination;
       this.subId = desId;
       this.recId = receiptd;
    }
    public int getsubId() {
        return subId;
    }

    public String getDestination(){
        return destination;
    }

    public int getrecId(){
        return recId;
    }
    @Override
    public String getType() {
        return "SUBSCRIBE";
    }

    @Override
    public String toString() {
        return null;
    }

}

