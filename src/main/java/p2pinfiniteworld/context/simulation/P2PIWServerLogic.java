package p2pinfiniteworld.context.simulation;

import static context.visuals.colour.Colour.rgb;
import static java.lang.Math.floorDiv;
import static java.lang.Math.floorMod;
import static java.lang.Math.random;
import static p2pinfiniteworld.context.simulation.P2PIWServerData.REGION_NUM_CHUNKS;

import java.util.HashSet;
import java.util.Set;

import common.math.Vector2i;
import context.input.networking.packet.address.PacketAddress;
import context.logic.GameLogic;
import p2pinfiniteworld.event.P2PIWJoinQueueRequestEvent;
import p2pinfiniteworld.event.P2PIWJoinQueueSuccessResponseEvent;
import p2pinfiniteworld.model.NomadTiny;
import p2pinfiniteworld.model.P2PIWChunk;
import p2pinfiniteworld.model.P2PIWRegion;

public class P2PIWServerLogic extends GameLogic {

	public static final int RENDER_DISTANCE = 2;

	private P2PIWServerData data;

	/**
	 * time of first tick
	 */
	private long tick0Time;
	private int tick = -1;
	private long frame = 0;

	@Override
	protected void init() {
		data = (P2PIWServerData) context().data();
		addHandler(P2PIWJoinQueueRequestEvent.class, this::handleJoinQueue);
	}

	private void handleJoinQueue(P2PIWJoinQueueRequestEvent event) {
		String username = event.username();
		PacketAddress address = event.source().address();

		if (data.isQueued(address) || data.isInWorld(address)) {
			return;
		}

		System.out.println(username + " requested to join queue");
		data.queuedUsers().add(new NomadTiny(10, 10, address, username, randomColour()));

		context().sendPacket(new P2PIWJoinQueueSuccessResponseEvent().toPacket(address));
	}

	private int randomColour() {
		return rgb((int) (255 * random()), (int) (255 * random()), (int) (255 * random()));
	}

	@Override
	public void update() {
		if (frame % 10 == 0) {
			tick++;
			updateWorld();
			if (tick == 0) {
				tick0Time = System.currentTimeMillis();
			}
		}
		frame++;
	}

	private void updateWorld() {
		Set<P2PIWChunk> chunksToUpdate = new HashSet<>();
		for (NomadTiny nomad : data.nomads()) {
			for (int i = nomad.y - RENDER_DISTANCE; i <= nomad.y + RENDER_DISTANCE; i++) {
				for (int j = nomad.x - RENDER_DISTANCE; j <= nomad.x + RENDER_DISTANCE; j++) {
					Vector2i regionCoord = new Vector2i(floorDiv(j, REGION_NUM_CHUNKS), floorDiv(j, REGION_NUM_CHUNKS));
					P2PIWRegion region = data.getOrCreateRegion(regionCoord);
					chunksToUpdate.add(region.chunks[floorMod(i, REGION_NUM_CHUNKS)][floorMod(j, REGION_NUM_CHUNKS)]);
				}
			}
		}
		for (P2PIWChunk chunk : chunksToUpdate) {
			chunk.age++;
		}
	}

	public long tick0Time() {
		return tick0Time;
	}

}
