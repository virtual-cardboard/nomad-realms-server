package context;

import static protocol.STUNProtocol.STUN_REQUEST;

import common.GameInputEventHandler;
import common.source.NetworkSource;
import context.input.GameInput;
import context.input.networking.packet.PacketReader;
import event.STUNRequestEvent;

public class STUNInput extends GameInput {

	public STUNInput() {
		addPacketReceivedFunction(new GameInputEventHandler<>((event) -> {
			NetworkSource source = (NetworkSource) event.source();
			PacketReader reader = STUN_REQUEST.reader(event.model());
			long timestamp = reader.readLong();
			long nonce = reader.readLong();
			return new STUNRequestEvent(System.currentTimeMillis(), source, timestamp, nonce);
		}));
	}

}
