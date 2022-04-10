package nomadrealms.context.server;

import java.util.ArrayList;
import java.util.List;

import context.data.GameData;
import networking.NetworkCluster;
import nomadrealms.model.NomadMini;

public class ServerData extends GameData {

	private List<NetworkCluster> clusters = new ArrayList<>();
	private List<NomadMini> minis = new ArrayList<>();

	public NomadMini selectedMini;

	public List<NomadMini> minis() {
		return minis;
	}

}
