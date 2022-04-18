package nomadrealms.context.server.input;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import engine.common.QueueGroup;

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
		String response;
		if (t.getRequestHeaders().containsKey("from") && t.getRequestHeaders().get("from").contains("nomad-realms")) {
			Map<String, String> map = queryToMap(query);
			String name = map.get("name");
			System.out.println(name);
			response = "Hello, " + name + "! You have reached the join cluster endpoint";
//			response.getBytes();
			t.sendResponseHeaders(200, response.length());
		} else {
			response = "Access Denied. Sorry!";
			t.sendResponseHeaders(400, response.length());
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
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
