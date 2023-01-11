package bgu.spl.net.srv.FrameForService;

import bgu.spl.net.srv.Frame;

public class Reciept extends Frame {
    private Integer recieptId;
    public Reciept(Integer recieptId){
        this.recieptId = recieptId;

    }
    @Override
    public String getType() {
        return "RECIEPT";
    }

    @Override
    public String toString() {

        return "RECIEPT\n"+
                "reciept-id:"+recieptId+ '\n' +
                '\u0000';
    }


}
