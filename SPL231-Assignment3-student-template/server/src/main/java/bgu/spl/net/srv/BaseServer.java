package bgu.spl.net.srv;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.api.StompMessagingProtocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Supplier;

public abstract class BaseServer<T> implements Server<T> { // thread per client

    private final int port;
    private final Supplier<StompMessagingProtocolimplement> protocolFactory;
    private final Supplier<EncoderDecoderImplement> encdecFactory;
    private ServerSocket sock;
    private ConnectionImpl<Frame> connections;
    private int  connectionsHandlerId;

    public BaseServer(// need to check if the clien already logged in here or in the client protocol?????????
            int port,
            Supplier<StompMessagingProtocolimplement> protocolFactory,
            Supplier<EncoderDecoderImplement> encdecFactory) {

        this.port = port;
        this.protocolFactory = protocolFactory;
        this.encdecFactory = encdecFactory;
		this.sock = null;
        connections = new ConnectionImpl<>();
        connectionsHandlerId = 1;
    }

    @Override
    public void serve() {

        try (ServerSocket serverSock = new ServerSocket(port)) {
			System.out.println("Server started");

            this.sock = serverSock; //just to be able to close

            while (!Thread.currentThread().isInterrupted()) {

                Socket clientSock = serverSock.accept();
                StompMessagingProtocolimplement protocol = protocolFactory.get();
                protocol.start(connectionsHandlerId , connections);

                BlockingConnectionHandler<T> handler = new BlockingConnectionHandler(
                        clientSock,
                        encdecFactory.get(),
                        protocol,
                        connectionsHandlerId,
                        connections);
                connections.addNewConnectionHandler(connectionsHandlerId, handler); // add new connection to the connectionMap- is the id unique per trting of connection or per client???????????????
                connectionsHandlerId++; //just for testing need to rmove note
                execute(handler);
            }
        } catch (IOException ex) {
        }

        System.out.println("server closed!!!");
    }

    @Override
    public void close() throws IOException {
		if (sock != null)
			sock.close();
    }

    protected abstract void execute(BlockingConnectionHandler<T>  handler);

}
