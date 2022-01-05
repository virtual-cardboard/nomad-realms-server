package p2pinfiniteworld.protocols;

import static context.input.networking.packet.PacketPrimitive.BYTE;
import static context.input.networking.packet.PacketPrimitive.INT;
import static context.input.networking.packet.PacketPrimitive.IP_V4;
import static context.input.networking.packet.PacketPrimitive.IP_V4_ARRAY;
import static context.input.networking.packet.PacketPrimitive.LONG;
import static context.input.networking.packet.PacketPrimitive.SHORT;
import static context.input.networking.packet.PacketPrimitive.SHORT_ARRAY;
import static context.input.networking.packet.PacketPrimitive.STRING;

import context.input.networking.packet.PacketFormat;
import p2pinfiniteworld.event.P2PIWNetworkEvent;
import p2pinfiniteworld.event.P2PIWRegionDataReceiveEvent;
import p2pinfiniteworld.event.peer2peer.game.P2PIWChunkDataRequestEvent;
import p2pinfiniteworld.event.peer2peer.game.P2PIWClickChunkNotificationEvent;
import p2pinfiniteworld.event.peer2peer.game.P2PIWEnterRegionNotificationEvent;
import p2pinfiniteworld.event.peer2peer.session.P2PIWJoinNetworkRequestEvent;
import p2pinfiniteworld.event.peer2peer.session.P2PIWPeerJoinNetworkConfirmationEvent;
import p2pinfiniteworld.event.peer2peer.session.P2PIWPeerJoinNetworkConsensusEvent;
import p2pinfiniteworld.event.serverconnect.P2PIWAlreadyInQueueResponseEvent;
import p2pinfiniteworld.event.serverconnect.P2PIWAlreadyInWorldResponseEvent;
import p2pinfiniteworld.event.serverconnect.P2PIWCheckNomadStatusRequestEvent;
import p2pinfiniteworld.event.serverconnect.P2PIWEnterWorldNotificationEvent;
import p2pinfiniteworld.event.serverconnect.P2PIWJoinQueueRequestEvent;
import p2pinfiniteworld.event.serverconnect.P2PIWJoinQueueSuccessResponseEvent;
import p2pinfiniteworld.event.serverconnect.P2PIWReadyToJoinNetworkResponse;

public enum P2PIWNetworkProtocol {

	/** region_x, region_y */
	ENTER_REGION_NOTIFICATION(P2PIWEnterRegionNotificationEvent.class, 100, new PacketFormat().with(INT, INT)),

	/** timestamp, chunk_x, chunk_y */
	CHUNK_DATA_REQUEST(P2PIWChunkDataRequestEvent.class, 102, new PacketFormat().with(LONG, INT, INT)),

	/** region_x, region_y, 16 x (age, value) */
	REGION_DATA(P2PIWRegionDataReceiveEvent.class, 103, new PacketFormat().with(INT, INT,
			INT, SHORT, INT, SHORT, INT, SHORT, INT, SHORT,
			INT, SHORT, INT, SHORT, INT, SHORT, INT, SHORT,
			INT, SHORT, INT, SHORT, INT, SHORT, INT, SHORT,
			INT, SHORT, INT, SHORT, INT, SHORT, INT, SHORT)),

	/** user_id, chunk_x, chunk_y */
	CLICK_CHUNK_NOTIFICATION(P2PIWClickChunkNotificationEvent.class, 104, new PacketFormat().with(BYTE, INT, INT)),

	/** username */
	JOIN_NETWORK_REQUEST(P2PIWJoinNetworkRequestEvent.class, 50, new PacketFormat().with(STRING)),

	/** joiner_wan_ip, joiner_wan_port */
	PEER_JOIN_NETWORK_CONSENSUS(P2PIWPeerJoinNetworkConsensusEvent.class, 51, new PacketFormat().with(IP_V4, SHORT)),

	PEER_JOIN_NETWORK_CONFIRMATION(P2PIWPeerJoinNetworkConfirmationEvent.class, 52, new PacketFormat().with(IP_V4, SHORT)),

	CHECK_NOMAD_STATUS_REQUEST(P2PIWCheckNomadStatusRequestEvent.class, 199, new PacketFormat()),

	/** username */
	JOIN_QUEUE_REQUEST(P2PIWJoinQueueRequestEvent.class, 200, new PacketFormat().with(STRING)),

	/** tick_0_time */
	JOIN_QUEUE_SUCCESS_RESPONSE(P2PIWJoinQueueSuccessResponseEvent.class, 201, new PacketFormat().with(LONG)),

	/**  */
	ALREADY_IN_QUEUE_RESPONSE(P2PIWAlreadyInQueueResponseEvent.class, 202, new PacketFormat()),

	/** chunk_x, chunk_y, tick_0_time */
	ALREADY_IN_WORLD_RESPONSE(P2PIWAlreadyInWorldResponseEvent.class, 203, new PacketFormat().with(INT, INT, LONG)),

	/** chunk_x, chunk_y, network_ip_array, network_port_array */
	READY_TO_JOIN_NETWORK_RESPONSE(P2PIWReadyToJoinNetworkResponse.class, 204, new PacketFormat().with(INT, INT, IP_V4_ARRAY, SHORT_ARRAY)),

	/** chunk_x, chunk_y, tick_0_time */
	ENTER_WORLD_NOTIFICATION(P2PIWEnterWorldNotificationEvent.class, 210, new PacketFormat().with(INT, INT, LONG));

	private short id;
	private Class<? extends P2PIWNetworkEvent> clazz;
	private PacketFormat format;

	private P2PIWNetworkProtocol(Class<? extends P2PIWNetworkEvent> clazz, int id, PacketFormat format) {
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

	public Class<? extends P2PIWNetworkEvent> clazz() {
		return clazz;
	}

}
