package context.loading;

import context.GameContext;
import context.data.GameData;
import context.input.GameInput;
import context.logic.GameLogic;
import context.server.ServerData;
import context.server.ServerInput;
import context.server.ServerLogic;
import context.server.ServerVisuals;
import context.visuals.GameVisuals;

public class ServerLoadingLogic extends GameLogic {

	public ServerLoadingLogic() {
	}

	@Override
	protected void init() {

	}

	@Override
	public void update() {
		if (((ServerLoadingVisuals) context().visuals()).initialized()) {
			GameData data = new ServerData();
			GameInput input = new ServerInput();
			GameLogic logic = new ServerLogic();
			GameVisuals visuals = new ServerVisuals();
			GameContext context = new GameContext(data, input, logic, visuals);
			context().transition(context);
		}
	}

}
