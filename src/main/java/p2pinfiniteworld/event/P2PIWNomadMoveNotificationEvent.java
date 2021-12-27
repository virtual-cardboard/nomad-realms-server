package p2pinfiniteworld.event;

import static p2pinfiniteworld.protocols.P2PIWServerProtocol.MOVE_NOTIFICATION;

import common.source.NetworkSource;
import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import p2pinfiniteworld.protocols.P2PIWServerProtocol;

public class P2PIWNomadMoveNotificationEvent extends P2PIWServerGameEvent {

	private int id;
	private int chunkX, chunkY;

	public P2PIWNomadMoveNotificationEvent(NetworkSource source, PacketReader reader) {
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
	protected P2PIWServerProtocol protocol() {
		return MOVE_NOTIFICATION;
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
