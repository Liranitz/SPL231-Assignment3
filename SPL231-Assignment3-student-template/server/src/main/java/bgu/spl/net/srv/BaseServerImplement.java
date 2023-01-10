package bgu.spl.net.srv;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.MessagingProtocol;

import java.util.function.Supplier;
//figure out if we need to extend the base server or change the original one
public class BaseServerImplement<Frame> extends BaseServer<Frame>{
    public BaseServerImplement(int port, Supplier<StompMessagingProtocolimplement> protocolFactory, Supplier<EncoderDecoderImplement> encdecFactory) {
        super(port, protocolFactory, encdecFactory);
    }

    @Override
    protected void execute(BlockingConnectionHandler handler) {

    }
}
