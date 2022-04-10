package networking;

import java.util.ArrayList;
import java.util.List;

import nomadrealms.model.WorldInfo;

public class NetworkCluster {

	protected List<PlayerData> peers = new ArrayList<>();
	protected List<PlayerData> backups = new ArrayList<>();
	protected WorldInfo worldInfo;

	public NetworkCluster(WorldInfo worldInfo) {
		this.worldInfo = worldInfo;
	}

	public void addPeer(PlayerData address) {
		peers.add(address);
	}

	public void addBackup(PlayerData address) {
		backups.add(address);
	}

}
