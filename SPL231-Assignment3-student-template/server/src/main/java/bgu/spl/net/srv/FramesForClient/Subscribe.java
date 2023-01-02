package bgu.spl.net.srv.FramesForClient;

import bgu.spl.net.srv.Frame;

public class Subscribe extends Frame {
    private String destination;
    private int id;
    private int receiptId;
    public Subscribe(String destination , int id , int receipt){
        this.destination = destination;
        this.id = id;
        this.receiptId = receipt;
    }
    public int getId() {
        return id;
    }

    @Override
    public String getType() {
        return null;
    }

    public int getReceipt() {
        return receiptId;
    }
}

