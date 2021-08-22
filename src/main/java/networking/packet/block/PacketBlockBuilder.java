package networking.packet.block;

import static networking.packet.block.PacketPrimitive.LONG;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.Queue;

import com.sun.corba.se.impl.io.TypeMismatchException;

import networking.packet.cryption.EncryptionAlgorithmType;

public class PacketBlockBuilder {

	private Queue<PacketPrimitive> primitives;
	@SuppressWarnings("unused")
	private Queue<EncryptionAlgorithmType> encryptions;
	private Queue<Byte> bytes = new LinkedList<>();

	public PacketBlockBuilder(PacketBlockFormat format) {
		primitives = format.primitives();
		encryptions = format.encryptions();
	}

	private void typeValidate(PacketPrimitive actual) {
		PacketPrimitive expected = primitives.poll();
		if (expected != actual) {
			throw new TypeMismatchException("Packet block creation failed: expected " + expected + ", actual " + actual);
		}
	}

	public void consume(long val) {
		typeValidate(LONG);
		bytes.add((byte) ((val >> 56) & 0xFF));
		bytes.add((byte) ((val >> 48) & 0xFF));
		bytes.add((byte) ((val >> 40) & 0xFF));
		bytes.add((byte) ((val >> 32) & 0xFF));
		bytes.add((byte) ((val >> 24) & 0xFF));
		bytes.add((byte) ((val >> 16) & 0xFF));
		bytes.add((byte) ((val >> 8) & 0xFF));
		bytes.add((byte) (val & 0xFF));
	}

	public void consume(int val) {
		typeValidate(PacketPrimitive.INT);
		bytes.add((byte) ((val >> 24) & 0xFF));
		bytes.add((byte) ((val >> 16) & 0xFF));
		bytes.add((byte) ((val >> 8) & 0xFF));
		bytes.add((byte) (val & 0xFF));
	}

	public void consume(short val) {
		typeValidate(PacketPrimitive.SHORT);
		bytes.add((byte) ((val >> 8) & 0xFF));
		bytes.add((byte) (val & 0xFF));
	}

	public void consume(byte val) {
		typeValidate(PacketPrimitive.BYTE);
		bytes.add(val);
	}

	public void consume(long[] val) {
		typeValidate(PacketPrimitive.LONG_ARRAY);
		for (long x : val) {
			bytes.add((byte) ((x >> 56) & 0xFF));
			bytes.add((byte) ((x >> 48) & 0xFF));
			bytes.add((byte) ((x >> 40) & 0xFF));
			bytes.add((byte) ((x >> 32) & 0xFF));
			bytes.add((byte) ((x >> 24) & 0xFF));
			bytes.add((byte) ((x >> 16) & 0xFF));
			bytes.add((byte) ((x >> 8) & 0xFF));
			bytes.add((byte) (x & 0xFF));
		}
	}

	public void consume(int[] val) {
		typeValidate(PacketPrimitive.INT_ARRAY);
		for (int x : val) {
			bytes.add((byte) ((x >> 24) & 0xFF));
			bytes.add((byte) ((x >> 16) & 0xFF));
			bytes.add((byte) ((x >> 8) & 0xFF));
			bytes.add((byte) (x & 0xFF));
		}
	}

	public void consume(short[] val) {
		typeValidate(PacketPrimitive.SHORT_ARRAY);
		for (short x : val) {
			bytes.add((byte) ((x >> 8) & 0xFF));
			bytes.add((byte) (x & 0xFF));
		}
	}

	public void consume(byte[] val) {
		typeValidate(PacketPrimitive.BYTE_ARRAY);
		for (byte x : val) {
			bytes.add(x);
		}
	}

	public void consume(InetAddress val) {
		if (val instanceof Inet6Address) {
			typeValidate(PacketPrimitive.IP_V6);
			byte[] address = val.getAddress();
			bytes.add(address[0]);
			bytes.add(address[1]);
			bytes.add(address[2]);
			bytes.add(address[3]);
			bytes.add(address[4]);
			bytes.add(address[5]);
		} else if (val instanceof Inet4Address) {
			typeValidate(PacketPrimitive.IP_V4);
			byte[] address = val.getAddress();
			bytes.add(address[0]);
			bytes.add(address[1]);
			bytes.add(address[2]);
			bytes.add(address[3]);
		}
	}

	public void consume(int ipv4_0, int ipv4_1, int ipv4_2, int ipv4_3) {
		typeValidate(PacketPrimitive.IP_V4);
		bytes.add((byte) ipv4_0);
		bytes.add((byte) ipv4_1);
		bytes.add((byte) ipv4_2);
		bytes.add((byte) ipv4_3);
	}

	public void consume(int ipv6_0, int ipv6_1, int ipv6_2, int ipv6_3, int ipv6_4, int ipv6_5) {
		typeValidate(PacketPrimitive.IP_V4);
		bytes.add((byte) ipv6_0);
		bytes.add((byte) ipv6_1);
		bytes.add((byte) ipv6_2);
		bytes.add((byte) ipv6_3);
		bytes.add((byte) ipv6_4);
		bytes.add((byte) ipv6_5);
	}

	public PacketBlock build() {
		byte[] buffer = new byte[bytes.size()];
		int i = 0;
		while (!bytes.isEmpty()) {
			buffer[i] = bytes.poll();
			i++;
		}
		// TODO
		// apply encryption
		PacketBlock packetBlock = new PacketBlock(buffer);
		return packetBlock;
	}

}