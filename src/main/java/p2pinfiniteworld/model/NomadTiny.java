package p2pinfiniteworld.model;

import static java.lang.Math.floorDiv;

import common.math.Vector2i;
import context.input.networking.packet.address.PacketAddress;

public class NomadTiny {

	public int x, y;
	private int colour;
	private String username;
	private PacketAddress address;

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

}
