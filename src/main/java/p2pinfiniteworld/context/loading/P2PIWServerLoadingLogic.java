package p2pinfiniteworld.context.loading;

import context.GameContext;
import context.audio.DefaultGameAudio;
import context.audio.GameAudio;
import context.data.GameData;
import context.input.GameInput;
import context.logic.GameLogic;
import context.visuals.GameVisuals;
import p2pinfiniteworld.context.simulation.P2PIWServerData;
import p2pinfiniteworld.context.simulation.P2PIWServerInput;
import p2pinfiniteworld.context.simulation.P2PIWServerLogic;
import p2pinfiniteworld.context.simulation.P2PIWServerVisuals;

public class P2PIWServerLoadingLogic extends GameLogic {

	private P2PIWServerLoadingVisuals serverLoadingVisuals;

	@Override
	protected void init() {
		serverLoadingVisuals = (P2PIWServerLoadingVisuals) context().visuals();
	}

	@Override
	public void update() {
		if (serverLoadingVisuals.initialized()) {
			GameAudio audio = new DefaultGameAudio();
			GameData data = new P2PIWServerData();
			GameInput input = new P2PIWServerInput();
			GameLogic logic = new P2PIWServerLogic();
			GameVisuals visuals = new P2PIWServerVisuals();
			GameContext context = new GameContext(audio, data, input, logic, visuals);
			context().transition(context);
		}
	}

}
