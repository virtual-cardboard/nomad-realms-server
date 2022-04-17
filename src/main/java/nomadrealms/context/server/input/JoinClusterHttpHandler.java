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
		String query = t.getRequestURI().getQuery();
		Map<String, String> map = queryToMap(query);
		String name = map.get("name");
		System.out.println(name);
		String response = "Hello, " + name + "! You have reached the join cluster endpoint";
		t.sendResponseHeaders(200, response.length());
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
