package p2pinfiniteworld.event.serverconnect;

import static p2pinfiniteworld.protocols.P2PIWNetworkProtocol.CHECK_NOMAD_STATUS_REQUEST;

import context.input.networking.packet.PacketBuilder;
import engine.common.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import engine.common.source.NetworkSource;
import p2pinfiniteworld.event.P2PIWNetworkEvent;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocol;

public class P2PIWCheckNomadStatusRequestEvent extends P2PIWNetworkEvent {

	public P2PIWCheckNomadStatusRequestEvent() {
		super(null);
	}

	public P2PIWCheckNomadStatusRequestEvent(NetworkSource source, PacketReader reader) {
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
		return CHECK_NOMAD_STATUS_REQUEST;
	}

}
