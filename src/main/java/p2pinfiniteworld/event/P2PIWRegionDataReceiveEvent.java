package p2pinfiniteworld.event;

import static p2pinfiniteworld.protocols.P2PIWServerProtocol.REGION_DATA;

import common.source.NetworkSource;
import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import p2pinfiniteworld.protocols.P2PIWServerProtocol;

public class P2PIWRegionDataReceiveEvent extends P2PIWServerGameEvent {

	private int regionX, regionY;
	private int[][] ages = new int[4][4];
	private int[][] values = new int[4][4];

	public P2PIWRegionDataReceiveEvent(NetworkSource source, PacketReader reader) {
		super(source);
		this.regionX = reader.readInt();
		this.regionY = reader.readInt();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				ages[i][j] = reader.readInt();
				values[i][j] = reader.readInt();
			}
		}
		reader.close();
	}

	@Override
	protected PacketModel toPacketModel(PacketBuilder builder) {
		builder.consume(regionX).consume(regionY);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				builder.consume(ages[i][j]).consume(values[i][j]);
			}
		}
		return builder.build();
	}

	@Override
	protected P2PIWServerProtocol protocol() {
		return REGION_DATA;
	}

	public int regionX() {
		return regionX;
	}

	public int regionY() {
		return regionY;
	}

	public int[][] ages() {
		return ages;
	}

	public int[][] values() {
		return values;
	}

}
