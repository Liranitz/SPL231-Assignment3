package bgu.spl.net.srv.FrameForService;

import bgu.spl.net.srv.Frame;

public class Receipt extends Frame {
    private Integer receiptId;
    public Receipt(Integer receiptId){
        this.receiptId = receiptId;

    }
    @Override
    public String getType() {
        return "RECEIPT";
    }

    @Override
    public String toString() {

        return "RECEIPT\n"+
                "receipt-id:"+receiptId+ '\n' +
                '\u0000';
    }


}
