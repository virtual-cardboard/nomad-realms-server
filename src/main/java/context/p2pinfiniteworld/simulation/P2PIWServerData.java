package context.p2pinfiniteworld.simulation;

import java.util.ArrayList;
import java.util.List;

import context.data.GameData;
import context.p2pinfiniteworld.simulation.visuals.NomadTiny;

public class P2PIWServerData extends GameData {

	private List<NomadTiny> tinies = new ArrayList<>();

	public NomadTiny selectedMini;

	public List<NomadTiny> nomads() {
		return tinies;
	}

}
