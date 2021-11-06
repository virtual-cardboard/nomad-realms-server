package context.singlebootstrap;

import context.data.GameData;
import context.input.networking.packet.address.PacketAddress;

public class SingleBootstrapData extends GameData {

	public boolean gotFirstPacket = false;
	public PacketAddress lan;
	public PacketAddress wan;

}
