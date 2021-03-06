package SSS;

import SSS.Event.EventHandler;
import SSS.Util.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executors;

public class Server {
	private int port = 5003;
	private ServerSocket serverSocket;

	public ClientHandler clientHandler;
	public InternalValues values;
	public InputHandler inputHandler;
	public EventHandler eventHandler;

	private static Server instance;

	public static void main(String[] args) {
		for (String arg : args) {
			if (arg.equals("-v")) {
				Logger.debug = true;
				Logger.debug("Debug true");
			}
		}
		try {
			new Server();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Server() throws IOException {
		instance = this;

		values = new InternalValues();
		inputHandler = new InputHandler();
		eventHandler = new EventHandler();
		serverSocket = new ServerSocket(port);
		clientHandler = new ClientHandler(serverSocket);

		inputHandler.run();
		clientHandler.acceptConnections();
	}

	/**
	 * Return the running instance of the Server
	 * @return The instance of the Server
	 */
	public static Server get() {
		return instance;
	}
}
