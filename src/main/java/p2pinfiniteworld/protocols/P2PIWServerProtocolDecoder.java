package p2pinfiniteworld.protocols;

import static networking.NetworkUtils.PROTOCOL_ID;

import java.lang.reflect.Constructor;
import java.util.function.Function;

import common.event.GameEvent;
import common.source.NetworkSource;
import context.input.event.PacketReceivedInputEvent;
import context.input.networking.packet.PacketReader;
import nomadrealms.event.NomadRealmsServerGameEvent;

public class P2PIWServerProtocolDecoder implements Function<PacketReceivedInputEvent, GameEvent> {

	@SuppressWarnings("unchecked")
	private static final Class<? extends NomadRealmsServerGameEvent>[] PROTOCOL_EVENTS = new Class[Short.MAX_VALUE];

	static {
		P2PIWServerProtocols[] values = P2PIWServerProtocols.values();
		for (short i = 0; i < values.length; i++) {
			P2PIWServerProtocols value = values[i];
			PROTOCOL_EVENTS[value.id()] = value.clazz();
		}
	}

	@Override
	public GameEvent apply(PacketReceivedInputEvent event) {
		System.out.println("Received packet from: " + event.source().address());
		PacketReader protocolReader = PROTOCOL_ID.reader(event.model());
		int id = protocolReader.readShort() & 0xFFFF;
		try {
			Constructor<? extends NomadRealmsServerGameEvent> constructor = PROTOCOL_EVENTS[id].getConstructor(NetworkSource.class, PacketReader.class);
			return constructor.newInstance(event.source(), protocolReader);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Unknown protocol id " + id);
		return null;
	}

}
