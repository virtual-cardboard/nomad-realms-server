package p2pinfiniteworld.event.peer2peer;

import static p2pinfiniteworld.protocols.P2PIWNetworkProtocol.CLICK_CHUNK_NOTIFICATION;

import common.source.NetworkSource;
import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import p2pinfiniteworld.event.P2PIWNetworkEvent;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocol;

public class P2PIWMoveNotificationEvent extends P2PIWNetworkEvent {

	private int id;
	private int chunkX, chunkY;

	public P2PIWMoveNotificationEvent(NetworkSource source, PacketReader reader) {
		super(source);
		this.id = reader.readInt();
		this.chunkX = reader.readInt();
		this.chunkY = reader.readInt();
		reader.close();
	}

	@Override
	protected PacketModel toPacketModel(PacketBuilder builder) {
		return builder
				.consume(id)
				.consume(chunkX)
				.consume(chunkY)
				.build();
	}

	@Override
	protected P2PIWNetworkProtocol protocol() {
		return CLICK_CHUNK_NOTIFICATION;
	}

	public int id() {
		return id;
	}

	public int chunkX() {
		return chunkX;
	}

	public int chunkY() {
		return chunkY;
	}

}
