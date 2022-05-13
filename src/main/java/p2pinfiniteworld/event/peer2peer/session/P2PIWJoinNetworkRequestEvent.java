package p2pinfiniteworld.event.peer2peer.session;

import static p2pinfiniteworld.protocols.P2PIWNetworkProtocol.JOIN_NETWORK_REQUEST;

import context.input.networking.packet.PacketBuilder;
import engine.common.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import engine.common.source.NetworkSource;
import p2pinfiniteworld.event.P2PIWNetworkEvent;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocol;

public class P2PIWJoinNetworkRequestEvent extends P2PIWNetworkEvent {

	private String username;

	public P2PIWJoinNetworkRequestEvent(String username) {
		super(null);
		this.username = username;
	}

	public P2PIWJoinNetworkRequestEvent(NetworkSource source, PacketReader reader) {
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
	protected P2PIWNetworkProtocol protocol() {
		return JOIN_NETWORK_REQUEST;
	}

	public String username() {
		return username;
	}

}
