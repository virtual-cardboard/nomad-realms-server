package context.singlebootstrap;

import static protocol.BootstrapProtocol.BOOTSTRAP_REQUEST;

import java.net.InetAddress;
import java.net.UnknownHostException;

import common.GameInputEventHandler;
import common.source.NetworkSource;
import context.input.GameInput;
import context.input.networking.packet.PacketReader;
import context.input.networking.packet.address.PacketAddress;
import event.BootstrapRequestEvent;

public class SingleBootstrapInput extends GameInput {

	public SingleBootstrapInput() {
		addPacketReceivedFunction(new GameInputEventHandler<>((event) -> {
			NetworkSource source = (NetworkSource) event.source();
			PacketReader reader = BOOTSTRAP_REQUEST.reader(event.model());
			long timestamp = reader.readLong();
			InetAddress ip;
			try {
				ip = InetAddress.getByAddress(reader.readIPv4());
			} catch (UnknownHostException e) {
				e.printStackTrace();
				return null;
			}
			short port = reader.readShort();
			PacketAddress address = new PacketAddress(ip, port);
			return new BootstrapRequestEvent(source, timestamp, address);
		}));
	}

}
