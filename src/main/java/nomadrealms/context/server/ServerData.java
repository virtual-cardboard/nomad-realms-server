package nomadrealms.context.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import context.data.GameData;
import context.game.data.DebugTools;
import networking.NetworkCluster;
import nomadrealms.context.server.data.db.NomadRealmsDatabase;
import nomadrealms.model.NomadMini;

public class ServerData extends GameData {

	private final DebugTools tools;

	private Map<Long, NetworkCluster> clusters = new HashMap<>();
	private List<NomadMini> minis = new ArrayList<>();

	public NomadMini selectedMini;

	private NomadRealmsDatabase database;

	public ServerData(DebugTools tools) {
		this.tools = tools;
	}

	@Override
	protected void init() {
		database = new NomadRealmsDatabase();
		database.connect();
		tools.logMessage("Connected to database", 0xeba8FF);
	}

	public DebugTools tools() {
		return tools;
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
