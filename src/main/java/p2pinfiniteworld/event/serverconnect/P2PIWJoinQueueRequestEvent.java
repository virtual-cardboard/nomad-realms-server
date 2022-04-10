package p2pinfiniteworld.event.serverconnect;

import static p2pinfiniteworld.protocols.P2PIWNetworkProtocol.JOIN_QUEUE_REQUEST;

import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import engine.common.source.NetworkSource;
import p2pinfiniteworld.event.P2PIWNetworkEvent;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocol;

public class P2PIWJoinQueueRequestEvent extends P2PIWNetworkEvent {

	private String username;

	public P2PIWJoinQueueRequestEvent(String username) {
		super(null);
		this.username = username;
	}

	public P2PIWJoinQueueRequestEvent(NetworkSource source, PacketReader reader) {
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
		return JOIN_QUEUE_REQUEST;
	}

	public String username() {
		return username;
	}

}
