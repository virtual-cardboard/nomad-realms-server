package nomadrealms.context.server.input;

import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Random;

import engine.common.networking.packet.address.PacketAddress;
import event.network.c2s.JoinClusterRequestEvent;
import event.network.c2s.JoinClusterResponseEvent;
import event.network.p2p.s2c.JoiningPlayerNetworkEvent;
import networking.NetworkCluster;
import networking.PlayerData;
import nomadrealms.context.server.ServerData;
import nomadrealms.model.WorldInfo;

public class JoinClusterHttpHandler extends HttpEventHandler<JoinClusterRequestEvent, JoinClusterResponseEvent> {

	public static final int JOIN_TIME_OFFSET = 5000;

	private final ServerData data;
	private final byte[] bytes = new byte[65536];

	public JoinClusterHttpHandler(ServerData data) {
		super(JoinClusterRequestEvent.class);
		this.data = data;
	}

	@Override
	public JoinClusterResponseEvent handle(JoinClusterRequestEvent request, PacketAddress clientAddress) {
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
		return new JoinClusterResponseEvent(currentTimeMillis() + JOIN_TIME_OFFSET, nonce, peerLanAddresses, peerWanAddresses);
	}

}
