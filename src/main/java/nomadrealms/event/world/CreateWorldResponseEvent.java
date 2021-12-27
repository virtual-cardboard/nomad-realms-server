package nomadrealms.event.world;

import static networking.NetworkUtils.LOCAL_HOST;
import static nomadrealms.protocols.NomadRealmsServerProtocol.CREATE_WORLD_RESPONSE;

import common.source.NetworkSource;
import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import nomadrealms.event.NomadRealmsServerGameEvent;
import nomadrealms.protocols.NomadRealmsServerProtocol;

public class CreateWorldResponseEvent extends NomadRealmsServerGameEvent {

	private long seed;

	public CreateWorldResponseEvent(NetworkSource source, long timestamp, long seed) {
		super(timestamp, source);
		this.seed = seed;
	}

	public CreateWorldResponseEvent(long seed) {
		super(LOCAL_HOST);
		this.seed = seed;
	}

	public CreateWorldResponseEvent(NetworkSource source, PacketReader reader) {
		super(source);
		setTime(reader.readLong());
		this.seed = reader.readLong();
		reader.close();
	}

	public long seed() {
		return seed;
	}

	@Override
	protected PacketModel toPacketModel(PacketBuilder builder) {
		return builder
				.consume(time())
				.consume(seed)
				.build();
	}

	@Override
	protected NomadRealmsServerProtocol protocol() {
		return CREATE_WORLD_RESPONSE;
	}

}
