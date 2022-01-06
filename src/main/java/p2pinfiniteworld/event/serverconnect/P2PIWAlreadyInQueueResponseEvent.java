package p2pinfiniteworld.event.serverconnect;

import static p2pinfiniteworld.protocols.P2PIWNetworkProtocol.ALREADY_IN_QUEUE_RESPONSE;

import common.source.NetworkSource;
import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import p2pinfiniteworld.event.P2PIWNetworkEvent;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocol;

public class P2PIWAlreadyInQueueResponseEvent extends P2PIWNetworkEvent {

	private long tick0Time;

	public P2PIWAlreadyInQueueResponseEvent(long tick0Time) {
		super(null);
		this.tick0Time = tick0Time;
	}

	public P2PIWAlreadyInQueueResponseEvent(NetworkSource source, PacketReader reader) {
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
		return ALREADY_IN_QUEUE_RESPONSE;
	}

	public long tick0Time() {
		return 0;
	}

}
