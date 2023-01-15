package bgu.spl.net.srv.FrameForService;

import bgu.spl.net.srv.Frame;
import bgu.spl.net.srv.FramesForClient.Send;

public class Error extends Frame {
    private String errorMessage;
    public Error(String errorMessage){
        this.errorMessage = errorMessage;
    }

    

    public void enterErrorMessage(Send message){
        errorMessage = "-----" + '\n' + "This message:" + '\n' + message.toString() + '\n' + "-----" + '\n';
    }

    @Override
    public String getType() {
        return "ERROR";
    }

    public String toString(){
        return "ERROR\n" +
                errorMessage + '\n' +
                '\u0000';

    }
}
