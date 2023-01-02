package bgu.spl.net.srv.FrameForService;

import bgu.spl.net.srv.Frame;

public class Connected extends Frame {
    public Connected(){
        this.Type = "Connected";
    }

    @Override
    public String getType() {
        return Type;
    }
}
