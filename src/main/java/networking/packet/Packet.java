package networking.packet;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;

import networking.packet.block.PacketBlock;
import networking.packet.destination.PacketDestination;

public class Packet {

	private PacketDestination dest;
	private PacketBlock header;
	private List<PacketBlock> payload;

	public Packet(PacketDestination dest, PacketBlock header, List<PacketBlock> payload) {
		this.dest = dest;
		this.header = header;
		this.payload = payload;
	}

	/**
	 * Constructs the actual {@link DatagramPacket} that can be sent from a
	 * {@link DatagramSocket}. Should only be called once for efficiency.
	 * 
	 * @return
	 */
	public DatagramPacket packet() {
		DatagramPacket datagramPacket = new DatagramPacket(null, 0, dest.ip(), dest.port());
		return datagramPacket;
	}

}
