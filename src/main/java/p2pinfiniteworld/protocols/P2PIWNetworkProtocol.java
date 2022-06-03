package p2pinfiniteworld.protocols;

import static derealizer.SerializationClassGenerator.generate;
import static derealizer.datatype.SerializationDataType.BYTE;
import static derealizer.datatype.SerializationDataType.INT;
import static derealizer.datatype.SerializationDataType.LONG;
import static derealizer.datatype.SerializationDataType.SHORT;
import static derealizer.datatype.SerializationDataType.STRING_UTF8;
import static derealizer.datatype.SerializationDataType.pojo;
import static derealizer.datatype.SerializationDataType.repeated;
import static derealizer.format.SerializationFormat.types;
import static engine.common.networking.packet.NetworkingSerializationFormats.PACKET_ADDRESS;

import derealizer.format.FieldNames;
import derealizer.format.SerializationFormat;
import derealizer.format.SerializationFormatEnum;
import p2pinfiniteworld.event.P2PIWNetworkEvent;

public enum P2PIWNetworkProtocol implements SerializationFormatEnum<P2PIWNetworkEvent> {

	@FieldNames({ "regionX", "regionY" })
	ENTER_REGION_NOTIFICATION(types(INT, INT)),
	@FieldNames({ "timestamp", "chunkX", "chunkY" })
	CHUNK_DATA_REQUEST(types(LONG, INT, INT)),
	@FieldNames({ "age", "value" })
	AGE_VALUE_PAIR(types(INT, SHORT)),
	@FieldNames({ "regionX", "regionY", "ageValuePairs" })
	REGION_DATA(types(INT, INT, repeated(pojo(AGE_VALUE_PAIR)))),
	@FieldNames({ "user_id", "chunkX", "chunkY" })
	CLICK_CHUNK_NOTIFICATION(types(BYTE, INT, INT)),
	@FieldNames({ "username" })
	JOIN_NETWORK_REQUEST(types(STRING_UTF8)),
	@FieldNames({ "joinerAddress" })
	PEER_JOIN_NETWORK_CONSENSUS(types(pojo(PACKET_ADDRESS))),
	@FieldNames({ "joinerAddress" })
	PEER_JOIN_NETWORK_CONFIRMATION(types(pojo(PACKET_ADDRESS))),
	@FieldNames({})
	CHECK_NOMAD_STATUS_REQUEST(types()),
	@FieldNames({ "username" })
	JOIN_QUEUE_REQUEST(types(STRING_UTF8)),
	@FieldNames({ "tick0Time" })
	JOIN_QUEUE_SUCCESS_RESPONSE(types(LONG)),
	@FieldNames({ "tick0Time" })
	ALREADY_IN_QUEUE_RESPONSE(types(LONG)),
	@FieldNames({ "chunkX", "chunkY", "tick0Time" })
	ALREADY_IN_WORLD_RESPONSE(types(INT, INT, LONG)),
	@FieldNames({ "chunkX", "chunkY", "networkAddresses" })
	READY_TO_JOIN_NETWORK_RESPONSE(types(INT, INT, repeated(pojo(PACKET_ADDRESS)))),
	@FieldNames({ "chunkX", "chunkY", "tick0Time" })
	ENTER_WORLD_NOTIFICATION(types(INT, INT, LONG)),
	;

	static {
		ENTER_REGION_NOTIFICATION.id = 100;
		CHUNK_DATA_REQUEST.id = 102;
		REGION_DATA.id = 103;
		CLICK_CHUNK_NOTIFICATION.id = 104;
		JOIN_NETWORK_REQUEST.id = 50;
		PEER_JOIN_NETWORK_CONSENSUS.id = 51;
		PEER_JOIN_NETWORK_CONFIRMATION.id = 52;
		CHECK_NOMAD_STATUS_REQUEST.id = 199;
		JOIN_QUEUE_REQUEST.id = 200;
		JOIN_QUEUE_SUCCESS_RESPONSE.id = 201;
		ALREADY_IN_QUEUE_RESPONSE.id = 202;
		ALREADY_IN_WORLD_RESPONSE.id = 203;
		READY_TO_JOIN_NETWORK_RESPONSE.id = 204;
		ENTER_WORLD_NOTIFICATION.id = 210;
	}

	private short id = -1;
	private final SerializationFormat format;
	private final Class<? extends P2PIWNetworkEvent> pojoClass;
	private final Class<? extends P2PIWNetworkEvent> superClass;

	P2PIWNetworkProtocol(SerializationFormat format) {
		this.format = format;
		this.pojoClass = null;
		this.superClass = P2PIWNetworkEvent.class;
	}

	public short id() {
		return id;
	}

	@Override
	public SerializationFormat format() {
		return format;
	}

	@Override
	public Class<? extends P2PIWNetworkEvent> pojoClass() {
		return pojoClass;
	}

	@Override
	public Class<? extends P2PIWNetworkEvent> superClass() {
		return superClass;
	}

	public static void main(String[] args) {
		generate(P2PIWNetworkProtocol.class, P2PIWNetworkEvent.class);
	}

}
