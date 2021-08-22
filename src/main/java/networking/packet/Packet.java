package networking.packet;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;

import networking.packet.block.PacketBlock;
import networking.packet.destination.PacketDestination;

public class Packet {

	private PacketDestination dest;
	private List<PacketBlock> payload;

	public Packet(PacketDestination dest, List<PacketBlock> payload) {
		this.dest = dest;
		this.payload = payload;
	}

	/**
	 * Constructs the actual {@link DatagramPacket} that can be sent from a
	 * {@link DatagramSocket}. Should only be called once for efficiency.
	 * 
	 * @return
	 */
	public DatagramPacket packet() {
		int totalSize = 0;
		for (int i = 0, numBlocks = payload.size(); i < numBlocks; i++) {
			PacketBlock block = payload.get(i);
			totalSize += block.bytes().length;
		}
		byte[] bytes = new byte[totalSize];
		int index = 0;
		for (int i = 0, numBlocks = payload.size(); i < numBlocks; i++) {
			PacketBlock block = payload.get(i);
			byte[] blockBytes = block.bytes();
			System.arraycopy(blockBytes, 0, bytes, bytes[index], blockBytes.length);
		}
		DatagramPacket datagramPacket = new DatagramPacket(bytes, totalSize, dest.ip(), dest.port());
		return datagramPacket;
	}

}
