package bgu.spl.net.impl.stomp;

import bgu.spl.net.srv.EncoderDecoderImplement;
import bgu.spl.net.srv.Server;
import bgu.spl.net.srv.StompMessagingProtocolimplement;

public class StompServer {

    public static void main(String[] args) {
       //System.out.println(args[0]);
      if(args[0].equals("tpc")){
        Server.threadPerClient(
            Integer.parseInt(args[0]), //port
           () -> new StompMessagingProtocolimplement(), //protocol factory
           EncoderDecoderImplement::new //message encoder decoder factory
            ).serve();
      }

      else{
        Server.reactor(
                 Runtime.getRuntime().availableProcessors(),
                 Integer.parseInt(args[0]), //port
                 () -> new StompMessagingProtocolimplement(), //protocol factory
                 EncoderDecoderImplement::new //message encoder decoder factory
         ).serve();
      }
    //  Server.reactor(
    //              Runtime.getRuntime().availableProcessors(),
    //              7777, //port
    //              () -> new StompMessagingProtocolimplement(), //protocol factory
    //              EncoderDecoderImplement::new //message encoder decoder factory
    //      ).serve();



    }
}
