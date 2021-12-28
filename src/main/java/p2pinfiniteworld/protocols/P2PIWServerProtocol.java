package p2pinfiniteworld.protocols;

import static context.input.networking.packet.PacketPrimitive.INT;
import static context.input.networking.packet.PacketPrimitive.LONG;
import static context.input.networking.packet.PacketPrimitive.SHORT;
import static context.input.networking.packet.PacketPrimitive.STRING;

import context.input.networking.packet.PacketFormat;
import p2pinfiniteworld.event.P2PIWRegionDataReceiveEvent;
import p2pinfiniteworld.event.P2PIWChunkDataRequestEvent;
import p2pinfiniteworld.event.P2PIWClickChunkNotificationEvent;
import p2pinfiniteworld.event.P2PIWJoinWorldRequestEvent;
import p2pinfiniteworld.event.P2PIWEnterRegionNotificationEvent;
import p2pinfiniteworld.event.P2PIWServerGameEvent;

public enum P2PIWServerProtocol {

	/** id, region_x, region_y */
	ENTER_REGION_NOTIFICATION(P2PIWEnterRegionNotificationEvent.class, 100, new PacketFormat().with(INT, INT, INT)),

	/** timestamp, chunk_x, chunk_y */
	CHUNK_DATA_REQUEST(P2PIWChunkDataRequestEvent.class, 102, new PacketFormat().with(LONG, INT, INT)),

	/** region_x, region_y, 16 x (age, value) */
	REGION_DATA(P2PIWRegionDataReceiveEvent.class, 103, new PacketFormat().with(INT, INT,
			INT, SHORT, INT, SHORT, INT, SHORT, INT, SHORT,
			INT, SHORT, INT, SHORT, INT, SHORT, INT, SHORT,
			INT, SHORT, INT, SHORT, INT, SHORT, INT, SHORT,
			INT, SHORT, INT, SHORT, INT, SHORT, INT, SHORT)),

	/** id, chunk_x, chunk_y */
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
