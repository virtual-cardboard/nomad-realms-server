package p2pinfiniteworld.event.peer2peer.session;

import static p2pinfiniteworld.protocols.P2PIWNetworkProtocol.PEER_JOIN_NETWORK_CONSENSUS;

import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import context.input.networking.packet.address.PacketAddress;
import engine.common.source.NetworkSource;
import networking.ServerNetworkUtils;
import p2pinfiniteworld.event.P2PIWNetworkEvent;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocol;

public class P2PIWPeerJoinNetworkConfirmationEvent extends P2PIWNetworkEvent {

	private PacketAddress joinerAddress;

	public P2PIWPeerJoinNetworkConfirmationEvent(PacketAddress joinerAddress) {
		super(null);
		this.joinerAddress = joinerAddress;
	}

	public P2PIWPeerJoinNetworkConfirmationEvent(NetworkSource source, PacketReader reader) {
		super(source);
		byte[] ip = reader.readIPv4();
		short port = reader.readShort();
		this.joinerAddress = ServerNetworkUtils.toAddress(ip, port);
		reader.close();
	}

	@Override
	protected PacketModel toPacketModel(PacketBuilder builder) {
		return builder
				.consume(joinerAddress.ip())
				.consume(joinerAddress.port())
				.build();
	}

	@Override
	protected P2PIWNetworkProtocol protocol() {
		return PEER_JOIN_NETWORK_CONSENSUS;
	}

	public PacketAddress joinerAddress() {
		return joinerAddress;
	}

}
