package p2pinfiniteworld.event.serverconnect;

import static p2pinfiniteworld.protocols.P2PIWNetworkProtocol.ENTER_REGION_NOTIFICATION;

import derealizer.SerializationReader;
import derealizer.SerializationWriter;
import p2pinfiniteworld.event.P2PIWNetworkEvent;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocol;

public class EnterRegionNotification extends P2PIWNetworkEvent {

	private int regionX;
	private int regionY;

	public EnterRegionNotification() {
	}

	public EnterRegionNotification(int regionX, int regionY) {
		this.regionX = regionX;
		this.regionY = regionY;
	}

	@Override
	public void read(SerializationReader reader) {
		this.regionX = reader.readInt();
		this.regionY = reader.readInt();
	}

	@Override
	public void write(SerializationWriter writer) {
		writer.consume(regionX);
		writer.consume(regionY);
	}

	public int regionX() {
		return regionX;
	}

	public int regionY() {
		return regionY;
	}

	@Override
	protected P2PIWNetworkProtocol protocol() {
		return ENTER_REGION_NOTIFICATION;
	}

}
