package p2pinfiniteworld.context.simulation.logic;

import static context.visuals.colour.Colour.rgb;
import static java.lang.Math.random;

import java.util.function.Consumer;

import context.GameContext;
import context.input.networking.packet.address.PacketAddress;
import p2pinfiniteworld.context.simulation.P2PIWServerData;
import p2pinfiniteworld.context.simulation.P2PIWServerLogic;
import p2pinfiniteworld.event.serverconnect.P2PIWAlreadyInQueueResponseEvent;
import p2pinfiniteworld.event.serverconnect.P2PIWAlreadyInWorldResponseEvent;
import p2pinfiniteworld.event.serverconnect.P2PIWJoinQueueRequestEvent;
import p2pinfiniteworld.event.serverconnect.P2PIWJoinQueueSuccessResponseEvent;
import p2pinfiniteworld.model.NomadTiny;

public class JoinQueueHandler implements Consumer<P2PIWJoinQueueRequestEvent> {

	private P2PIWServerData data;
	private GameContext context;
	private P2PIWServerLogic logic;

	public JoinQueueHandler(P2PIWServerData data, P2PIWServerLogic logic, GameContext context) {
		this.data = data;
		this.logic = logic;
		this.context = context;
	}

	@Override
	public void accept(P2PIWJoinQueueRequestEvent event) {
		String username = event.username();
		PacketAddress address = event.source().address();

		NomadTiny inQueue = data.isQueued(address);
		if (inQueue != null) {
			context.sendPacket(new P2PIWAlreadyInQueueResponseEvent().toPacket(address));
			return;
		}
		NomadTiny inWorld = data.isInWorld(address);
		if (inWorld != null) {
			context.sendPacket(new P2PIWAlreadyInWorldResponseEvent(inWorld.x, inWorld.y, logic.tick0Time()).toPacket(address));
			return;
		}

		System.out.println(username + " requested to join queue");
		data.queuedUsers().add(new NomadTiny(10, 10, address, username, randomColour()));

		context.sendPacket(new P2PIWJoinQueueSuccessResponseEvent().toPacket(address));
	}

	private int randomColour() {
		return rgb((int) (255 * random()), (int) (255 * random()), (int) (255 * random()));
	}

}
