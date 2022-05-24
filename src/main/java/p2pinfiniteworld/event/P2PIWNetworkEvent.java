package p2pinfiniteworld.event;

import derealizer.format.SerializationPojo;
import engine.common.event.GameEvent;
import engine.common.networking.packet.PacketModel;
import engine.common.networking.packet.address.PacketAddress;
import engine.common.source.NetworkSource;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocol;

public abstract class P2PIWNetworkEvent extends GameEvent implements SerializationPojo {

	private NetworkSource source;

	public P2PIWNetworkEvent() {
	}

	public P2PIWNetworkEvent(long time) {
		super(time);
	}

	public NetworkSource source() {
		return source;
	}

	public PacketModel toPacketModel(PacketAddress address) {
		byte[] serialized = serialize();
		short protocolID = protocol().id();
		byte[] bytes = new byte[serialized.length + 2];
		System.arraycopy(serialized, 0, bytes, 2, serialized.length);
		bytes[0] = (byte) ((protocolID >> 8) & 0xFF);
		bytes[1] = (byte) (protocolID & 0xFF);
		return new PacketModel(bytes, address);
	}

	protected P2PIWNetworkProtocol protocol() {
		String className = getClass().getSimpleName();
		return P2PIWNetworkProtocol.valueOf(className);
	}

}
