package p2pinfiniteworld.protocols;

import nomadrealms.event.NomadRealmsServerGameEvent;
import nomadrealms.event.bootstrap.BootstrapRequestEvent;
import nomadrealms.event.bootstrap.BootstrapResponseEvent;
import nomadrealms.event.world.CreateWorldRequestEvent;
import nomadrealms.event.world.CreateWorldResponseEvent;

public enum P2PIWServerProtocols {

	BOOTSTRAP_REQUEST(BootstrapRequestEvent.class, 100),
	BOOTSTRAP_RESPONSE(BootstrapResponseEvent.class, 101),
	CREATE_WORLD_REQUEST(CreateWorldRequestEvent.class, 110),
	CREATE_WORLD_RESPONSE(CreateWorldResponseEvent.class, 111);

	private static final P2PIWServerProtocols[] IDS = new P2PIWServerProtocols[Short.MAX_VALUE];

	static {
		P2PIWServerProtocols[] values = P2PIWServerProtocols.values();
		for (short i = 0; i < values.length; i++) {
			P2PIWServerProtocols value = values[i];
			IDS[value.id] = value;
		}
	}

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
