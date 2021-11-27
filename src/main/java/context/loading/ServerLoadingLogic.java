package context.loading;

import context.GameContext;
import context.data.GameData;
import context.input.GameInput;
import context.logic.GameLogic;
import context.singlebootstrap.SingleBootstrapData;
import context.singlebootstrap.SingleBootstrapInput;
import context.singlebootstrap.SingleBootstrapLogic;
import context.singlebootstrap.SingleBootstrapVisuals;
import context.visuals.GameVisuals;

public class ServerLoadingLogic extends GameLogic {

	public ServerLoadingLogic() {
	}

	@Override
	public void update() {
		if (((ServerLoadingVisuals) context().visuals()).initialized()) {
			GameData data = new SingleBootstrapData();
			GameInput input = new SingleBootstrapInput();
			GameLogic logic = new SingleBootstrapLogic();
			GameVisuals visuals = new SingleBootstrapVisuals();
			GameContext context = new GameContext(data, input, logic, visuals);
			context().transition(context);
		}
	}

}
