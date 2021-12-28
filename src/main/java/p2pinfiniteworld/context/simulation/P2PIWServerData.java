package p2pinfiniteworld.context.simulation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import context.data.GameData;
import p2pinfiniteworld.model.NomadTiny;

public class P2PIWServerData extends GameData {

	private List<NomadTiny> tinies = new ArrayList<>();
	private Queue<NomadTiny> queuedUsers = new ArrayDeque<>();

	private NomadTiny selectedTiny;

	public List<NomadTiny> nomads() {
		return tinies;
	}

	public Queue<NomadTiny> queuedUsers() {
		return queuedUsers;
	}

	public NomadTiny selectedNomad() {
		return selectedTiny;
	}

	public void setSelectedNomad(NomadTiny selectedTiny) {
		this.selectedTiny = selectedTiny;
	}

}
