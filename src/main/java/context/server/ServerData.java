package context.server;

import java.util.ArrayList;
import java.util.List;

import context.data.GameData;
import context.input.networking.packet.address.PacketAddress;
import context.server.visuals.model.NomadMini;
import model.WorldInfo;

public class ServerData extends GameData {

	public boolean gotFirstPacket = false;
	public PacketAddress lan;
	public PacketAddress wan;

	private List<WorldInfo> worldInfos = new ArrayList<>();
	private List<NomadMini> minis = new ArrayList<>();

	public NomadMini selectedMini;

	public List<NomadMini> minis() {
		return minis;
	}

	public List<WorldInfo> worldInfos() {
		return worldInfos;
	}

}
