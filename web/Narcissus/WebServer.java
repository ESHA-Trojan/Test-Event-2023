import java.io.*;
import java.net.*;
import java.util.*;

public class WebServer {

	public static void main(String[] args) throws IOException {
		int port = 80;
		
		ServerSocket serverSocket = new ServerSocket(port);

		System.out.println("Started webserver, listening for connections...");

		try {
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("Accepted connection");
	
				HttpRequest httpRequest = new HttpRequest(socket);
				Thread thread = new Thread(httpRequest);
				thread.start();
			}
		} finally {
			serverSocket.close();
			System.out.println("Webserver closed");
		}
	}

}