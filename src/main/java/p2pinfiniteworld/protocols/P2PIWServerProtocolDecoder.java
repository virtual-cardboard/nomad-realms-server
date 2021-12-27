package p2pinfiniteworld.protocols;

import static networking.NetworkUtils.PROTOCOL_ID;

import java.lang.reflect.Constructor;
import java.util.function.Function;

import common.event.GameEvent;
import common.source.NetworkSource;
import context.input.event.PacketReceivedInputEvent;
import context.input.networking.packet.PacketFormat;
import context.input.networking.packet.PacketReader;
import p2pinfiniteworld.event.P2PIWServerGameEvent;

public class P2PIWServerProtocolDecoder implements Function<PacketReceivedInputEvent, GameEvent> {

	@SuppressWarnings("unchecked")
	private static final Constructor<? extends P2PIWServerGameEvent>[] PROTOCOL_EVENTS = new Constructor[Short.MAX_VALUE];
	private static final PacketFormat[] PROTOCOL_FORMATS = new PacketFormat[Short.MAX_VALUE];

	static {
		P2PIWServerProtocol[] values = P2PIWServerProtocol.values();
		for (short i = 0; i < values.length; i++) {
			P2PIWServerProtocol value = values[i];
			Class<? extends P2PIWServerGameEvent> clazz = value.clazz();
			Constructor<? extends P2PIWServerGameEvent> constructor = null;
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

		Constructor<? extends P2PIWServerGameEvent> constructor = PROTOCOL_EVENTS[id];
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
