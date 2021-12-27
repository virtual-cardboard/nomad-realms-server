package nomadrealms.protocols;

import networking.NomadRealmsServerGameEvent;
import nomadrealms.event.bootstrap.BootstrapRequestEvent;
import nomadrealms.event.bootstrap.BootstrapResponseEvent;
import nomadrealms.event.world.CreateWorldRequestEvent;
import nomadrealms.event.world.CreateWorldResponseEvent;

public enum NomadRealmsServerProtocols {

	BOOTSTRAP_REQUEST(BootstrapRequestEvent.class, 100),
	BOOTSTRAP_RESPONSE(BootstrapResponseEvent.class, 101),
	CREATE_WORLD_REQUEST(CreateWorldRequestEvent.class, 110),
	CREATE_WORLD_RESPONSE(CreateWorldResponseEvent.class, 111);

	private short id;
	private Class<? extends NomadRealmsServerGameEvent> clazz;

	private NomadRealmsServerProtocols(Class<? extends NomadRealmsServerGameEvent> clazz, int id) {
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
