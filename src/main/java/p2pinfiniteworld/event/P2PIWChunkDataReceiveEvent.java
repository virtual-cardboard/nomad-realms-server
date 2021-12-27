package p2pinfiniteworld.event;

import static p2pinfiniteworld.protocols.P2PIWServerProtocol.CHUNK_DATA;

import common.source.NetworkSource;
import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import p2pinfiniteworld.protocols.P2PIWServerProtocol;

public class P2PIWChunkDataReceiveEvent extends P2PIWServerGameEvent {

	private int chunkX, chunkY;
	private int age, value;

	public P2PIWChunkDataReceiveEvent(NetworkSource source, PacketReader reader) {
		super(source);
		this.chunkX = reader.readInt();
		this.chunkY = reader.readInt();
		this.age = reader.readInt();
		this.value = reader.readInt();
		reader.close();
	}

	@Override
	protected PacketModel toPacketModel(PacketBuilder builder) {
		return builder
				.consume(chunkX)
				.consume(chunkY)
				.consume(age)
				.consume(value)
				.build();
	}

	@Override
	protected P2PIWServerProtocol protocol() {
		return CHUNK_DATA;
	}

	public int chunkX() {
		return chunkX;
	}

	public int chunkY() {
		return chunkY;
	}
	
	public int age() {
		return age;
	}
	
	public int value() {
		return value;
	}

}
