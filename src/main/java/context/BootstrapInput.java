package context;

import static protocol.BootstrapProtocol.BOOTSTRAP_REQUEST;

import java.net.InetAddress;
import java.net.UnknownHostException;

import common.GameInputEventHandler;
import common.source.NetworkSource;
import context.input.GameInput;
import context.input.networking.packet.PacketReader;
import context.input.networking.packet.address.PacketAddress;
import context.input.networking.packet.address.PeerAddress;
import event.BootstrapRequestEvent;

public class BootstrapInput extends GameInput {

	public BootstrapInput() {
		addPacketReceivedFunction(new GameInputEventHandler<>((event) -> {
			NetworkSource source = (NetworkSource) event.getSource();
			PacketReader reader = BOOTSTRAP_REQUEST.reader(event.getModel());
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
