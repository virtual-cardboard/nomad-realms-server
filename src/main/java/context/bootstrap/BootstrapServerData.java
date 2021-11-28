package context.bootstrap;

import java.util.ArrayList;
import java.util.List;

import context.bootstrap.visuals.model.NomadMini;
import context.data.GameData;
import context.input.networking.packet.address.PacketAddress;

public class BootstrapServerData extends GameData {

	public boolean gotFirstPacket = false;
	public PacketAddress lan;
	public PacketAddress wan;

	private List<NomadMini> minis = new ArrayList<>();

	public NomadMini selectedMini;

	public List<NomadMini> minis() {
		return minis;
	}

}
