package p2pinfiniteworld.model;

import static java.lang.Math.floorDiv;

import java.util.HashSet;
import java.util.Set;

import engine.common.networking.packet.address.PacketAddress;
import engine.common.math.Vector2i;

public class NomadTiny {

	public int x, y;
	private int colour;
	private String username;
	private PacketAddress address;
	private boolean pendingJoin = true;

	private Set<Vector2i> visitedChunks = new HashSet<>();
	private Set<Vector2i> visitedRegions = new HashSet<>();

	public NomadTiny(int x, int y, PacketAddress address, String username, int colour) {
		this.x = x;
		this.y = y;
		this.address = address;
		this.username = username;
		this.colour = colour;
	}

	public Vector2i region() {
		return new Vector2i(floorDiv(x, 256), floorDiv(x, 256));
	}

	public Vector2i chunk() {
		return new Vector2i(floorDiv(x, 16), floorDiv(x, 16));
	}

	public PacketAddress address() {
		return address;
	}

	public int colour() {
		return colour;
	}

	public String username() {
		return username;
	}

	public Set<Vector2i> visitedChunks() {
		return visitedChunks;
	}

	public Set<Vector2i> visitedRegions() {
		return visitedRegions;
	}

	public boolean pendingJoin() {
		return pendingJoin;
	}

	public void setPendingJoin(boolean pendingJoin) {
		this.pendingJoin = pendingJoin;
	}

}
