package p2pinfiniteworld.context.simulation.logic;

import java.util.function.Consumer;

import context.GameContext;
import engine.common.networking.packet.address.PacketAddress;
import p2pinfiniteworld.context.simulation.P2PIWServerData;
import p2pinfiniteworld.context.simulation.P2PIWServerLogic;
import p2pinfiniteworld.model.NomadTiny;

public class CheckStatusHandler implements Consumer<P2PIWCheckNomadStatusRequestEvent> {

	private P2PIWServerData data;
	private GameContext context;
	private P2PIWServerLogic logic;

	public CheckStatusHandler(P2PIWServerData data, P2PIWServerLogic logic, GameContext context) {
		this.data = data;
		this.logic = logic;
		this.context = context;
	}

	@Override
	public void accept(P2PIWCheckNomadStatusRequestEvent event) {
		PacketAddress address = event.source().address();

		System.out.println(address + " is checking status");

		NomadTiny inQueue = data.isQueued(address);
		if (inQueue != null) {
			context.sendPacket(new P2PIWAlreadyInQueueResponseEvent(logic.tick0Time()).toPacketModel(address));
			return;
		}
		NomadTiny inWorld = data.isInWorld(address);
		if (inWorld != null) {
			context.sendPacket(new P2PIWAlreadyInWorldResponseEvent(inWorld.x, inWorld.y, logic.tick0Time()).toPacketModel(address));
			return;
		}
		System.out.println(address + " tried to check status without joining queue first.");
	}

}
