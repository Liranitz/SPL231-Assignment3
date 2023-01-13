package bgu.spl.net.srv.FrameForService;

import bgu.spl.net.srv.Frame;
import bgu.spl.net.srv.FramesForClient.Send;

public class Error extends Frame {
    private String headerMessage;
    private String bodyOfError;
    private String errorMessage;
    public Error(String errorMessage){
        headerMessage = "";
        bodyOfError = "";
        this.errorMessage = errorMessage;
    }

    public void enterErrorHeader(String headerMessage){
        this.headerMessage = "message: " + headerMessage;
    }

    public void enterErrorMessage(Send message){
        bodyOfError = "-----" + '\n' + "This message:" + '\n' + message.toString() + '\n' + "-----" + '\n';
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
