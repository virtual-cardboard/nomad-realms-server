package p2pinfiniteworld.event.serverconnect;

import static p2pinfiniteworld.protocols.P2PIWNetworkProtocol.ALREADY_IN_WORLD_RESPONSE;

import common.source.NetworkSource;
import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import p2pinfiniteworld.event.P2PIWNetworkEvent;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocol;

public class P2PIWAlreadyInWorldResponseEvent extends P2PIWNetworkEvent {

	private int chunkX, chunkY;
	private long tick0Time;

	public P2PIWAlreadyInWorldResponseEvent(int chunkX, int chunkY, long tick0Time) {
		super(null);
		this.chunkX = chunkX;
		this.chunkY = chunkY;
		this.tick0Time = tick0Time;
	}

	public P2PIWAlreadyInWorldResponseEvent(NetworkSource source, PacketReader reader) {
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
	protected P2PIWNetworkProtocol protocol() {
		return ALREADY_IN_WORLD_RESPONSE;
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
