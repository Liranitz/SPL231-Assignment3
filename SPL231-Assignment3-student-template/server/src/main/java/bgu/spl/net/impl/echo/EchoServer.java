package bgu.spl.net.impl.echo;

import bgu.spl.net.srv.*;

import java.util.function.Supplier;

public class EchoServer {

    public static void main(String[] args) {

        // you can use any server... 
      //  Server.threadPerClient(
     //           7777, //port
     //           () -> new EchoProtocol(), //protocol factory
      //          LineMessageEncoderDecoder::new //message encoder decoder factory
      //  ).serve();

        //  Server.threadPerClient(
        //         7777, //port
        //        () -> new StompMessagingProtocolimplement(), //protocol factory
        //        EncoderDecoderImplement::new //message encoder decoder factory
        // ).serve();

        // Server.reactor(
        //          Runtime.getRuntime().availableProcessors(),
        //          7777, //port
        //          () -> new StompMessagingProtocolimplement() , //protocol factory
        //          () -> new EncoderDecoderImplement() //message encoder decoder factory
        //  ).serve();

      //          Server.reactor(
      //          Runtime.getRuntime().availableProcessors(),
      //          7777, //port
      //          () -> new StompMessagingProtocolimplement(), //protocol factory
      //          EncoderDecoderImplement::new //message encoder decoder factory
      //  ).serve();

          // Supplier<StompMessagingProtocolimplement> protocolFactory;
        // Supplier<EncoderDecoderImplement> encdecFactory;
        //     this.port = port;
        //     this.protocolFactory = protocolFactory;
        //     this.encdecFactory = encdecFactory;
        //     this.sock = null;
        //     connections = new ConnectionImpl<>();
        //     connectionsHandlerId = 1;
        // }
        // Server<Frame> reactor = Server.reactor(
        //          Runtime.getRuntime().availableProcessors(),
        //          7777, //port
        //          () -> new StompMessagingProtocolimplement(), //protocol factory
        //          EncoderDecoderImplement::new //message encoder decoder factory
        //  );
        //  reactor.serve();

        //  Server<Frame> react = new Reactor(Runtime.getRuntime().availableProcessors(),
        //  7777, //port
        //  () -> new StompMessagingProtocolimplement(), //protocol factory
        //  EncoderDecoderImplement::new);
        
        // react.serve();
        //}

}



