package context.bootstrap;

import static context.visuals.colour.Colour.rgb;
import static java.lang.Math.random;

import common.event.GameEvent;
import context.bootstrap.visuals.model.NomadMini;
import context.logic.GameLogic;
import event.bootstrap.BootstrapRequestEvent;

public class BootstrapServerLogic extends GameLogic {

	@Override
	protected void init() {
		BootstrapServerData data = (BootstrapServerData) context().data();
		addHandler(GameEvent.class, event -> {
			throw new RuntimeException("Was expecting Bootstrap Request");
		});
		addHandler(BootstrapRequestEvent.class, (event) -> {
			int rgb = rgb((int) (255 * random()), (int) (255 * random()), (int) (255 * random()));
			data.minis().add(new NomadMini(rgb, event.username(), event.lanAddress(), event.source().address()));
			System.out.println(event.lanAddress());
		});
	}

	@Override
	public void update() {
	}

}
