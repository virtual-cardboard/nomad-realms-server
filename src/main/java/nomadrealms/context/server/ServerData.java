package nomadrealms.context.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import context.data.GameData;
import networking.NetworkCluster;
import nomadrealms.model.NomadMini;

public class ServerData extends GameData {

	private Map<Long, NetworkCluster> clusters = new HashMap<>();
	private List<NomadMini> minis = new ArrayList<>();

	public NomadMini selectedMini;

	public List<NomadMini> minis() {
		return minis;
	}

	public NetworkCluster getCluster(long id) {
		return clusters.get(id);
	}

	public void addCluster(NetworkCluster cluster) {
		clusters.put(cluster.worldInfo().id, cluster);
	}

}
