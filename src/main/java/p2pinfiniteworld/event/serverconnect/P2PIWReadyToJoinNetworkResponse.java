package p2pinfiniteworld.event.serverconnect;

import static p2pinfiniteworld.protocols.P2PIWNetworkProtocol.ENTER_WORLD_NOTIFICATION;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

import common.source.NetworkSource;
import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import context.input.networking.packet.address.PacketAddress;
import networking.NetworkUtils;
import p2pinfiniteworld.event.P2PIWNetworkEvent;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocol;

public class P2PIWReadyToJoinNetworkResponse extends P2PIWNetworkEvent {

	private List<PacketAddress> networkAddresses = new ArrayList<>();

	public P2PIWReadyToJoinNetworkResponse(List<PacketAddress> networkAddresses) {
		super(null);
		this.networkAddresses = networkAddresses;
	}

	public P2PIWReadyToJoinNetworkResponse(NetworkSource source, PacketReader reader) {
		super(source);
		byte[][] ips = reader.readIPv4Array();
		short[] ports = reader.readShortArray();
		for (int i = 0; i < ips.length; i++) {
			networkAddresses().add(NetworkUtils.toAddress(ips[i], ports[i]));
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
				.consume(ips)
				.consume(ports)
				.build();
	}

	@Override
	protected P2PIWNetworkProtocol protocol() {
		return ENTER_WORLD_NOTIFICATION;
	}

	public List<PacketAddress> networkAddresses() {
		return networkAddresses;
	}

}
