package bgu.spl.net.srv.FramesForClient;

import bgu.spl.net.srv.Frame;


public class Disconnect extends Frame {

    private int receiptId;
    public Disconnect (int id){
        this.receiptId = id;
    }
    @Override
    public String getType() {
        return null;
    }
}
