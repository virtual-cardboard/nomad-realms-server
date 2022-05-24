package nomadrealms.context.server.input;

import static java.lang.Math.abs;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import engine.common.QueueGroup;
import event.network.c2s.JoinClusterResponseEvent;

public class JoinClusterHttpHandler implements HttpHandler {

	private QueueGroup queueGroup;

	public JoinClusterHttpHandler(QueueGroup queueGroup) {
		this.queueGroup = queueGroup;
	}

	@Override
	public void handle(HttpExchange t) throws IOException {
		t.getRemoteAddress();
		String query = t.getRequestURI().getQuery();
		t.getRequestHeaders().forEach((s, list) -> System.out.println(list));
		byte[] response;
		String clientWanAddress = t.getRemoteAddress().getHostName();
		int clientWanPort = t.getRemoteAddress().getPort();
		System.out.println("Received request from " + clientWanAddress + " port=" + clientWanPort);
		if (t.getRequestHeaders().containsKey("from") && t.getRequestHeaders().get("from").contains("nomad-realms")) {
			Map<String, String> map = queryToMap(query);
			String name = map.get("name");
			System.out.println(name);
			response = new JoinClusterResponseEvent(abs(new Random().nextLong()), new ArrayList<>(), new ArrayList<>()).serialize();
//			response.getBytes();
			t.sendResponseHeaders(200, response.length);
		} else {
			response = new JoinClusterResponseEvent(-1, new ArrayList<>(), new ArrayList<>()).serialize();
			t.sendResponseHeaders(400, response.length);
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		OutputStream os = t.getResponseBody();
		os.write(response);
		os.close();
	}

	public Map<String, String> queryToMap(String query) {
		if (query == null) {
			return null;
		}
		Map<String, String> result = new HashMap<>();
		for (String param : query.split("&")) {
			String[] entry = param.split("=");
			if (entry.length > 1) {
				result.put(entry[0], entry[1]);
			} else {
				result.put(entry[0], "");
			}
		}
		return result;
	}

}
