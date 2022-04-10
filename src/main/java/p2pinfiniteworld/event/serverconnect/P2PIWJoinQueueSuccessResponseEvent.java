package p2pinfiniteworld.event.serverconnect;

import static p2pinfiniteworld.protocols.P2PIWNetworkProtocol.JOIN_QUEUE_SUCCESS_RESPONSE;

import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import engine.common.source.NetworkSource;
import p2pinfiniteworld.event.P2PIWNetworkEvent;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocol;

public class P2PIWJoinQueueSuccessResponseEvent extends P2PIWNetworkEvent {

	private long tick0Time;

	public P2PIWJoinQueueSuccessResponseEvent() {
		super(null);
	}

	public P2PIWJoinQueueSuccessResponseEvent(NetworkSource source, PacketReader reader) {
		super(source);
		tick0Time = reader.readLong();
		reader.close();
	}

	@Override
	protected PacketModel toPacketModel(PacketBuilder builder) {
		return builder
				.consume(tick0Time)
				.build();
	}

	@Override
	protected P2PIWNetworkProtocol protocol() {
		return JOIN_QUEUE_SUCCESS_RESPONSE;
	}

	public long tick0Time() {
		return 0;
	}

}
