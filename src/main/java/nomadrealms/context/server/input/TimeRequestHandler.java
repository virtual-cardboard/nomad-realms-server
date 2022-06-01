package nomadrealms.context.server.input;

import static java.lang.System.currentTimeMillis;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class TimeRequestHandler implements HttpHandler {

	public TimeRequestHandler() {
		currentTimeMillis();
	}

	@Override
	public void handle(HttpExchange t) throws IOException {
		t.sendResponseHeaders(200, Long.BYTES);
		OutputStream os = t.getResponseBody();
		byte[] bytes = new byte[8];

		long time = currentTimeMillis();

		bytes[0] = (byte) ((time >> 56) & 0xFF);
		bytes[1] = (byte) ((time >> 48) & 0xFF);
		bytes[2] = (byte) ((time >> 40) & 0xFF);
		bytes[3] = (byte) ((time >> 32) & 0xFF);
		bytes[4] = (byte) ((time >> 24) & 0xFF);
		bytes[5] = (byte) ((time >> 16) & 0xFF);
		bytes[6] = (byte) ((time >> 8) & 0xFF);
		bytes[7] = (byte) (time & 0xFF);
		os.write(bytes);
	}

}
