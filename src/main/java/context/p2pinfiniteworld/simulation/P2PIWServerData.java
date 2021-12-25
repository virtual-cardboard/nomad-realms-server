package context.p2pinfiniteworld.simulation;

import java.util.ArrayList;
import java.util.List;

import context.data.GameData;
import context.nomadrealms.server.visuals.model.NomadMini;
import model.WorldInfo;

public class P2PIWServerData extends GameData {

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
