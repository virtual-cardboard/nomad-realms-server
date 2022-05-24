package p2pinfiniteworld.event.serverconnect;

import static p2pinfiniteworld.protocols.P2PIWNetworkProtocol.JOIN_NETWORK_REQUEST;

import derealizer.SerializationReader;
import derealizer.SerializationWriter;
import p2pinfiniteworld.event.P2PIWNetworkEvent;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocol;

public class JoinNetworkRequest extends P2PIWNetworkEvent {

	private String username;

	public JoinNetworkRequest() {
	}

	public JoinNetworkRequest(String username) {
		this.username = username;
	}

	@Override
	public void read(SerializationReader reader) {
		this.username = reader.readStringUtf8();
	}

	@Override
	public void write(SerializationWriter writer) {
		writer.consume(username);
	}

	public String username() {
		return username;
	}

	@Override
	protected P2PIWNetworkProtocol protocol() {
		return JOIN_NETWORK_REQUEST;
	}

}
