package p2pinfiniteworld.event.serverconnect;

import static p2pinfiniteworld.protocols.P2PIWNetworkProtocol.CHUNK_DATA_REQUEST;

import derealizer.SerializationReader;
import derealizer.SerializationWriter;
import p2pinfiniteworld.event.P2PIWNetworkEvent;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocol;

public class ChunkDataRequest extends P2PIWNetworkEvent {

	private long timestamp;
	private int chunkX;
	private int chunkY;

	public ChunkDataRequest() {
	}

	public ChunkDataRequest(long timestamp, int chunkX, int chunkY) {
		this.timestamp = timestamp;
		this.chunkX = chunkX;
		this.chunkY = chunkY;
	}

	@Override
	public void read(SerializationReader reader) {
		this.timestamp = reader.readLong();
		this.chunkX = reader.readInt();
		this.chunkY = reader.readInt();
	}

	@Override
	public void write(SerializationWriter writer) {
		writer.consume(timestamp);
		writer.consume(chunkX);
		writer.consume(chunkY);
	}

	public long timestamp() {
		return timestamp;
	}

	public int chunkX() {
		return chunkX;
	}

	public int chunkY() {
		return chunkY;
	}

	@Override
	protected P2PIWNetworkProtocol protocol() {
		return CHUNK_DATA_REQUEST;
	}

}
