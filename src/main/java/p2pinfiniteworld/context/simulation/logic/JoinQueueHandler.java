package p2pinfiniteworld.context.simulation.logic;

import static context.visuals.colour.Colour.rgb;
import static java.lang.Math.random;

import java.util.function.Consumer;

import context.GameContext;
import context.input.networking.packet.address.PacketAddress;
import p2pinfiniteworld.context.simulation.P2PIWServerData;
import p2pinfiniteworld.event.P2PIWJoinQueueRequestEvent;
import p2pinfiniteworld.event.P2PIWJoinQueueSuccessResponseEvent;
import p2pinfiniteworld.model.NomadTiny;

public class JoinQueueHandler implements Consumer<P2PIWJoinQueueRequestEvent> {

	private P2PIWServerData data;
	private GameContext context;

	public JoinQueueHandler(P2PIWServerData data, GameContext context) {
		this.data = data;
		this.context = context;
	}

	@Override
	public void accept(P2PIWJoinQueueRequestEvent event) {
		String username = event.username();
		PacketAddress address = event.source().address();

		if (data.isQueued(address) || data.isInWorld(address)) {
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
