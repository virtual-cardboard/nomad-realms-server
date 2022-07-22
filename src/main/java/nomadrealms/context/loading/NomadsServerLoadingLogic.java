package nomadrealms.context.loading;

import context.GameContext;
import context.audio.DefaultGameAudio;
import context.audio.GameAudio;
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
			GameAudio audio = new DefaultGameAudio();
			GameData data = new ServerData(((NomadsServerLoadingData) context().data()).tools());
			GameInput input = new ServerInput();
			GameLogic logic = new ServerLogic();
			GameVisuals visuals = new ServerVisuals();
			GameContext context = new GameContext(audio, data, input, logic, visuals);
			context().transition(context);
		}
	}

}
