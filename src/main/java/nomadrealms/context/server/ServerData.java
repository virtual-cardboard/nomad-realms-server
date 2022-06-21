package nomadrealms.context.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import context.data.GameData;
import networking.NetworkCluster;
import nomadrealms.context.server.data.db.NomadRealmsDatabase;
import nomadrealms.model.NomadMini;

public class ServerData extends GameData {

	private Map<Long, NetworkCluster> clusters = new HashMap<>();
	private List<NomadMini> minis = new ArrayList<>();

	public NomadMini selectedMini;

	private NomadRealmsDatabase database;

	@Override
	protected void init() {
		database = new NomadRealmsDatabase();
		database.connect();
		System.out.println("Connected to database");
	}

	public List<NomadMini> minis() {
		return minis;
	}

	public NetworkCluster getCluster(long id) {
		return clusters.get(id);
	}

	public NomadRealmsDatabase database() {
		return database;
	}

	public void addCluster(NetworkCluster cluster) {
		clusters.put(cluster.worldInfo().id, cluster);
	}

}
