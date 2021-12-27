package p2pinfiniteworld.protocols;

import networking.NomadRealmsServerGameEvent;
import nomadrealms.event.world.CreateWorldResponseEvent;

public enum P2PIWServerProtocols {

	JOIN_WORLD_REQUEST(CreateWorldResponseEvent.class, 111);

	private short id;
	private Class<? extends NomadRealmsServerGameEvent> clazz;

	private P2PIWServerProtocols(Class<? extends NomadRealmsServerGameEvent> clazz, int id) {
		this.clazz = clazz;
		this.id = (short) id;
	}

	public short id() {
		return id;
	}

	public Class<? extends NomadRealmsServerGameEvent> clazz() {
		return clazz;
	}

}
