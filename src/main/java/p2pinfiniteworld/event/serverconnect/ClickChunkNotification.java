package p2pinfiniteworld.event.serverconnect;

import static p2pinfiniteworld.protocols.P2PIWNetworkProtocol.CLICK_CHUNK_NOTIFICATION;

import derealizer.SerializationReader;
import derealizer.SerializationWriter;
import p2pinfiniteworld.event.P2PIWNetworkEvent;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocol;

public class ClickChunkNotification extends P2PIWNetworkEvent {

	private byte user_id;
	private int chunkX;
	private int chunkY;

	public ClickChunkNotification() {
	}

	public ClickChunkNotification(byte user_id, int chunkX, int chunkY) {
		this.user_id = user_id;
		this.chunkX = chunkX;
		this.chunkY = chunkY;
	}

	@Override
	public void read(SerializationReader reader) {
		this.user_id = reader.readByte();
		this.chunkX = reader.readInt();
		this.chunkY = reader.readInt();
	}

	@Override
	public void write(SerializationWriter writer) {
		writer.consume(user_id);
		writer.consume(chunkX);
		writer.consume(chunkY);
	}

	public byte user_id() {
		return user_id;
	}

	public int chunkX() {
		return chunkX;
	}

	public int chunkY() {
		return chunkY;
	}

	@Override
	protected P2PIWNetworkProtocol protocol() {
		return CLICK_CHUNK_NOTIFICATION;
	}

}
