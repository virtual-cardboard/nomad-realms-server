package networking;

import java.net.InetAddress;
import java.net.UnknownHostException;

import engine.common.networking.packet.address.PacketAddress;
import engine.common.source.NetworkSource;

public class ServerNetworkUtils {

	public static final NetworkSource SERVER;
	public static final NetworkSource P2PIW_SERVER;

	static {
		InetAddress serverDest = null;
		try {
			serverDest = InetAddress.getByName("99.250.93.242");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		SERVER = new NetworkSource(new PacketAddress(serverDest, 45000));
		P2PIW_SERVER = new NetworkSource(new PacketAddress(serverDest, 45001));
	}

	public static InetAddress toAddress(byte[] bytes) {
		InetAddress ip = null;
		try {
			ip = InetAddress.getByAddress(bytes);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
	}

	public static PacketAddress toAddress(byte[] bytes, short port) {
		return new PacketAddress(toAddress(bytes), port & 0xFFFF);
	}

}
