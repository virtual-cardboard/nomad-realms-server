package app;

import context.GameContext;
import context.data.DefaultGameData;
import context.data.GameData;
import context.input.DefaultGameInput;
import context.input.GameInput;
import context.logic.GameLogic;
import context.nomadrealms.loading.NomadsServerLoadingLogic;
import context.nomadrealms.loading.ServerLoadingVisuals;
import context.visuals.GameVisuals;
import engine.GameEngine;

public class NomadRealmsServerApp {

	public static void main(String[] args) {
		GameData data = new DefaultGameData();
		GameInput input = new DefaultGameInput();
		GameLogic logic = new NomadsServerLoadingLogic();
		GameVisuals visuals = new ServerLoadingVisuals();
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
