package context.bootstrap;

import context.bootstrap.visuals.model.NomadMini;
import context.data.GameData;
import context.input.networking.packet.address.PacketAddress;

public class BootstrapServerData extends GameData {

	public boolean gotFirstPacket = false;
	public PacketAddress lan;
	public PacketAddress wan;

	public NomadMini selectedMini;

}
