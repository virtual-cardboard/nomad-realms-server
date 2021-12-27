package nomadrealms.context.loading;

import context.GameContext;
import context.data.GameData;
import context.input.GameInput;
import context.logic.GameLogic;
import context.visuals.GameVisuals;
import nomadrealms.context.server.ServerData;
import nomadrealms.context.server.ServerInput;
import nomadrealms.context.server.ServerLogic;
import nomadrealms.context.server.ServerVisuals;

public class NomadsServerLoadingLogic extends GameLogic {

	private NomadsServerLoadingVisuals serverLoadingVisuals;

	@Override
	protected void init() {
		serverLoadingVisuals = (NomadsServerLoadingVisuals) context().visuals();
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
