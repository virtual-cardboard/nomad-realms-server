package nomadrealms.context.server.input;

import static java.lang.System.currentTimeMillis;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import event.network.c2s.TimeResponseEvent;

public class TimeRequestHandler implements HttpHandler {

	public TimeRequestHandler() {
		currentTimeMillis();
	}

	@Override
	public void handle(HttpExchange t) throws IOException {
		System.out.println("Received time request from " + t.getRemoteAddress());
		long receiveTime = currentTimeMillis();
		TimeResponseEvent response = new TimeResponseEvent(receiveTime, currentTimeMillis());
		byte[] bytes = response.serialize();
		t.sendResponseHeaders(200, bytes.length);
		OutputStream os = t.getResponseBody();
		os.write(bytes);
		os.close();
	}

}
