package p2pinfiniteworld.protocols;

import p2pinfiniteworld.event.P2PIWJoinWorldEvent;
import p2pinfiniteworld.event.P2PIWServerGameEvent;

public enum P2PIWServerProtocols {

	JOIN_WORLD_REQUEST(P2PIWJoinWorldEvent.class, 111);

	private short id;
	private Class<? extends P2PIWServerGameEvent> clazz;

	private P2PIWServerProtocols(Class<? extends P2PIWServerGameEvent> clazz, int id) {
		this.clazz = clazz;
		this.id = (short) id;
	}

	public short id() {
		return id;
	}

	public Class<? extends P2PIWServerGameEvent> clazz() {
		return clazz;
	}

}
