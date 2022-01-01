package p2pinfiniteworld.event.peer2peer;

import static p2pinfiniteworld.protocols.P2PIWNetworkProtocol.CHUNK_DATA_REQUEST;

import common.source.NetworkSource;
import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import p2pinfiniteworld.event.P2PIWNetworkEvent;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocol;

public class P2PIWChunkDataRequestEvent extends P2PIWNetworkEvent {

	private int chunkX, chunkY;
	private long timestamp;

	public P2PIWChunkDataRequestEvent(NetworkSource source, PacketReader reader) {
		super(source);
		this.timestamp = reader.readLong();
		this.chunkX = reader.readInt();
		this.chunkY = reader.readInt();
		reader.close();
	}

	@Override
	protected PacketModel toPacketModel(PacketBuilder builder) {
		return builder
				.consume(timestamp)
				.consume(chunkX)
				.consume(chunkY)
				.build();
	}

	@Override
	protected P2PIWNetworkProtocol protocol() {
		return CHUNK_DATA_REQUEST;
	}

	public int chunkX() {
		return chunkX;
	}

	public int chunkY() {
		return chunkY;
	}

	public long timestamp() {
		return timestamp;
	}

}
