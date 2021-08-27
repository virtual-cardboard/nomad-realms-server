package context;

import context.data.GameData;
import context.input.networking.packet.address.PacketAddress;

public class BootstrapData extends GameData {

	public boolean gotFirstPacket = false;
	public PacketAddress lan;
	public PacketAddress wan;

}
