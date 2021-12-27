package nomadrealms.context.server;

import java.util.ArrayList;
import java.util.List;

import context.data.GameData;
import nomadrealms.context.server.visuals.model.NomadMini;
import nomadrealms.model.WorldInfo;

public class ServerData extends GameData {

	private List<WorldInfo> worldInfos = new ArrayList<>();
	private List<NomadMini> minis = new ArrayList<>();

	public NomadMini selectedMini;

	public List<NomadMini> minis() {
		return minis;
	}

	public List<WorldInfo> worldInfos() {
		return worldInfos;
	}

}
