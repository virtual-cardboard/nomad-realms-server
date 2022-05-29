package nomadrealms.context.server.input;

import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import engine.common.networking.packet.address.PacketAddress;
import event.network.c2s.JoinClusterRequestEvent;
import event.network.c2s.JoinClusterResponseEvent;
import event.network.p2p.s2c.JoiningPlayerNetworkEvent;
import networking.NetworkCluster;
import networking.PlayerData;
import nomadrealms.context.server.ServerData;
import nomadrealms.model.WorldInfo;

public class JoinClusterHttpHandler implements HttpHandler {

	private final ServerData data;
	private final byte[] bytes = new byte[65536];

	public JoinClusterHttpHandler(ServerData data) {
		this.data = data;
	}

	@Override
	public void handle(HttpExchange t) throws IOException {
//		t.getRequestHeaders().forEach((s, list) -> System.out.println(s + ": " + list));
		PacketAddress clientAddress = new PacketAddress(t.getRemoteAddress().getAddress(), t.getRemoteAddress().getPort());
		System.out.println("Received request from " + clientAddress);
		int numRead = t.getRequestBody().read(bytes);
		try {
			JoinClusterRequestEvent request = new JoinClusterRequestEvent(Arrays.copyOfRange(bytes, 0, numRead));
			long worldId = request.worldID();
			System.out.println(clientAddress + " is trying to join world with Id=" + worldId);
			if (data.getCluster(worldId) == null) {
				data.addCluster(new NetworkCluster(new WorldInfo(0, "This is a test", 12345)));
			}
			NetworkCluster cluster = data.getCluster(worldId);
			long nonce = new Random().nextLong();
			cluster.peers().forEach(peer -> {
				JoiningPlayerNetworkEvent joiningPlayerEvent = new JoiningPlayerNetworkEvent(currentTimeMillis(), nonce, request.lanAddress(), clientAddress);
				data.context().sendPacket(joiningPlayerEvent.toPacketModel(peer.wanAddress));
				data.context().sendPacket(joiningPlayerEvent.toPacketModel(peer.lanAddress));
				System.out.println("Sending JoiningPlayerNetworkEvent to " + peer.username);
			});
			List<PacketAddress> peerLanAddresses = cluster.peers().stream().map(p -> p.lanAddress).collect(toList());
			List<PacketAddress> peerWanAddresses = cluster.peers().stream().map(p -> p.wanAddress).collect(toList());
			cluster.addPeer(new PlayerData(clientAddress, request.lanAddress(), request.username()));
			JoinClusterResponseEvent response = new JoinClusterResponseEvent(nonce, peerLanAddresses, peerWanAddresses);
			byte[] serializedResponse = response.serialize();

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			t.sendResponseHeaders(200, serializedResponse.length);
			OutputStream os = t.getResponseBody();
			os.write(serializedResponse);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not create JoinClusterRequestEvent from the HTTP request body. 400 Bad Request");
			t.sendResponseHeaders(400, 0);
			OutputStream os = t.getResponseBody();
			os.write(new byte[] {});
			os.close();
		}
	}

}
