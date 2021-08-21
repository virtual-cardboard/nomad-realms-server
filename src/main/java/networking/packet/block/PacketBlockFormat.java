package networking.packet.block;

import java.util.LinkedList;
import java.util.Queue;

import networking.packet.cryption.EncryptionAlgorithmType;

public class PacketBlockFormat {

	private Queue<PacketPrimitive> primitives;
	private Queue<EncryptionAlgorithmType> encryptions;

	public PacketBlockBuilder builder() {
		return new PacketBlockBuilder(this);
	}

	public Queue<PacketPrimitive> primitives() {
		return new LinkedList<>(primitives);
	}

	public Queue<EncryptionAlgorithmType> encryptions() {
		return new LinkedList<>(encryptions);
	}

}
