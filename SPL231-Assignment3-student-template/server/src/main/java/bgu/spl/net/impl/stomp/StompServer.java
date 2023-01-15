package bgu.spl.net.impl.stomp;

import bgu.spl.net.srv.EncoderDecoderImplement;
import bgu.spl.net.srv.Server;
import bgu.spl.net.srv.StompMessagingProtocolimplement;

public class StompServer {

    public static void main(String[] args) {
<<<<<<< HEAD
     // if(args[0].equals("tpc")){
=======
        //String[] args_new = args[0].split(" ");
       //System.out.println(args[0]);
      if(args[1].equals("tpc")){
>>>>>>> 7782d9ff4cf37ef8c7c39dc4f77df45f8e788b49
        Server.threadPerClient(
           7777, //port
           () -> new StompMessagingProtocolimplement(), //protocol factory
           EncoderDecoderImplement::new //message encoder decoder factory
            ).serve();
     // }

    //  else if(args[0].equals("reactor")){
        Server.reactor(
                 Runtime.getRuntime().availableProcessors(),
                7777, //port
                 () -> new StompMessagingProtocolimplement(), //protocol factory
                 EncoderDecoderImplement::new //message encoder decoder factory
         ).serve();
     // }
    //  Server.reactor(
    //              Runtime.getRuntime().availableProcessors(),
    //              7777, //port
    //              () -> new StompMessagingProtocolimplement(), //protocol factory
    //              EncoderDecoderImplement::new //message encoder decoder factory
    //      ).serve();
    }
}
