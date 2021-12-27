package nomadrealms.protocols;

import static context.input.networking.packet.PacketPrimitive.IP_V4;
import static context.input.networking.packet.PacketPrimitive.LONG;
import static context.input.networking.packet.PacketPrimitive.SHORT;
import static context.input.networking.packet.PacketPrimitive.STRING;

import context.input.networking.packet.PacketFormat;
import nomadrealms.event.NomadRealmsServerGameEvent;
import nomadrealms.event.bootstrap.BootstrapRequestEvent;
import nomadrealms.event.bootstrap.BootstrapResponseEvent;
import nomadrealms.event.world.CreateWorldRequestEvent;
import nomadrealms.event.world.CreateWorldResponseEvent;

public enum NomadRealmsServerProtocol {

	/**
	 * protocol_id(100): timestamp, lan_ip, lan_port, username
	 */
	BOOTSTRAP_REQUEST(BootstrapRequestEvent.class, 100, new PacketFormat().with(LONG, IP_V4, SHORT, STRING)),

	/**
	 * protocol_id(101): timestamp, nonce, lan_ip, lan_port, wan_ip, wan_port,
	 * username
	 */
	BOOTSTRAP_RESPONSE(BootstrapResponseEvent.class, 101, new PacketFormat().with(LONG, LONG, IP_V4, SHORT, IP_V4, SHORT, STRING)),

	/**
	 * protocol_id(110): timestamp, world_name
	 */
	CREATE_WORLD_REQUEST(CreateWorldRequestEvent.class, 110, new PacketFormat().with(LONG, STRING)),

	/**
	 * protocol_id(111): timestamp, seed
	 */
	CREATE_WORLD_RESPONSE(CreateWorldResponseEvent.class, 111, new PacketFormat().with(LONG, LONG));

	private short id;
	private Class<? extends NomadRealmsServerGameEvent> clazz;
	private PacketFormat format;

	private NomadRealmsServerProtocol(Class<? extends NomadRealmsServerGameEvent> clazz, int id, PacketFormat format) {
		this.clazz = clazz;
		this.id = (short) id;
		this.format = format;
	}

	public short id() {
		return id;
	}

	public Class<? extends NomadRealmsServerGameEvent> clazz() {
		return clazz;
	}

	public PacketFormat format() {
		return format;
	}

}
