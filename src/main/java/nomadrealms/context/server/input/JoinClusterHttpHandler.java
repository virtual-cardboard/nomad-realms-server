package nomadrealms.context.server.input;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import event.network.c2s.JoinClusterRequestEvent;
import event.network.c2s.JoinClusterResponseEvent;
import nomadrealms.context.server.ServerData;

public class JoinClusterHttpHandler implements HttpHandler {

	private final byte[] bytes = new byte[65536];

	public JoinClusterHttpHandler(ServerData data) {
	}

	@Override
	public void handle(HttpExchange t) throws IOException {
		t.getRemoteAddress();
		String query = t.getRequestURI().getQuery();
		t.getRequestHeaders().forEach((s, list) -> System.out.println(list));
		String clientWanAddress = t.getRemoteAddress().getHostName();
		int clientWanPort = t.getRemoteAddress().getPort();
		System.out.println("Received request from " + clientWanAddress + " port=" + clientWanPort);
		int numRead = t.getRequestBody().read(bytes);
		try {
			JoinClusterRequestEvent request = new JoinClusterRequestEvent(Arrays.copyOfRange(bytes, 0, numRead));
			long worldId = request.worldID();
			System.out.println(clientWanAddress + ":" + clientWanPort + " is trying to join world with Id=" + worldId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not create JoinClusterRequestEvent from the HTTP request body. 400 Bad Request");
			t.sendResponseHeaders(400, 0);
			OutputStream os = t.getResponseBody();
			os.write(new byte[] {});
			os.close();
			return;
		}
		JoinClusterResponseEvent response = new JoinClusterResponseEvent(new Random().nextLong(), new ArrayList<>(), new ArrayList<>());
		byte[] serializedResponse = response.serialize();
		t.sendResponseHeaders(200, serializedResponse.length);

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		OutputStream os = t.getResponseBody();
		os.write(serializedResponse);
		os.close();
	}

}
