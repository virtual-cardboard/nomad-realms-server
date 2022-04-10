package networking;

import java.util.ArrayList;
import java.util.List;

import context.input.networking.packet.address.PacketAddress;
import nomadrealms.model.WorldInfo;

public class NetworkCluster {

	protected List<PacketAddress> peers = new ArrayList<>();
	protected List<PacketAddress> backups = new ArrayList<>();
	protected WorldInfo worldInfo;

	public NetworkCluster(WorldInfo worldInfo) {
		this.worldInfo = worldInfo;
	}

	public void addPeer(PacketAddress address) {
		peers.add(address);
	}

	public void addBackup(PacketAddress address) {
		backups.add(address);
	}

}
