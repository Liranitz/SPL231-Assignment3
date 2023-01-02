package bgu.spl.net.srv.FrameForService;

import bgu.spl.net.srv.Frame;

public class Error extends Frame {
    private String errorMessage;
    public Error(String errorMessage){
        this.errorMessage = errorMessage;
    }
    @Override
    public String getType() {
        return "ERROR";
    }
}
