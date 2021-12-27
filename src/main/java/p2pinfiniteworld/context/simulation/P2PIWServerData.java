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

	public NomadTiny selectedMini;

	public List<NomadTiny> nomads() {
		return tinies;
	}

	public Queue<NomadTiny> queuedUsers() {
		return queuedUsers;
	}

}
