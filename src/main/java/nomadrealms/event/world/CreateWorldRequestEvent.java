package nomadrealms.event.world;

import static context.input.networking.packet.PacketPrimitive.LONG;
import static context.input.networking.packet.PacketPrimitive.STRING;
import static nomadrealms.protocols.NomadRealmsServerProtocols.CREATE_WORLD_REQUEST;

import common.source.NetworkSource;
import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketFormat;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import nomadrealms.event.NomadRealmsServerGameEvent;
import nomadrealms.protocols.NomadRealmsServerProtocols;

public class CreateWorldRequestEvent extends NomadRealmsServerGameEvent {

	/**
	 * protocol_id(110): timestamp, world_name
	 */
	public static final PacketFormat CREATE_WORLD_REQUEST_FORMAT = new PacketFormat().with(LONG, STRING);
	private String worldName;

	public CreateWorldRequestEvent(NetworkSource source, long timestamp, String worldName) {
		super(timestamp, source);
		this.worldName = worldName;
	}

	public CreateWorldRequestEvent(NetworkSource source, PacketReader protocolReader) {
		super(source);
		PacketReader reader = CREATE_WORLD_REQUEST_FORMAT.reader(protocolReader);
		this.worldName = reader.readString();
		reader.close();
	}

	public String worldName() {
		return worldName;
	}

	@Override
	protected PacketModel toPacketModel(PacketBuilder builder) {
		return CREATE_WORLD_REQUEST_FORMAT.builder(builder)
				.consume(time())
				.consume(worldName)
				.build();
	}

	@Override
	protected NomadRealmsServerProtocols protocolID() {
		return CREATE_WORLD_REQUEST;
	}

}
