package p2pinfiniteworld.protocols;

import java.lang.reflect.Constructor;
import java.util.function.Function;

import context.input.event.PacketReceivedInputEvent;
import derealizer.SerializationClassGenerator;
import derealizer.SerializationReader;
import engine.common.event.GameEvent;
import networking.protocols.NomadRealmsP2PNetworkProtocol;
import p2pinfiniteworld.event.P2PIWNetworkEvent;

public class P2PIWNetworkProtocolDecoder implements Function<PacketReceivedInputEvent, GameEvent> {

	@SuppressWarnings("unchecked")
	private static final Constructor<? extends P2PIWNetworkEvent>[] PROTOCOL_EVENTS = new Constructor[Short.MAX_VALUE];

	static {
		P2PIWNetworkProtocol[] values = P2PIWNetworkProtocol.values();
		for (short i = 0; i < values.length; i++) {
			P2PIWNetworkProtocol value = values[i];
			short id = value.id();
			if (id == -1) {
				throw new RuntimeException("No id set for P2P Network Event " + value.name() + ". " +
						"Set its id in " + NomadRealmsP2PNetworkProtocol.class.getSimpleName() + "'s static block.\n" +
						"E.g. " + value.name() + ".id = 137;");
			}
			Class<? extends P2PIWNetworkEvent> clazz = value.pojoClass();
			if (clazz == null) {
				throw new RuntimeException("No POJO class defined for P2P Network Event " + value.name() + ". " +
						"Try using " + SerializationClassGenerator.class.getSimpleName() + " to generate a POJO class for you.");
			}
			try {
				PROTOCOL_EVENTS[id] = clazz.getConstructor();
			} catch (NoSuchMethodException e) {
				System.err.println("Could not find no-arg constructor in class " + clazz.getSimpleName());
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public GameEvent apply(PacketReceivedInputEvent event) {
		SerializationReader reader = new SerializationReader(event.model().bytes());
		int protocolId = reader.readShort();

		Constructor<? extends P2PIWNetworkEvent> constructor = PROTOCOL_EVENTS[protocolId];

		try {
			P2PIWNetworkEvent networkEvent = constructor.newInstance();
			networkEvent.read(reader);
			return networkEvent;
		} catch (Exception e) {
			System.err.println("Could not create " + constructor.getDeclaringClass().getSimpleName()
					+ " from constructor.");
			e.printStackTrace();
		}

		System.out.println("Unknown protocol id " + protocolId);
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
