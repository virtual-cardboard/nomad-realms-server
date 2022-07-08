package networking;

import java.util.ArrayList;
import java.util.List;

import model.Player;
import model.PlayerSession;
import nomadrealms.model.WorldInfo;

public class NetworkCluster {

	protected List<PlayerSession> playerSessions = new ArrayList<>();
	protected List<Integer> availableIdRanges = new ArrayList<>();

	protected List<Player> backups = new ArrayList<>();
	protected WorldInfo worldInfo;

	public NetworkCluster(WorldInfo worldInfo) {
		this.worldInfo = worldInfo;
		availableIdRanges.add(0);
	}

	public void addPlayerSession(PlayerSession session) {
		playerSessions.add(session);
	}

	public void addBackup(Player address) {
		backups.add(address);
	}

	public List<PlayerSession> playerSessions() {
		return playerSessions;
	}

	public WorldInfo worldInfo() {
		return worldInfo;
	}

	public int generateNewIdRange() {
		int id = availableIdRanges.remove(0);
		if (availableIdRanges.isEmpty()) {
			availableIdRanges.add(id + 1);
		}
		return id;
	}

	public void makeIdAvailable(int id) {
		availableIdRanges.add(0, id);
	}

}
