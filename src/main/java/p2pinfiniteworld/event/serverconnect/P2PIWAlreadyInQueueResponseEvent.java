package p2pinfiniteworld.event.serverconnect;

import static p2pinfiniteworld.protocols.P2PIWNetworkProtocol.ALREADY_IN_QUEUE_RESPONSE;

import common.source.NetworkSource;
import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import p2pinfiniteworld.event.P2PIWNetworkEvent;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocol;

public class P2PIWAlreadyInQueueResponseEvent extends P2PIWNetworkEvent {

	public P2PIWAlreadyInQueueResponseEvent() {
		super(null);
	}

	public P2PIWAlreadyInQueueResponseEvent(NetworkSource source, PacketReader reader) {
		super(source);
		reader.close();
	}

	@Override
	protected PacketModel toPacketModel(PacketBuilder builder) {
		return builder
				.build();
	}

	@Override
	protected P2PIWNetworkProtocol protocol() {
		return ALREADY_IN_QUEUE_RESPONSE;
	}

}
