package p2pinfiniteworld.context.simulation;

import static context.visuals.colour.Colour.rgb;
import static java.lang.Math.random;

import context.input.networking.packet.address.PacketAddress;
import context.logic.GameLogic;
import p2pinfiniteworld.event.P2PIWJoinQueueRequestEvent;
import p2pinfiniteworld.event.P2PIWJoinQueueSuccessResponseEvent;
import p2pinfiniteworld.model.NomadTiny;

public class P2PIWServerLogic extends GameLogic {

	private P2PIWServerData data;

	@Override
	protected void init() {
		data = (P2PIWServerData) context().data();
		addHandler(P2PIWJoinQueueRequestEvent.class, this::handleJoinQueue);
	}

	private void handleJoinQueue(P2PIWJoinQueueRequestEvent event) {
		String username = event.username();
		PacketAddress address = event.source().address();

		System.out.println(username + " requested to join queue");
		data.queuedUsers().add(new NomadTiny(10, 10, address, username, randomColour()));

		context().sendPacket(new P2PIWJoinQueueSuccessResponseEvent().toPacket(address));
	}

	private int randomColour() {
		return rgb((int) (255 * random()), (int) (255 * random()), (int) (255 * random()));
	}

	@Override
	public void update() {
	}

}
