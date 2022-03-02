package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {
    boolean shutdown = false;
    Integer timeout = null;
    Integer limit = null;

    public TCPClient(boolean shutdown, Integer timeout, Integer limit) {
        this.timeout = timeout;
        this.shutdown = shutdown;
        this.limit = limit;
    }

    public byte[] askServer(String hostname, int port, byte[] toServerBytes) throws IOException {

        Socket Clientsocket = new Socket(hostname, port);

        if (timeout != null) {
            Clientsocket.setSoTimeout(timeout);
        }

        ByteArrayOutputStream fromServer = new ByteArrayOutputStream();

        byte[] fromUserBuffer = new byte[1024];

        try {
            OutputStream out = Clientsocket.getOutputStream();
            out.write(toServerBytes, 0, toServerBytes.length);

            if (shutdown) {
                Clientsocket.shutdownOutput();
            }

            InputStream in = Clientsocket.getInputStream();

            if (limit == null) {
                int s = 0;
                while ((s = in.read(fromUserBuffer)) != -1) {
                    fromServer.write(fromUserBuffer, 0, s);
                }

            } else {
                Integer counter = 0;
                Integer temp = limit;
                if (temp > 1024) {
                    counter = 1024;
                }
                int s = 0;
                if (limit < 1024) {
                    in.read(fromUserBuffer, 0, limit);
                    fromServer.write(fromUserBuffer);

                } else {
                    while (temp > 0 && (s = in.read(fromUserBuffer, 0, counter)) != -1) {
                        fromServer.write(fromUserBuffer, 0, s);
                        temp = temp - s;
                        if (s < temp && temp > 1024) {
                            counter = 1024;
                        } else {
                            counter = temp;
                        }

                    }
                }
            }
        } catch (SocketTimeoutException e) {
            Clientsocket.shutdownOutput();
        } finally {
            return fromServer.toByteArray();
        }

    }
}

