package p2pinfiniteworld.event.peer2peer;

import static p2pinfiniteworld.protocols.P2PIWNetworkProtocol.ENTER_REGION_NOTIFICATION;

import common.source.NetworkSource;
import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import p2pinfiniteworld.event.P2PIWNetworkEvent;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocol;

public class P2PIWEnterRegionNotificationEvent extends P2PIWNetworkEvent {

	private byte id;
	private int regionX, regionY;

	public P2PIWEnterRegionNotificationEvent(NetworkSource source, PacketReader reader) {
		super(source);
		this.id = reader.readByte();
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
	protected P2PIWNetworkProtocol protocol() {
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
