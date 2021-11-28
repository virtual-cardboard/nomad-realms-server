package context.loading;

import context.GameContext;
import context.bootstrap.BootstrapServerData;
import context.bootstrap.BootstrapServerInput;
import context.bootstrap.BootstrapServerLogic;
import context.bootstrap.BootstrapServerVisuals;
import context.data.GameData;
import context.input.GameInput;
import context.logic.GameLogic;
import context.visuals.GameVisuals;

public class ServerLoadingLogic extends GameLogic {

	public ServerLoadingLogic() {
	}

	@Override
	public void update() {
		if (((ServerLoadingVisuals) context().visuals()).initialized()) {
			GameData data = new BootstrapServerData();
			GameInput input = new BootstrapServerInput();
			GameLogic logic = new BootstrapServerLogic();
			GameVisuals visuals = new BootstrapServerVisuals();
			GameContext context = new GameContext(data, input, logic, visuals);
			context().transition(context);
		}
	}

}
