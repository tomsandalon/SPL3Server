package bgu.spl.net.srv;

import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.srv.StompServices.StompMessageEncoderDecoder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class BlockingConnectionHandler<T> implements Runnable, ConnectionHandler<T> {

    private final StompMessagingProtocol protocol;
    private final StompMessageEncoderDecoder encdec;
    private final Socket sock;
    private BufferedInputStream in;
    private BufferedOutputStream out;
    private volatile boolean connected = true;
    private final List<T> sendList = new LinkedList<>();

    public BlockingConnectionHandler(Socket sock, StompMessageEncoderDecoder reader, StompMessagingProtocol protocol) {
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

            while (!protocol.shouldTerminate() && connected) {
                while ((read = in.read()) >= 0) {
                    String nextMessage = encdec.decodeNextByte((byte) read);
                    if (nextMessage != null) {
                        protocol.process(nextMessage);
                    }
                }
                if (!sendList.isEmpty()) {
                    synchronized (sendList) {
                        for (T s : sendList) {
                            out.write(encdec.encode((String) s));
                            out.flush();
                        }
                        sendList.clear();
                    }
                }
                try {
                    this.wait();
                } catch (InterruptedException ignore) {
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void close() throws IOException {
        connected = false;
        sock.close();
    }

    @Override
    public void send(T msg) {
        if (msg != null) {
            synchronized (sendList) {
                sendList.add(msg);
                notifyAll();
            }
        }
    }
}
