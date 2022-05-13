package p2pinfiniteworld.event.serverconnect;

import static p2pinfiniteworld.protocols.P2PIWNetworkProtocol.READY_TO_JOIN_NETWORK_RESPONSE;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

import context.input.networking.packet.PacketBuilder;
import engine.common.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import engine.common.networking.packet.address.PacketAddress;
import engine.common.source.NetworkSource;
import networking.ServerNetworkUtils;
import p2pinfiniteworld.event.P2PIWNetworkEvent;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocol;

public class P2PIWReadyToJoinNetworkResponse extends P2PIWNetworkEvent {

	private int chunkX, chunkY;
	private List<PacketAddress> networkAddresses = new ArrayList<>();

	public P2PIWReadyToJoinNetworkResponse(int chunkX, int chunkY, List<PacketAddress> networkAddresses) {
		super(null);
		this.chunkX = chunkX;
		this.chunkY = chunkY;
		this.networkAddresses = networkAddresses;
	}

	public P2PIWReadyToJoinNetworkResponse(NetworkSource source, PacketReader reader) {
		super(source);
		chunkX = reader.readInt();
		chunkY = reader.readInt();
		byte[][] ips = reader.readIPv4Array();
		short[] ports = reader.readShortArray();
		for (int i = 0; i < ips.length; i++) {
			networkAddresses().add(ServerNetworkUtils.toAddress(ips[i], ports[i]));
		}
		reader.close();
	}

	@Override
	protected PacketModel toPacketModel(PacketBuilder builder) {
		Inet4Address[] ips = new Inet4Address[networkAddresses().size()];
		short[] ports = new short[networkAddresses().size()];
		for (int i = 0; i < networkAddresses().size(); i++) {
			PacketAddress address = networkAddresses().get(i);
			ips[i] = (Inet4Address) address.ip();
			ports[i] = address.shortPort();
		}
		return builder
				.consume(chunkX)
				.consume(chunkY)
				.consume(ips)
				.consume(ports)
				.build();
	}

	@Override
	protected P2PIWNetworkProtocol protocol() {
		return READY_TO_JOIN_NETWORK_RESPONSE;
	}

	public List<PacketAddress> networkAddresses() {
		return networkAddresses;
	}

	public int chunkX() {
		return chunkX;
	}

	public int chunkY() {
		return chunkY;
	}

}
