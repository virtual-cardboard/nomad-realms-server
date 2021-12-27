package p2pinfiniteworld.event;

import static p2pinfiniteworld.protocols.P2PIWServerProtocol.JOIN_WORLD_RESPONSE;

import common.source.NetworkSource;
import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import p2pinfiniteworld.protocols.P2PIWServerProtocol;

public class P2PIWJoinWorldResponseEvent extends P2PIWServerGameEvent {

	private String username;

	public P2PIWJoinWorldResponseEvent(NetworkSource source, PacketReader reader) {
		super(source);
		this.username = reader.readString();
		reader.close();
	}

	@Override
	protected PacketModel toPacketModel(PacketBuilder builder) {
		return builder
				.consume(username)
				.build();
	}

	@Override
	protected P2PIWServerProtocol protocol() {
		return JOIN_WORLD_RESPONSE;
	}

}
