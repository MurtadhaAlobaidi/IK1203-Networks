package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {
	public TCPClient () {
		
	}
    
    public byte[] askServer(String hostname, int port, byte [] toServerBytes) throws IOException {    	
    	
    	Socket Clientsocket = new Socket(hostname, port);
    	
        byte[] fromUserBuffer  = new byte[1];
        
        OutputStream outPutServer = Clientsocket.getOutputStream();
        outPutServer.write(toServerBytes,0,toServerBytes.length);
        
        InputStream inputServer = Clientsocket.getInputStream();
        
    	ByteArrayOutputStream fromServer = new ByteArrayOutputStream();
    	
        int s = -1;
        while ((s = inputServer.read(fromUserBuffer)) != -1){
        	fromServer.write(fromUserBuffer,0,fromUserBuffer.length);
        }
        Clientsocket.close();        
        return fromServer.toByteArray();
    }
}
