package nomadrealms.context.server.input;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import engine.common.networking.packet.address.PacketAddress;
import nomadrealms.context.server.ServerData;

public class JoinClusterSuccessEventHandler implements HttpHandler {

	private final ServerData data;

	public JoinClusterSuccessEventHandler(ServerData data) {
		this.data = data;
	}

	@Override
	public void handle(HttpExchange t) throws IOException {
		PacketAddress clientAddress = new PacketAddress(t.getRemoteAddress().getAddress(), t.getRemoteAddress().getPort());
		System.out.println("Client at " + clientAddress + " has successfully joined cluster.");
		t.sendResponseHeaders(200, 0);
		OutputStream os = t.getResponseBody();
		os.close();
	}

}
