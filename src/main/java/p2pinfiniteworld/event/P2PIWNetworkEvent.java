package p2pinfiniteworld.event;

import static networking.NetworkUtils.PROTOCOL_ID;

import common.event.GameEvent;
import common.source.NetworkSource;
import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.address.PacketAddress;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocol;

public abstract class P2PIWNetworkEvent extends GameEvent {

	public P2PIWNetworkEvent(NetworkSource source) {
		super(source);
	}

	public P2PIWNetworkEvent(long time, NetworkSource source) {
		super(time, source);
	}

	@Override
	public NetworkSource source() {
		return (NetworkSource) super.source();
	}

	public PacketModel toPacket(PacketAddress address) {
		PacketBuilder idBuilder = PROTOCOL_ID.builder(address).consume(protocol().id());
		PacketBuilder builder = protocol().format().builder(idBuilder);
		return toPacketModel(builder);
	}

	protected abstract PacketModel toPacketModel(PacketBuilder builder);

	protected abstract P2PIWNetworkProtocol protocol();

}