package bootstrap;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;

public class NomadRealmsBootstrapServer {

	private static final int TEN_SECONDS = 10000;

	private boolean isDone = false;
	private final DatagramSocket socket;
	private final byte[] buffers = new byte[256];
	private final DatagramPacket message = new DatagramPacket(buffers, buffers.length);

	public NomadRealmsBootstrapServer() throws SocketException {
		socket = new DatagramSocket(45001);
		socket.setSoTimeout(TEN_SECONDS);
	}

	public void start() {
		readNext();
		InetSocketAddress socketAddress1 = (InetSocketAddress) message.getSocketAddress();
		System.out.println("Socket Address 1: " + socketAddress1);
		byte[] ip1 = socketAddress1.getAddress().getAddress(); // 4 bytes
		int port1 = socketAddress1.getPort(); // 2 bytes
		System.out.println("IP 1: " + Arrays.toString(ip1));
		System.out.println("Port 1: " + port1);

		readNext();
		InetSocketAddress socketAddress2 = (InetSocketAddress) message.getSocketAddress();
		System.out.println("Socket Address 2: " + socketAddress2);
		byte[] ip2 = socketAddress2.getAddress().getAddress(); // 4 bytes
		int port2 = socketAddress2.getPort(); // 2 bytes
		System.out.println("IP 2: " + Arrays.toString(ip2));
		System.out.println("Port 2: " + port2);

		System.out.println("Sending hole punch info to " + socketAddress1 + " and " + socketAddress2);

		try {
			socket.send(createHolePunchInfoPacket(ip1, port1, socketAddress2));
			socket.send(createHolePunchInfoPacket(ip2, port2, socketAddress1));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public DatagramPacket createHolePunchInfoPacket(byte[] infoIp, int infoPort, SocketAddress dest) {
		byte[] holePunchBuffer = new byte[6];
		DatagramPacket holePunchInfo = new DatagramPacket(holePunchBuffer, holePunchBuffer.length, dest);
		for (int i = 0; i < 4; i++) {
			holePunchBuffer[i] = infoIp[i];
		}
		holePunchBuffer[4] = (byte) (infoPort >> 8);
		holePunchBuffer[5] = (byte) (infoPort % 8);
		return holePunchInfo;
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
