package bgu.spl.net.srv;

import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.srv.StompServices.StompMessageEncoderDecoder;
import bgu.spl.net.srv.StompServices.StompMessagingProtocolImpl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class BlockingConnectionHandler<T> implements Runnable, ConnectionHandler<T> {

    public StompMessagingProtocol getProtocol() {
        return protocol;
    }

    private final StompMessagingProtocolImpl protocol;
    private final StompMessageEncoderDecoder encdec;
    private final Socket sock;
    private BufferedInputStream in;
    private BufferedOutputStream out;
    private volatile boolean connected = true;

    public BlockingConnectionHandler(Socket sock, StompMessageEncoderDecoder reader, StompMessagingProtocolImpl protocol) {
        this.sock = sock;
        this.encdec = reader;
        this.protocol = protocol;
    }

    @Override
    public void run() {
        try (Socket sock = this.sock) { //just for automatic closing
            int read;

            in = new BufferedInputStream(sock.getInputStream());
            out = new BufferedOutputStream(sock.getOutputStream());

            while (!protocol.shouldTerminate() && connected && (read = in.read()) >= 0) {
                String nextMessage = encdec.decodeNextByte((byte) read);
                if (nextMessage != null) {
                    protocol.process(nextMessage);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            synchronized (this) { //just so we wouldn't exit while sending a message
                try {
                    close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void close() throws IOException {
        connected = false;
        try{
            if (!protocol.shouldTerminate()) {
                protocol.getConnections().disconnect(protocol.getConnectionId());
            }
        }
        catch (Exception ignored){}
        sock.close();
    }

    @Override
    public synchronized void send(T msg) {
        if (protocol.shouldTerminate()) return; //don't send if the thread needs to be closed
        try {
            out.write(encdec.encode((String) msg));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

