package p2pinfiniteworld.protocols;

import static context.input.networking.packet.PacketPrimitive.STRING;

import context.input.networking.packet.PacketFormat;
import p2pinfiniteworld.event.P2PIWJoinWorldRequestEvent;
import p2pinfiniteworld.event.P2PIWServerGameEvent;

public enum P2PIWServerProtocol {

	/** username */
	JOIN_WORLD_REQUEST(P2PIWJoinWorldRequestEvent.class, 111, new PacketFormat().with(STRING));

	private short id;
	private Class<? extends P2PIWServerGameEvent> clazz;
	private PacketFormat format;

	private P2PIWServerProtocol(Class<? extends P2PIWServerGameEvent> clazz, int id, PacketFormat format) {
		this.clazz = clazz;
		this.id = (short) id;
		this.format = format;
	}

	public PacketFormat format() {
		return format;
	}

	public short id() {
		return id;
	}

	public Class<? extends P2PIWServerGameEvent> clazz() {
		return clazz;
	}

}
