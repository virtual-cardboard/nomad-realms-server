package context.nomadrealms.loading;

import context.GameContext;
import context.data.GameData;
import context.input.GameInput;
import context.logic.GameLogic;
import context.nomadrealms.server.ServerData;
import context.nomadrealms.server.ServerInput;
import context.nomadrealms.server.ServerLogic;
import context.nomadrealms.server.ServerVisuals;
import context.visuals.GameVisuals;

public class NomadsServerLoadingLogic extends GameLogic {

	private ServerLoadingVisuals serverLoadingVisuals;

	@Override
	protected void init() {
		serverLoadingVisuals = (ServerLoadingVisuals) context().visuals();
	}

	@Override
	public void update() {
		if (serverLoadingVisuals.initialized()) {
			GameData data = new ServerData();
			GameInput input = new ServerInput();
			GameLogic logic = new ServerLogic();
			GameVisuals visuals = new ServerVisuals();
			GameContext context = new GameContext(data, input, logic, visuals);
			context().transition(context);
		}
	}

}
