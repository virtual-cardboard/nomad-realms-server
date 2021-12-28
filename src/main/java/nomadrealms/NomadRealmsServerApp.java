package nomadrealms;

import context.GameContext;
import context.data.DefaultGameData;
import context.data.GameData;
import context.input.DefaultGameInput;
import context.input.GameInput;
import context.logic.GameLogic;
import context.visuals.GameVisuals;
import engine.GameEngine;
import nomadrealms.context.loading.NomadsServerLoadingLogic;
import nomadrealms.context.loading.NomadsServerLoadingVisuals;

public class NomadRealmsServerApp {

	public static void main(String[] args) {
		GameData data = new DefaultGameData();
		GameInput input = new DefaultGameInput();
		GameLogic logic = new NomadsServerLoadingLogic();
		GameVisuals visuals = new NomadsServerLoadingVisuals();
		GameContext context = new GameContext(data, input, logic, visuals);
		new GameEngine("Nomad Realms Server", context)
				.enableLoading()
				.enableNetworking(45000)
				.enableRendering()
				.enablePrintProgress()
				.windowDimensions(512, 1012)
				.disableResizing()
				.run();
		System.out.println("Started Nomad Realms Server");
	}

}