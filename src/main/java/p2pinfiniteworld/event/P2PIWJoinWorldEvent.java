package p2pinfiniteworld.event;

import static context.input.networking.packet.PacketPrimitive.STRING;
import static p2pinfiniteworld.protocols.P2PIWServerProtocols.JOIN_WORLD_REQUEST;

import common.source.NetworkSource;
import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketFormat;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import p2pinfiniteworld.protocols.P2PIWServerProtocols;

public class P2PIWJoinWorldEvent extends P2PIWServerGameEvent {

	public static final PacketFormat P2PIW_JOIN_WORLD_REQUEST_FORMAT = new PacketFormat().with(STRING);

	private String username;

	public P2PIWJoinWorldEvent(NetworkSource source, PacketReader protocolReader) {
		super(source);
		PacketReader reader = P2PIW_JOIN_WORLD_REQUEST_FORMAT.reader(protocolReader);
		this.username = reader.readString();
		reader.close();
	}

	@Override
	protected PacketModel toPacketModel(PacketBuilder builder) {
		return P2PIW_JOIN_WORLD_REQUEST_FORMAT.builder(builder)
				.consume(username)
				.build();
	}

	@Override
	protected P2PIWServerProtocols protocolID() {
		return JOIN_WORLD_REQUEST;
	}

	public String username() {
		return username;
	}

}
