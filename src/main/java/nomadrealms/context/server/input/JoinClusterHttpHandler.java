package nomadrealms.context.server.input;

import static constants.NomadRealmsConstants.TICK_TIME;
import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Random;

import engine.common.networking.packet.address.PacketAddress;
import event.network.c2s.JoinClusterRequestEvent;
import event.network.c2s.JoinClusterResponseEvent;
import event.network.p2p.s2c.JoiningPlayerNetworkEvent;
import model.Player;
import model.PlayerSession;
import networking.NetworkCluster;
import nomadrealms.context.server.ServerData;
import nomadrealms.model.WorldInfo;

public class JoinClusterHttpHandler extends HttpEventHandler<JoinClusterRequestEvent, JoinClusterResponseEvent> {

	public static final int JOIN_TIME_OFFSET = 6000;

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
			data.addCluster(new NetworkCluster(new WorldInfo(0, "This is a test", 12345, 1655063005817L)));
		}
		NetworkCluster cluster = data.getCluster(worldId);
		long nonce = new Random().nextLong();
		long tick0Time = cluster.worldInfo().tick0Time;
		long spawnTick = (currentTimeMillis() + JOIN_TIME_OFFSET - tick0Time) / TICK_TIME;
		long spawnTime = tick0Time + spawnTick * TICK_TIME;
		cluster.playerSessions().removeIf(session -> session.player().uuid() == request.playerId());
		cluster.playerSessions().forEach(peer -> {
			JoiningPlayerNetworkEvent joiningPlayerEvent = new JoiningPlayerNetworkEvent(spawnTick, nonce, request.lanAddress(), clientAddress, 0);
			data.context().sendPacket(joiningPlayerEvent.toPacketModel(peer.wanAddress()));
			data.context().sendPacket(joiningPlayerEvent.toPacketModel(peer.lanAddress()));
			System.out.println("Sending JoiningPlayerNetworkEvent to " + peer.player().username());
		});
		List<PacketAddress> peerLanAddresses = cluster.playerSessions().stream().map(p -> p.lanAddress()).collect(toList());
		List<PacketAddress> peerWanAddresses = cluster.playerSessions().stream().map(p -> p.wanAddress()).collect(toList());
//		String username = data.database().getUsername(request.playerId());
		cluster.addPlayerSession(new PlayerSession(new Player(request.playerId(), "username"), request.lanAddress(), clientAddress));
		System.out.println("Spawning player at tick: " + spawnTick + " time:" + spawnTime);
		int idRange = cluster.generateNewIdRange();
		return new JoinClusterResponseEvent(spawnTime, spawnTick, nonce, "username", peerLanAddresses, peerWanAddresses, 0, idRange);
	}

}
