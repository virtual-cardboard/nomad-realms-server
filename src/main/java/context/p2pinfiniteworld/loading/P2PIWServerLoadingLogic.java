package context.p2pinfiniteworld.loading;

import context.GameContext;
import context.data.GameData;
import context.input.GameInput;
import context.logic.GameLogic;
import context.p2pinfiniteworld.simulation.P2PIWServerData;
import context.p2pinfiniteworld.simulation.P2PIWServerInput;
import context.p2pinfiniteworld.simulation.P2PIWServerLogic;
import context.p2pinfiniteworld.simulation.P2PIWServerVisuals;
import context.visuals.GameVisuals;

public class P2PIWServerLoadingLogic extends GameLogic {

	private P2PIWServerLoadingVisuals serverLoadingVisuals;

	@Override
	protected void init() {
		serverLoadingVisuals = (P2PIWServerLoadingVisuals) context().visuals();
	}

	@Override
	public void update() {
		if (serverLoadingVisuals.initialized()) {
			GameData data = new P2PIWServerData();
			GameInput input = new P2PIWServerInput();
			GameLogic logic = new P2PIWServerLogic();
			GameVisuals visuals = new P2PIWServerVisuals();
			GameContext context = new GameContext(data, input, logic, visuals);
			context().transition(context);
		}
	}

}
