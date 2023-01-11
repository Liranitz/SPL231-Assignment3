package bgu.spl.net.srv.FramesForClient;

import bgu.spl.net.srv.Frame;


public class Unsubscribe extends Frame {
    private int subId;
    private int recId;

    public  Unsubscribe(int id, int receiptId){
        this.subId = id;
        this.recId = receiptId;
        this.Type = "UNSUBSCRIBE";

    }
    public int getSubId() {
        return subId;
    }

    public int getRecId() {
        return recId;
    }

    @Override
    public String getType() {
        return Type;
    }

    @Override
    public String toString() {
        return null;
    }

}
