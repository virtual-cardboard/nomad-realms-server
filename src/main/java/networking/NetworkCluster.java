package networking;

import java.util.ArrayList;
import java.util.List;

import model.Player;
import model.PlayerSession;
import nomadrealms.model.WorldInfo;

public class NetworkCluster {

	protected List<PlayerSession> playerSessions = new ArrayList<>();
	protected List<Player> backups = new ArrayList<>();
	protected WorldInfo worldInfo;

	public NetworkCluster(WorldInfo worldInfo) {
		this.worldInfo = worldInfo;
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

}
