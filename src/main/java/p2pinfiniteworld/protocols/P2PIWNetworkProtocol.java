package p2pinfiniteworld.protocols;

import static context.input.networking.packet.PacketPrimitive.*;

import context.input.networking.packet.SerializationFormat;
import p2pinfiniteworld.event.P2PIWNetworkEvent;
import p2pinfiniteworld.event.P2PIWRegionDataReceiveEvent;
import p2pinfiniteworld.event.peer2peer.game.P2PIWChunkDataRequestEvent;
import p2pinfiniteworld.event.peer2peer.game.P2PIWClickChunkNotificationEvent;
import p2pinfiniteworld.event.peer2peer.game.P2PIWEnterRegionNotificationEvent;
import p2pinfiniteworld.event.peer2peer.session.P2PIWJoinNetworkRequestEvent;
import p2pinfiniteworld.event.peer2peer.session.P2PIWPeerJoinNetworkConfirmationEvent;
import p2pinfiniteworld.event.peer2peer.session.P2PIWPeerJoinNetworkConsensusEvent;
import p2pinfiniteworld.event.serverconnect.*;

public enum P2PIWNetworkProtocol {

	/**
	 * region_x, region_y
	 */
	ENTER_REGION_NOTIFICATION(P2PIWEnterRegionNotificationEvent.class, 100, new SerializationFormat().with(INT, INT)),

	/**
	 * timestamp, chunk_x, chunk_y
	 */
	CHUNK_DATA_REQUEST(P2PIWChunkDataRequestEvent.class, 102, new SerializationFormat().with(LONG, INT, INT)),

	/**
	 * region_x, region_y, 16 x (age, value)
	 */
	REGION_DATA(P2PIWRegionDataReceiveEvent.class, 103, new SerializationFormat().with(INT, INT,
			INT, SHORT, INT, SHORT, INT, SHORT, INT, SHORT,
			INT, SHORT, INT, SHORT, INT, SHORT, INT, SHORT,
			INT, SHORT, INT, SHORT, INT, SHORT, INT, SHORT,
			INT, SHORT, INT, SHORT, INT, SHORT, INT, SHORT)),

	/**
	 * user_id, chunk_x, chunk_y
	 */
	CLICK_CHUNK_NOTIFICATION(P2PIWClickChunkNotificationEvent.class, 104, new SerializationFormat().with(BYTE, INT, INT)),

	/**
	 * username
	 */
	JOIN_NETWORK_REQUEST(P2PIWJoinNetworkRequestEvent.class, 50, new SerializationFormat().with(STRING)),

	/**
	 * joiner_wan_ip, joiner_wan_port
	 */
	PEER_JOIN_NETWORK_CONSENSUS(P2PIWPeerJoinNetworkConsensusEvent.class, 51, new SerializationFormat().with(IP_V4, SHORT)),

	PEER_JOIN_NETWORK_CONFIRMATION(P2PIWPeerJoinNetworkConfirmationEvent.class, 52, new SerializationFormat().with(IP_V4, SHORT)),

	CHECK_NOMAD_STATUS_REQUEST(P2PIWCheckNomadStatusRequestEvent.class, 199, new SerializationFormat()),

	/**
	 * username
	 */
	JOIN_QUEUE_REQUEST(P2PIWJoinQueueRequestEvent.class, 200, new SerializationFormat().with(STRING)),

	/**
	 * tick_0_time
	 */
	JOIN_QUEUE_SUCCESS_RESPONSE(P2PIWJoinQueueSuccessResponseEvent.class, 201, new SerializationFormat().with(LONG)),

	/**
	 * tick_0_time
	 */
	ALREADY_IN_QUEUE_RESPONSE(P2PIWAlreadyInQueueResponseEvent.class, 202, new SerializationFormat().with(LONG)),

	/**
	 * chunk_x, chunk_y, tick_0_time
	 */
	ALREADY_IN_WORLD_RESPONSE(P2PIWAlreadyInWorldResponseEvent.class, 203, new SerializationFormat().with(INT, INT, LONG)),

	/**
	 * chunk_x, chunk_y, network_ip_array, network_port_array
	 */
	READY_TO_JOIN_NETWORK_RESPONSE(P2PIWReadyToJoinNetworkResponse.class, 204, new SerializationFormat().with(INT, INT, IP_V4_ARRAY, SHORT_ARRAY)),

	/**
	 * chunk_x, chunk_y, tick_0_time
	 */
	ENTER_WORLD_NOTIFICATION(P2PIWEnterWorldNotificationEvent.class, 210, new SerializationFormat().with(INT, INT, LONG));

	private short id;
	private Class<? extends P2PIWNetworkEvent> clazz;
	private SerializationFormat format;

	private P2PIWNetworkProtocol(Class<? extends P2PIWNetworkEvent> clazz, int id, SerializationFormat format) {
		this.clazz = clazz;
		this.id = (short) id;
		this.format = format;
	}

	public SerializationFormat format() {
		return format;
	}

	public short id() {
		return id;
	}

	public Class<? extends P2PIWNetworkEvent> clazz() {
		return clazz;
	}

}
