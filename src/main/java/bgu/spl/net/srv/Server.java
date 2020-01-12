package bgu.spl.net.srv;

import bgu.spl.net.srv.StompServices.StompMessageEncoderDecoder;
import bgu.spl.net.srv.StompServices.StompMessagingProtocolImpl;

import java.io.Closeable;
import java.util.function.Supplier;

public interface Server extends Closeable {

    /**
     * This function returns a new instance of a thread per client pattern server
     *
     * @param port                  The port for the server socket
     * @param protocolFactory       A factory that creats new MessagingProtocols
     * @param encoderDecoderFactory A factory that creats new MessageEncoderDecoder
     * @return A new Thread per client server
     */
    static Server threadPerClient(int port, Supplier<StompMessagingProtocolImpl> protocolFactory, Supplier<StompMessageEncoderDecoder> encoderDecoderFactory) {

        return new BaseServer(port, protocolFactory, encoderDecoderFactory) {
            @Override
            protected void execute(BlockingConnectionHandler<String> handler) {
                new Thread(handler).start();
            }
        };

    }

    /**
     * This function returns a new instance of a reactor pattern server
     *
     * @param nthreads              Number of threads available for protocol processing
     * @param port                  The port for the server socket
     * @param protocolFactory       A factory that creats new MessagingProtocols
     * @param encoderDecoderFactory A factory that creats new MessageEncoderDecoder
     * @return A new reactor server
     */
    static Server reactor(int nthreads, int port, Supplier<StompMessagingProtocolImpl> protocolFactory, Supplier<StompMessageEncoderDecoder> encoderDecoderFactory) {
        return new Reactor(nthreads, port, protocolFactory, encoderDecoderFactory);
    }

    /**
     * The main loop of the server, Starts listening and handling new clients.
     */
    void serve();

}
