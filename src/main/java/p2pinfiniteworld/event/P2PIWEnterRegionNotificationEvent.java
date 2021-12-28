package p2pinfiniteworld.event;

import static p2pinfiniteworld.protocols.P2PIWServerProtocol.ENTER_REGION_NOTIFICATION;

import common.source.NetworkSource;
import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import p2pinfiniteworld.protocols.P2PIWServerProtocol;

public class P2PIWEnterRegionNotificationEvent extends P2PIWServerGameEvent {

	private int id;
	private int regionX, regionY;

	public P2PIWEnterRegionNotificationEvent(NetworkSource source, PacketReader reader) {
		super(source);
		this.id = reader.readInt();
		this.regionX = reader.readInt();
		this.regionY = reader.readInt();
		reader.close();
	}

	@Override
	protected PacketModel toPacketModel(PacketBuilder builder) {
		return builder
				.consume(id)
				.consume(regionX)
				.consume(regionY)
				.build();
	}

	@Override
	protected P2PIWServerProtocol protocol() {
		return ENTER_REGION_NOTIFICATION;
	}

	public int id() {
		return id;
	}

	public int regionX() {
		return regionX;
	}

	public int regionY() {
		return regionY;
	}

}
