package context.bootstrap;

import static protocol.BootstrapProtocol.BOOTSTRAP_REQUEST;

import java.net.InetAddress;
import java.net.UnknownHostException;

import common.source.NetworkSource;
import context.input.GameInput;
import context.input.networking.packet.PacketReader;
import context.input.networking.packet.address.PacketAddress;
import event.BootstrapRequestEvent;

public class BootstrapServerInput extends GameInput {

	public BootstrapServerInput() {
		addPacketReceivedFunction((event) -> {
			NetworkSource source = (NetworkSource) event.source();
			PacketReader reader = BOOTSTRAP_REQUEST.reader(event.model());
			long timestamp = reader.readLong();
			InetAddress lanIP = getIp(reader.readIPv4());
			short lanPort = reader.readShort();
			PacketAddress address = new PacketAddress(lanIP, lanPort);
			return new BootstrapRequestEvent(source, timestamp, address);
		});
	}

	private InetAddress getIp(byte[] addr) {
		try {
			return InetAddress.getByAddress(addr);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

}
