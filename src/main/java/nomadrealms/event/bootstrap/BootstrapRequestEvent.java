package nomadrealms.event.bootstrap;

import static networking.NetworkUtils.toAddress;
import static nomadrealms.protocols.NomadRealmsServerProtocol.BOOTSTRAP_REQUEST;

import common.source.NetworkSource;
import context.input.networking.packet.PacketBuilder;
import context.input.networking.packet.PacketModel;
import context.input.networking.packet.PacketReader;
import context.input.networking.packet.address.PacketAddress;
import nomadrealms.event.NomadRealmsServerGameEvent;
import nomadrealms.protocols.NomadRealmsServerProtocol;

public class BootstrapRequestEvent extends NomadRealmsServerGameEvent {

	private PacketAddress lanAddress;
	private String username;

	public BootstrapRequestEvent(NetworkSource source, long timestamp, PacketAddress lanAddress, String username) {
		super(timestamp, source);
		this.lanAddress = lanAddress;
		this.username = username;
	}

	public BootstrapRequestEvent(PacketAddress lanAddress, String username) {
		super(null);
		this.lanAddress = lanAddress;
		this.username = username;
	}

	public BootstrapRequestEvent(NetworkSource source, PacketReader reader) {
		super(source);
		setTime(reader.readLong());
		this.lanAddress = toAddress(reader.readIPv4(), reader.readShort());
		this.username = reader.readString();
		reader.close();
	}

	public PacketAddress lanAddress() {
		return lanAddress;
	}

	public String username() {
		return username;
	}

	@Override
	protected PacketModel toPacketModel(PacketBuilder builder) {
		return builder
				.consume(time())
				.consume(lanAddress.ip())
				.consume(lanAddress.shortPort())
				.consume(username)
				.build();
	}

	@Override
	protected NomadRealmsServerProtocol protocol() {
		return BOOTSTRAP_REQUEST;
	}

}
