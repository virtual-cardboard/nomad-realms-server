package nomadrealms.event;

import static networking.NetworkUtils.PROTOCOL_ID;

import common.event.GameEvent;
import common.source.NetworkSource;
import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.address.PacketAddress;
import nomadrealms.protocols.NomadRealmsServerProtocol;

public abstract class NomadRealmsServerGameEvent extends GameEvent {

	private NetworkSource source;

	public NomadRealmsServerGameEvent(NetworkSource source) {
		this.source = source;
	}

	public NomadRealmsServerGameEvent(long time, NetworkSource source) {
		super(time);
		this.source = source;
	}

	public NetworkSource source() {
		return source;
	}

	public PacketModel toPacket(PacketAddress address) {
		PacketBuilder idBuilder = PROTOCOL_ID.builder(address).consume(protocol().id());
		PacketBuilder builder = protocol().format().builder(idBuilder);
		return toPacketModel(builder);
	}

	protected abstract PacketModel toPacketModel(PacketBuilder builder);

	protected abstract NomadRealmsServerProtocol protocol();

}
