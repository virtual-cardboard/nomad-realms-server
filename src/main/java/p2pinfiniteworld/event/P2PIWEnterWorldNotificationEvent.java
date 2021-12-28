package p2pinfiniteworld.event;

import static p2pinfiniteworld.protocols.P2PIWServerProtocol.ENTER_WORLD_NOTIFICATION;

import common.source.NetworkSource;
import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import p2pinfiniteworld.protocols.P2PIWServerProtocol;

public class P2PIWEnterWorldNotificationEvent extends P2PIWServerGameEvent {

	private int chunkX, chunkY;
	private long tick0Time;

	public P2PIWEnterWorldNotificationEvent(NetworkSource source, PacketReader reader) {
		super(source);
		this.chunkX = reader.readInt();
		this.chunkY = reader.readInt();
		this.tick0Time = reader.readLong();
		reader.close();
	}

	@Override
	protected PacketModel toPacketModel(PacketBuilder builder) {
		return builder
				.consume(chunkX)
				.consume(chunkY)
				.consume(tick0Time())
				.build();
	}

	@Override
	protected P2PIWServerProtocol protocol() {
		return ENTER_WORLD_NOTIFICATION;
	}

	public int chunkX() {
		return chunkX;
	}

	public int chunkY() {
		return chunkY;
	}

	public long tick0Time() {
		return tick0Time;
	}

}
