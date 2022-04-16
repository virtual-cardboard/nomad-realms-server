package nomadrealms.context.server;

import static context.visuals.colour.Colour.rgb;
import static java.lang.Math.random;

import java.net.Inet4Address;
import java.util.List;

import context.logic.GameLogic;
import event.network.bootstrap.BootstrapRequestEvent;
import event.network.join.JoinClusterRequestEvent;
import event.network.join.JoinClusterResponseEvent;
import event.network.join.JoinEmptyClusterResponseEvent;
import networking.NetworkCluster;
import networking.PlayerData;
import nomadrealms.model.NomadMini;
import nomadrealms.model.WorldInfo;

public class ServerLogic extends GameLogic {

	@Override
	protected void init() {
		ServerData data = (ServerData) context().data();
//		addHandler(GameEvent.class, event -> {
//			throw new RuntimeException("Was expecting Bootstrap Request");
//		});
		addHandler(BootstrapRequestEvent.class, (event) -> {
			int rgb = rgb((int) (255 * random()), (int) (255 * random()), (int) (255 * random()));
			data.minis().add(new NomadMini(rgb, event.username, event.lanAddress, event.source().address()));
			System.out.println(event.lanAddress);
		});
		addHandler(JoinClusterRequestEvent.class, event -> {
			NetworkCluster cluster = data.getCluster(event.worldID);
			if (cluster == null) {
				cluster = new NetworkCluster(new WorldInfo(event.worldID, "World 0", 0));
				data.addCluster(cluster);
				cluster.addPeer(new PlayerData(event.source().address(), event.lanAddress, event.username));
				JoinEmptyClusterResponseEvent response = new JoinEmptyClusterResponseEvent();
				context().sendPacket(response.toPacket(event.source().address()));
			} else {
				List<PlayerData> peers = cluster.peers();
				Inet4Address[] lanAddresses = new Inet4Address[peers.size()];
				Inet4Address[] wanAddresses = new Inet4Address[peers.size()];
				for (int i = 0; i < peers.size(); i++) {
					PlayerData peer = peers.get(i);
					lanAddresses[i] = (Inet4Address) peer.lanAddress.ip();
					wanAddresses[i] = (Inet4Address) peer.wanAddress.ip();
				}
				JoinClusterResponseEvent response = new JoinClusterResponseEvent(lanAddresses, wanAddresses);
				context().sendPacket(response.toPacket(event.source().address()));
			}
		});
//		Random rand = new Random();
//		addHandler(CreateWorldRequestEvent.class, (event) -> {
//			WorldInfo worldInfo = new WorldInfo();
//			worldInfo.name = event.worldName();
//			worldInfo.seed = rand.nextLong();
//			data.worldInfos().add(worldInfo);
//			context().sendPacket(new CreateWorldResponseEvent(worldInfo.seed).toPacket(event.source().address()));
//		});
	}

	@Override
	public void update() {
	}

}
