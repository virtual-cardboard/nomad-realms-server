package nomadrealms.protocols;

import static networking.NetworkUtils.PROTOCOL_ID;

import java.lang.reflect.Constructor;
import java.util.function.Function;

import common.event.GameEvent;
import common.source.NetworkSource;
import context.input.event.PacketReceivedInputEvent;
import context.input.networking.packet.PacketFormat;
import context.input.networking.packet.PacketReader;
import nomadrealms.event.NomadRealmsServerGameEvent;

public class NomadRealmsServerProtocolDecoder implements Function<PacketReceivedInputEvent, GameEvent> {

	@SuppressWarnings("unchecked")
	public static final Constructor<? extends NomadRealmsServerGameEvent>[] PROTOCOL_EVENTS = new Constructor[Short.MAX_VALUE];
	public static final PacketFormat[] PROTOCOL_FORMATS = new PacketFormat[Short.MAX_VALUE];

	static {
		NomadRealmsServerProtocol[] values = NomadRealmsServerProtocol.values();
		for (short i = 0; i < values.length; i++) {
			NomadRealmsServerProtocol value = values[i];
			Class<? extends NomadRealmsServerGameEvent> clazz = value.clazz();
			Constructor<? extends NomadRealmsServerGameEvent> constructor = null;
			try {
				constructor = clazz.getConstructor(NetworkSource.class, PacketReader.class);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			PROTOCOL_EVENTS[value.id()] = constructor;
			PROTOCOL_FORMATS[value.id()] = value.format();
		}
	}

	@Override
	public GameEvent apply(PacketReceivedInputEvent event) {
		System.out.println("Received packet from: " + event.source().address());

		PacketReader idReader = PROTOCOL_ID.reader(event.model());
		int id = idReader.readShort() & 0xFFFF;

		Constructor<? extends NomadRealmsServerGameEvent> constructor = PROTOCOL_EVENTS[id];
		PacketFormat format = PROTOCOL_FORMATS[id];
		PacketReader protocolReader = format.reader(idReader);

		try {
			return constructor.newInstance(event.source(), protocolReader);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Unknown protocol id " + id);
		return null;
	}

}
