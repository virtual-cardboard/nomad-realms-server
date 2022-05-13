package p2pinfiniteworld.context.simulation;

import static engine.common.networking.packet.address.PacketAddress.match;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import context.data.GameData;
import engine.common.networking.packet.address.PacketAddress;
import engine.common.math.Vector2i;
import p2pinfiniteworld.model.LogMessages;
import p2pinfiniteworld.model.NomadTiny;
import p2pinfiniteworld.model.P2PIWRegion;

public class P2PIWServerData extends GameData {

	public static final int REGION_NUM_CHUNKS = 4;

	private Map<Vector2i, P2PIWRegion> regions = new ConcurrentHashMap<>();

	private Collection<NomadTiny> nomads = new HashSet<>();
	private Queue<NomadTiny> queuedUsers = new ArrayDeque<>();

	private NomadTiny selectedQueueNomad;
	private NomadTiny selectedWorldNomad;

	private LogMessages logMessages = new LogMessages();

	public Map<Vector2i, P2PIWRegion> regions() {
		return regions;
	}

	public Collection<NomadTiny> nomads() {
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

	public List<PacketAddress> addressList() {
		List<PacketAddress> list = new ArrayList<>();
		for (NomadTiny nomad : nomads) {
			list.add(nomad.address());
		}
		return list;
	}

	public LogMessages logMessages() {
		return logMessages;
	}

	public void setLogMessages(LogMessages logMessages) {
		this.logMessages = logMessages;
	}

	public void addMessage(String source, String string) {
		logMessages.addMessage(source, string);
	}

	public void addServerMessage(String string) {
		logMessages.addMessage("Server", string);
	}

}
