package p2pinfiniteworld.context.simulation;

import static context.input.networking.packet.address.PacketAddress.match;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import common.math.Vector2i;
import context.data.GameData;
import context.input.networking.packet.address.PacketAddress;
import p2pinfiniteworld.model.NomadTiny;
import p2pinfiniteworld.model.P2PIWRegion;

public class P2PIWServerData extends GameData {

	public static final int REGION_NUM_CHUNKS = 4;

	private Map<Vector2i, P2PIWRegion> regions = new HashMap<>();

	private List<NomadTiny> nomads = new ArrayList<>();
	private Queue<NomadTiny> queuedUsers = new ArrayDeque<>();

	private NomadTiny selectedTiny;

	public Map<Vector2i, P2PIWRegion> regions() {
		return regions;
	}

	public List<NomadTiny> nomads() {
		return nomads;
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

	public boolean isQueued(PacketAddress address) {
		for (NomadTiny nomad : queuedUsers) {
			if (match(nomad.address(), address)) {
				return true;
			}
		}
		return false;
	}

	public boolean isInWorld(PacketAddress address) {
		for (NomadTiny nomad : nomads) {
			if (match(nomad.address(), address)) {
				return true;
			}
		}
		return false;
	}

	public P2PIWRegion getOrCreateRegion(Vector2i regionCoord) {
		P2PIWRegion region = regions.get(regionCoord);
		if (region == null) {
			region = new P2PIWRegion();
			regions.put(regionCoord, region);
		}
		return region;
	}

}
