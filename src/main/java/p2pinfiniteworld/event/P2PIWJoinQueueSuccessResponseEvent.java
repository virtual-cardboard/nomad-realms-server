package p2pinfiniteworld.event;

import static p2pinfiniteworld.protocols.P2PIWServerProtocol.JOIN_QUEUE_SUCCESS_RESPONSE;

import common.source.NetworkSource;
import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import p2pinfiniteworld.protocols.P2PIWServerProtocol;

public class P2PIWJoinQueueSuccessResponseEvent extends P2PIWServerGameEvent {

	public P2PIWJoinQueueSuccessResponseEvent(byte id) {
		super(null);
	}

	public P2PIWJoinQueueSuccessResponseEvent(NetworkSource source, PacketReader reader) {
		super(source);
		reader.close();
	}

	@Override
	protected PacketModel toPacketModel(PacketBuilder builder) {
		return builder
				.build();
	}

	@Override
	protected P2PIWServerProtocol protocol() {
		return JOIN_QUEUE_SUCCESS_RESPONSE;
	}

}
