package nomadrealms.event.world;

import static nomadrealms.protocols.NomadRealmsServerProtocol.CREATE_WORLD_REQUEST;

import common.source.NetworkSource;
import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import nomadrealms.event.NomadRealmsServerGameEvent;
import nomadrealms.protocols.NomadRealmsServerProtocol;

public class CreateWorldRequestEvent extends NomadRealmsServerGameEvent {

	private String worldName;

	public CreateWorldRequestEvent(NetworkSource source, long timestamp, String worldName) {
		super(timestamp, source);
		this.worldName = worldName;
	}

	public CreateWorldRequestEvent(NetworkSource source, PacketReader reader) {
		super(source);
		this.worldName = reader.readString();
		reader.close();
	}

	public String worldName() {
		return worldName;
	}

	@Override
	protected PacketModel toPacketModel(PacketBuilder builder) {
		return builder
				.consume(time())
				.consume(worldName)
				.build();
	}

	@Override
	protected NomadRealmsServerProtocol protocol() {
		return CREATE_WORLD_REQUEST;
	}

}
