package bgu.spl.net.impl.stomp;

import bgu.spl.net.srv.EncoderDecoderImplement;
import bgu.spl.net.srv.Server;
import bgu.spl.net.srv.StompMessagingProtocolimplement;

public class StompServer {

    public static void main(String[] args) {
       
      
        Server.threadPerClient(
            7777, //port
           () -> new StompMessagingProtocolimplement(), //protocol factory
           EncoderDecoderImplement::new //message encoder decoder factory
    ).serve();

    //  Server.reactor(
    //              Runtime.getRuntime().availableProcessors(),
    //              7777, //port
    //              () -> new StompMessagingProtocolimplement(), //protocol factory
    //              EncoderDecoderImplement::new //message encoder decoder factory
    //      ).serve();



    }
}
