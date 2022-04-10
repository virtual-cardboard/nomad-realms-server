package p2pinfiniteworld.protocols;

import static networking.ServerNetworkUtils.PROTOCOL_ID;

import java.lang.reflect.Constructor;
import java.util.function.Function;

import context.input.event.PacketReceivedInputEvent;
import context.input.networking.packet.PacketFormat;
import context.input.networking.packet.PacketReader;
import engine.common.event.GameEvent;
import engine.common.source.NetworkSource;
import p2pinfiniteworld.event.P2PIWNetworkEvent;

public class P2PIWNetworkProtocolDecoder implements Function<PacketReceivedInputEvent, GameEvent> {

	@SuppressWarnings("unchecked")
	private static final Constructor<? extends P2PIWNetworkEvent>[] PROTOCOL_EVENTS = new Constructor[Short.MAX_VALUE];
	private static final PacketFormat[] PROTOCOL_FORMATS = new PacketFormat[Short.MAX_VALUE];

	static {
		P2PIWNetworkProtocol[] values = P2PIWNetworkProtocol.values();
		for (short i = 0; i < values.length; i++) {
			P2PIWNetworkProtocol value = values[i];
			Class<? extends P2PIWNetworkEvent> clazz = value.clazz();
			Constructor<? extends P2PIWNetworkEvent> constructor = null;
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
		PacketReader idReader = PROTOCOL_ID.reader(event.model());
		int id = idReader.readShort() & 0xFFFF;

		Constructor<? extends P2PIWNetworkEvent> constructor = PROTOCOL_EVENTS[id];
		PacketFormat format = PROTOCOL_FORMATS[id];
		PacketReader protocolReader = format.reader(idReader);

		try {
			P2PIWNetworkEvent instance = constructor.newInstance(event.source(), protocolReader);
			print(event, instance);
			return instance;
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Unknown protocol id " + id);
		return null;
	}

	private void print(PacketReceivedInputEvent event, P2PIWNetworkEvent instance) {
		String simpleName = instance.getClass().getSimpleName();
		if (simpleName.startsWith("P2PIW")) {
			simpleName = simpleName.substring(5);
		}
		System.out.println("Received " + simpleName + " from: " + event.source().address());
	}

}
