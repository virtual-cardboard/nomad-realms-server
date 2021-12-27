package p2pinfiniteworld.protocols;

import static context.input.networking.packet.PacketPrimitive.INT;
import static context.input.networking.packet.PacketPrimitive.STRING;
import static context.input.networking.packet.PacketPrimitive.BYTE;
import static context.input.networking.packet.PacketPrimitive.LONG;

import context.input.networking.packet.PacketFormat;
import p2pinfiniteworld.event.P2PIWJoinWorldRequestEvent;
import p2pinfiniteworld.event.P2PIWNomadMoveNotificationEvent;
import p2pinfiniteworld.event.P2PIWClickChunkNotificationEvent;
import p2pinfiniteworld.event.P2PIWChunkDataReceiveEvent;
import p2pinfiniteworld.event.P2PIWChunkDataRequestEvent;
import p2pinfiniteworld.event.P2PIWServerGameEvent;

public enum P2PIWServerProtocol {

	/** id, region_x, region_y */
	MOVE_NOTIFICATION(P2PIWNomadMoveNotificationEvent.class, 100, new PacketFormat().with(INT, INT, INT)),
	
	/** chunk_x, chunk_y, timestamp*/
	CHUNK_DATA_REQUEST(P2PIWChunkDataRequestEvent.class, 102, new PacketFormat().with(INT, INT, LONG)),
	
	/** chunk_x, chunk_y, age, value*/
	CHUNK_DATA(P2PIWChunkDataReceiveEvent.class, 103, new PacketFormat().with(INT, INT, INT, INT)),
	
	/** id, chunk_x, chunk_y*/
	CLICK_CHUNK_NOTIFICATION(P2PIWClickChunkNotificationEvent.class, 104, new PacketFormat().with(INT, INT, INT)),

	/** username */
	JOIN_WORLD_REQUEST(P2PIWJoinWorldRequestEvent.class, 111, new PacketFormat().with(STRING)),

	/** username */
	JOIN_WORLD_RESPONSE(P2PIWJoinWorldRequestEvent.class, 112, new PacketFormat().with(STRING));
	

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
