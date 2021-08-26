package context;

import static protocol.STUNProtocol.STUN_REQUEST;

import java.util.List;

import common.GameInputEventHandler;
import common.source.NetworkSource;
import context.input.GameInput;
import context.input.networking.packet.block.PacketBlock;
import context.input.networking.packet.block.PacketBlockReader;
import event.STUNRequestEvent;

public class STUNInput extends GameInput {

	public STUNInput() {
		addPacketReceivedFunction(new GameInputEventHandler<>((packet) -> {
			NetworkSource source = (NetworkSource) packet.getSource();
			List<PacketBlock> blocks = packet.getModel().blocks();
			if (blocks.size() != 1) {
				throw new RuntimeException("Expected block of length 1");
			}
			PacketBlockReader reader = STUN_REQUEST.reader(blocks.get(0));
			long timestamp = reader.readLong();
			long nonce = reader.readLong();
			return new STUNRequestEvent(System.currentTimeMillis(), source, timestamp, nonce);
		}));
	}

}
