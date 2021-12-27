package p2pinfiniteworld.event;

import static context.input.networking.packet.PacketPrimitive.STRING;
import static p2pinfiniteworld.protocols.P2PIWServerProtocol.JOIN_WORLD_REQUEST;

import common.source.NetworkSource;
import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketFormat;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import p2pinfiniteworld.protocols.P2PIWServerProtocol;

public class P2PIWJoinWorldResponseEvent extends P2PIWServerGameEvent {

	public static final PacketFormat P2PIW_JOIN_WORLD_RESPONSE_FORMAT = new PacketFormat().with(STRING);

	private String username;

	public P2PIWJoinWorldResponseEvent(NetworkSource source, PacketReader protocolReader) {
		super(source);
		PacketReader reader = P2PIW_JOIN_WORLD_RESPONSE_FORMAT.reader(protocolReader);
		this.username = reader.readString();
		reader.close();
	}

	@Override
	protected PacketModel toPacketModel(PacketBuilder builder) {
		return P2PIW_JOIN_WORLD_RESPONSE_FORMAT.builder(builder)
				.consume(username)
				.build();
	}

	@Override
	protected P2PIWServerProtocol protocol() {
		return JOIN_WORLD_REQUEST;
	}

	public String username() {
		return username;
	}

}
