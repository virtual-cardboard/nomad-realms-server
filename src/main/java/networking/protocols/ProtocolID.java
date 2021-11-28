package networking.protocols;

import event.NomadRealmsServerGameEvent;
import event.bootstrap.BootstrapRequestEvent;
import event.bootstrap.BootstrapResponseEvent;

public enum ProtocolID {

	BOOTSTRAP_REQUEST(BootstrapRequestEvent.class, 100),
	BOOTSTRAP_RESPONSE(BootstrapResponseEvent.class, 101);

	private static final ProtocolID[] IDS = new ProtocolID[Short.MAX_VALUE];

	static {
		ProtocolID[] values = ProtocolID.values();
		for (short i = 0; i < values.length; i++) {
			ProtocolID value = values[i];
			IDS[value.id] = value;
		}
	}

	private short id;
	private Class<? extends NomadRealmsServerGameEvent> clazz;

	private ProtocolID(Class<? extends NomadRealmsServerGameEvent> clazz, int id) {
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
