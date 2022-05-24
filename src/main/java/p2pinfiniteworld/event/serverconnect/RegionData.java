package p2pinfiniteworld.event.serverconnect;

import static p2pinfiniteworld.protocols.P2PIWNetworkProtocol.REGION_DATA;

import java.util.ArrayList;
import java.util.List;

import derealizer.SerializationReader;
import derealizer.SerializationWriter;
import p2pinfiniteworld.event.P2PIWNetworkEvent;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocol;

public class RegionData extends P2PIWNetworkEvent {

	private int regionX;
	private int regionY;
	private List<AgeValuePair> ageValuePairs;

	public RegionData() {
	}

	public RegionData(int regionX, int regionY, List<AgeValuePair> ageValuePairs) {
		this.regionX = regionX;
		this.regionY = regionY;
		this.ageValuePairs = ageValuePairs;
	}

	@Override
	public void read(SerializationReader reader) {
		this.regionX = reader.readInt();
		this.regionY = reader.readInt();
		this.ageValuePairs = new ArrayList<>();
		for (byte i0 = 0, numElements0 = reader.readByte(); i0 < numElements0; i0++) {
			AgeValuePair pojo1 = new AgeValuePair();
			pojo1.read(reader);
			ageValuePairs.add(pojo1);
		}
	}

	@Override
	public void write(SerializationWriter writer) {
		writer.consume(regionX);
		writer.consume(regionY);
		writer.consume((byte) ageValuePairs.size());
		for (int i0 = 0; i0 < ageValuePairs.size(); i0++) {
			ageValuePairs.get(i0).write(writer);
		}
	}

	public int regionX() {
		return regionX;
	}

	public int regionY() {
		return regionY;
	}

	public List<AgeValuePair> ageValuePairs() {
		return ageValuePairs;
	}

	@Override
	protected P2PIWNetworkProtocol protocol() {
		return REGION_DATA;
	}

}
