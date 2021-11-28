package context.bootstrap;

import java.util.Random;

import common.event.GameEvent;
import context.logic.GameLogic;

public class BootstrapServerLogic extends GameLogic {

	private long nonce = new Random().nextLong();

	@Override
	protected void init() {
		addHandler(GameEvent.class, event -> {
			throw new RuntimeException("Was expecting Bootstrap Request");
		});
	}

	@Override
	public void update() {
	}

}
