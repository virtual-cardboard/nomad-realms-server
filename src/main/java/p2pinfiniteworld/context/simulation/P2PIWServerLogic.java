package p2pinfiniteworld.context.simulation;

import context.logic.GameLogic;
import p2pinfiniteworld.event.P2PIWJoinQueueRequestEvent;

public class P2PIWServerLogic extends GameLogic {

	@Override
	protected void init() {
		addHandler(P2PIWJoinQueueRequestEvent.class, (e) -> System.out.println(e.username()));
	}

	@Override
	public void update() {
	}

}
