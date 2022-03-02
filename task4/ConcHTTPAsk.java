import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConcHTTPAsk {

	public static void main(String[] args) throws IOException {

		try (ServerSocket welcomeSocket = new ServerSocket(Integer.parseInt(args[0]))) {
			System.out.println("Connected to HTTP Server" + welcomeSocket);

			while (true) {

				Socket HTTPSocket = welcomeSocket.accept();

				MyRunnable concurrentClient = new MyRunnable(HTTPSocket);
				Thread concurrentClientThread = new Thread(concurrentClient);
				concurrentClientThread.start();

			}
		} catch (IOException error) {
			System.err.println(error);
		}

	}

}
