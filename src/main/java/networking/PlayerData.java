package networking;

import engine.common.networking.packet.address.PacketAddress;

public final class PlayerData {

	public final PacketAddress wanAddress;
	public final PacketAddress lanAddress;
	public final String username;

	public PlayerData(PacketAddress wanAddress, PacketAddress lanAddress, String username) {
		this.wanAddress = wanAddress;
		this.lanAddress = lanAddress;
		this.username = username;
	}

}
