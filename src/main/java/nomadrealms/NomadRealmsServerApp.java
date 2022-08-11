package nomadrealms;

import context.GameContext;
import context.audio.DefaultGameAudio;
import context.audio.GameAudio;
import context.data.GameData;
import context.input.DefaultGameInput;
import context.input.GameInput;
import context.logic.GameLogic;
import context.visuals.GameVisuals;
import engine.GameEngine;
import nomadrealms.context.loading.NomadsServerLoadingData;
import nomadrealms.context.loading.NomadsServerLoadingLogic;
import nomadrealms.context.loading.NomadsServerLoadingVisuals;

public class NomadRealmsServerApp {

	public static void main(String[] args) {
		GameAudio audio = new DefaultGameAudio();
		GameData data = new NomadsServerLoadingData();
		GameInput input = new DefaultGameInput();
		GameLogic logic = new NomadsServerLoadingLogic();
		GameVisuals visuals = new NomadsServerLoadingVisuals();
		GameContext context = new GameContext(audio, data, input, logic, visuals);
		new GameEngine("Nomad Realms Server", context)
				.enableLoading()
				.enableNetworking(45000)
				.enableRendering()
				.disableAudio()
				.enablePrintProgress()
				.windowDimensions(512, 1012)
				.run();
		System.out.println("Started Nomad Realms Server");
	}

}
