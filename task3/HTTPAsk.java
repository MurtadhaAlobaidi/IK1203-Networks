import java.net.*;

import tcpclient.TCPClient;

import java.io.*;

public class HTTPAsk {
	private static int BUFFERSIZE = 1024;

	static boolean shutdown = false;
	static Integer timeout = null;
	static Integer limit = null;

	public static void main(String[] args) throws IOException {

		String status = null;
		String stringClient = "";
		String hostname = null;
		int port = 0;
		String res = null;

		byte[] fromClientBuffer = new byte[BUFFERSIZE];

		ServerSocket welcomeSocket = new ServerSocket(8888);
		System.out.println("Connected to HTTP Server" + welcomeSocket);

		while (true) {

			Socket HTTPSocket = welcomeSocket.accept();
			try {
				InputStream inFromClient = HTTPSocket.getInputStream();

				OutputStream outToClient = HTTPSocket.getOutputStream();

				System.out.println("Conected");

				String decodes = " ";

				int counter = inFromClient.read(fromClientBuffer);

				while (counter != -1) {
					decodes = new String(fromClientBuffer, 0, counter);

					String[] splitString = decodes.split("[?&= ]", 10);

					if (splitString[0].equals("GET") && splitString[1].equals("/ask") && decodes.contains("HTTP/1.1")) {
						status = ("HTTP/1.1 200 OK \r\n\r\n");
						for (int i = 0; i < splitString.length; i++) {
							if (splitString[i].equals("hostname")) {
								hostname = splitString[i + 1];
							} else if (splitString[i].equals("port")) {
								port = Integer.parseInt(splitString[i + 1]);
							} else if (splitString[i].equals("string")) {
								stringClient = splitString[i + 1];
							}
						}
						outToClient.write(status.getBytes("UTF-8"));
					} else {
						status = ("HTTP/1.1 400 Bad Request \r\n");
					}

					break;

				}

				if (!(status.contains("HTTP/1.1 400 Bad Request"))) {

					try {
						byte[] toServerByte = stringClient.getBytes("UTF-8");

						TCPClient tcpClient = new TCPClient(shutdown, timeout, limit);
						byte[] result = tcpClient.askServer(hostname, port, toServerByte);
						HTTPSocket.getOutputStream().write(result);

					} catch (IOException e) {
						status = ("HTTP/1.1 404 Not Found \r\n");
						HTTPSocket.getOutputStream().write(status.getBytes("UTF-8"));
					}
				} else {
					HTTPSocket.getOutputStream().write(status.getBytes("UTF-8"));
				}

				HTTPSocket.close();

			} catch (IOException ex) {
				System.out.println("ERROR");
			}
		}

	}

}
