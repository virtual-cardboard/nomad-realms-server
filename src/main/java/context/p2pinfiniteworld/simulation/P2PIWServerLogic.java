package context.p2pinfiniteworld.simulation;

import static context.visuals.colour.Colour.rgb;
import static java.lang.Math.random;

import java.util.Random;

import common.event.GameEvent;
import context.logic.GameLogic;
import context.nomadrealms.server.visuals.model.NomadMini;
import event.bootstrap.BootstrapRequestEvent;
import event.world.CreateWorldRequestEvent;
import event.world.CreateWorldResponseEvent;
import model.WorldInfo;

public class P2PIWServerLogic extends GameLogic {

	@Override
	protected void init() {
		P2PIWServerData data = (P2PIWServerData) context().data();
		addHandler(GameEvent.class, event -> {
			throw new RuntimeException("Was expecting Bootstrap Request");
		});
		addHandler(BootstrapRequestEvent.class, (event) -> {
			int rgb = rgb((int) (255 * random()), (int) (255 * random()), (int) (255 * random()));
			data.minis().add(new NomadMini(rgb, event.username(), event.lanAddress(), event.source().address()));
			System.out.println(event.lanAddress());
		});
		Random rand = new Random();
		addHandler(CreateWorldRequestEvent.class, (event) -> {
			WorldInfo worldInfo = new WorldInfo();
			worldInfo.name = event.worldName();
			worldInfo.seed = rand.nextLong();
			data.worldInfos().add(worldInfo);
			context().sendPacket(new CreateWorldResponseEvent(worldInfo.seed).toPacket(event.source().address()));
		});
	}

	@Override
	public void update() {
	}

}
