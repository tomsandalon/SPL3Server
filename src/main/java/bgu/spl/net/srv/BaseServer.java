package bgu.spl.net.srv;

import bgu.spl.net.srv.StompServices.StompMessageEncoderDecoder;
import bgu.spl.net.srv.StompServices.StompMessagingProtocolImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Supplier;

public abstract class BaseServer implements Server<String> {

    private final int port;
    private final Supplier<StompMessagingProtocolImpl> protocolFactory;
    private final Supplier<StompMessageEncoderDecoder> encdecFactory;
    private ServerSocket sock;
    private Connections<String> connections;

    public BaseServer(int port, Supplier<StompMessagingProtocolImpl> protocolFactory, Supplier<StompMessageEncoderDecoder> encdecFactory) {

        this.port = port;
        this.protocolFactory = protocolFactory;
        this.encdecFactory = encdecFactory;
        this.sock = null;
        this.connections = new ConnectionsImpl();
    }

    @Override
    public void serve() {

        int connectionId;

        try (ServerSocket serverSock = new ServerSocket(port)) {
            System.out.println("Server started");

            this.sock = serverSock; //just to be able to close

            while (!Thread.currentThread().isInterrupted()) {

                Socket clientSock = serverSock.accept();

                BlockingConnectionHandler<String> handler = new BlockingConnectionHandler<>(clientSock, encdecFactory.get(), protocolFactory.get());

                connectionId = connections.getAndIncConnectionCounter();

                handler.getProtocol().start(connectionId, connections);

                connections.getConnectionHandlerId().put(connectionId, handler);

                execute(handler);
            }
        } catch (IOException ignored) {
        }

        System.out.println("server closed!!!");
    }

    @Override
    public void close() throws IOException {
        if (sock != null) sock.close();
    }

    protected void execute(BlockingConnectionHandler<String> handler) {
        handler.run();
    }

}
