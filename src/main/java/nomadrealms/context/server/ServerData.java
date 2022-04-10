package nomadrealms.context.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import context.data.GameData;
import networking.NetworkCluster;
import nomadrealms.model.NomadMini;
import nomadrealms.model.WorldInfo;

public class ServerData extends GameData {

	private Map<Long, NetworkCluster> clusters = new HashMap<>();
	private List<NomadMini> minis = new ArrayList<>();

	public NomadMini selectedMini;

	public ServerData() {
		clusters.put(0L, new NetworkCluster(new WorldInfo(0, "world 1", 0)));
	}

	public List<NomadMini> minis() {
		return minis;
	}

	public NetworkCluster getCluster(long id) {
		return clusters.get(id);
	}

}
