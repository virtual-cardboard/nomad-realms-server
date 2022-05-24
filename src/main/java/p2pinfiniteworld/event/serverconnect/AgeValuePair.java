package p2pinfiniteworld.event.serverconnect;

import derealizer.SerializationReader;
import derealizer.SerializationWriter;
import p2pinfiniteworld.event.P2PIWNetworkEvent;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocol;

public class AgeValuePair extends P2PIWNetworkEvent {

	private int age;
	private short value;

	public AgeValuePair() {
	}

	public AgeValuePair(int age, short value) {
		this.age = age;
		this.value = value;
	}

	@Override
	public void read(SerializationReader reader) {
		this.age = reader.readInt();
		this.value = reader.readShort();
	}

	@Override
	public void write(SerializationWriter writer) {
		writer.consume(age);
		writer.consume(value);
	}

	public int age() {
		return age;
	}

	public short value() {
		return value;
	}

	@Override
	protected P2PIWNetworkProtocol protocol() {
		return P2PIWNetworkProtocol.AGE_VALUE_PAIR;
	}

}
