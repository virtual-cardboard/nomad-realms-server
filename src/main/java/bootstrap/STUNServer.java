package bootstrap;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;

public class STUNServer {

	private static final int TEN_SECONDS = 10000;
	private static final int PORT = 45001;

	private boolean isDone = false;
	private final DatagramSocket socket;
	private final byte[] buffers = new byte[256];
	private final DatagramPacket message = new DatagramPacket(buffers, buffers.length);

	public STUNServer() throws SocketException {
		socket = new DatagramSocket(PORT);
		socket.setSoTimeout(TEN_SECONDS);
	}

	public void start() {
		System.out.println("Starting STUN server at port " + PORT);
		while (!isDone) {
			try {
				socket.receive(message);
				socket.send(createSTUNInfoPacket((InetSocketAddress) message.getSocketAddress()));
			} catch (SocketTimeoutException e) {
				System.out.println("Receive timed out");
				continue;
			} catch (IOException e) {
				System.out.println("Error when receiving packet");
				e.printStackTrace();
				continue;
			}
			System.out.println("Message received");
			System.out.println(message.getSocketAddress());
			System.out.println(Arrays.toString(buffers));
		}
	}

	public DatagramPacket createSTUNInfoPacket(InetSocketAddress dest) {
		byte[] infoIp = dest.getAddress().getAddress(); // 4 bytes
		int infoPort = dest.getPort(); // 2 bytes
		byte[] stunBuffer = new byte[6];
		DatagramPacket stunPacket = new DatagramPacket(stunBuffer, stunBuffer.length, dest);
		for (int i = 0; i < 4; i++) {
			stunBuffer[i] = infoIp[i];
		}
		stunBuffer[4] = (byte) (infoPort >> 8);
		stunBuffer[5] = (byte) (infoPort & 0xFF);
		return stunPacket;
	}

	public void continuousRead() {
		while (!isDone) {
			try {
				socket.receive(message);
			} catch (SocketTimeoutException e) {
				System.out.println("Receive timed out");
				continue;
			} catch (IOException e) {
				System.out.println("Error when receiving packet");
				e.printStackTrace();
				continue;
			}
			System.out.println("Message received");
			System.out.println(message.getSocketAddress());
			System.out.println(Arrays.toString(buffers));
		}
	}

	public void readNext() {
		while (!isDone) {
			try {
				socket.receive(message);
			} catch (SocketTimeoutException e) {
				System.out.println("Receive timed out");
				continue;
			} catch (IOException e) {
				System.out.println("Error when receiving packet");
				e.printStackTrace();
				continue;
			}
			break;
		}
	}

}
