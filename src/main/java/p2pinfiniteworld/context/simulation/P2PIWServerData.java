package p2pinfiniteworld.context.simulation;

import static context.input.networking.packet.address.PacketAddress.match;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import common.math.Vector2i;
import context.data.GameData;
import context.input.networking.packet.address.PacketAddress;
import p2pinfiniteworld.model.NomadTiny;
import p2pinfiniteworld.model.P2PIWRegion;

public class P2PIWServerData extends GameData {

	public static final int REGION_NUM_CHUNKS = 4;

	private Map<Vector2i, P2PIWRegion> regions = new ConcurrentHashMap<>();

	private List<NomadTiny> nomads = new ArrayList<>();
	private Queue<NomadTiny> queuedUsers = new ArrayDeque<>();

	private NomadTiny selectedQueueNomad;
	private NomadTiny selectedWorldNomad;

	public Map<Vector2i, P2PIWRegion> regions() {
		return regions;
	}

	public List<NomadTiny> nomads() {
		return nomads;
	}

	public Queue<NomadTiny> queuedUsers() {
		return queuedUsers;
	}

	public NomadTiny selectedQueueNomad() {
		return selectedQueueNomad;
	}

	public void setSelectedQueueNomad(NomadTiny selectedQueueNomad) {
		this.selectedQueueNomad = selectedQueueNomad;
	}

	public NomadTiny selectedWorldNomad() {
		return selectedWorldNomad;
	}

	public void setSelectedWorldNomad(NomadTiny selectedWorldNomad) {
		this.selectedWorldNomad = selectedWorldNomad;
	}

	public NomadTiny isQueued(PacketAddress address) {
		for (NomadTiny nomad : queuedUsers) {
			if (match(nomad.address(), address)) {
				return nomad;
			}
		}
		return null;
	}

	public NomadTiny isInWorld(PacketAddress address) {
		for (NomadTiny nomad : nomads) {
			if (match(nomad.address(), address)) {
				return nomad;
			}
		}
		return null;
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
