package event;

import common.event.GameEvent;
import common.source.NetworkSource;
import context.input.networking.packet.address.PacketAddress;

public class BootstrapRequestEvent extends GameEvent {

	private static final long serialVersionUID = 7878990990289757559L;

	private long timestamp;
	private PacketAddress lan;

	public BootstrapRequestEvent(NetworkSource source, long timestamp, PacketAddress lan) {
		super(source);
		this.timestamp = timestamp;
		this.lan = lan;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public PacketAddress getLan() {
		return lan;
	}

	@Override
	public NetworkSource source() {
		return (NetworkSource) super.source();
	}

}