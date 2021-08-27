package context;

import static protocol.BootstrapProtocol.BOOTSTRAP_REQUEST;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import common.GameInputEventHandler;
import common.source.NetworkSource;
import context.input.GameInput;
import context.input.networking.packet.address.PacketAddress;
import context.input.networking.packet.address.PeerAddress;
import context.input.networking.packet.block.PacketBlock;
import context.input.networking.packet.block.PacketBlockReader;
import event.BootstrapRequestEvent;

public class BootstrapInput extends GameInput {

	public BootstrapInput() {
		addPacketReceivedFunction(new GameInputEventHandler<>((event) -> {
			NetworkSource source = (NetworkSource) event.getSource();
			List<PacketBlock> blocks = event.getModel().blocks();
			if (blocks.size() != 1) {
				throw new RuntimeException("Expected block of length 1");
			}
			PacketBlockReader reader = BOOTSTRAP_REQUEST.reader(blocks.get(0));
			long timestamp = reader.readLong();
			InetAddress ip;
			try {
				ip = InetAddress.getByAddress(reader.readIPv4());
			} catch (UnknownHostException e) {
				e.printStackTrace();
				return null;
			}
			short port = reader.readShort();
			PacketAddress address = new PeerAddress(ip, port);
			return new BootstrapRequestEvent(source, timestamp, address);
		}));
	}

}
