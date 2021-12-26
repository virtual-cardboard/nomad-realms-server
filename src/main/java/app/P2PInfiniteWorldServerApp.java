package app;

import context.GameContext;
import context.data.DefaultGameData;
import context.data.GameData;
import context.input.DefaultGameInput;
import context.input.GameInput;
import context.logic.GameLogic;
import context.p2pinfiniteworld.loading.P2PIWServerLoadingLogic;
import context.p2pinfiniteworld.loading.P2PIWServerLoadingVisuals;
import context.visuals.GameVisuals;
import engine.GameEngine;

public class P2PInfiniteWorldServerApp {

	public static void main(String[] args) {
		GameData data = new DefaultGameData();
		GameInput input = new DefaultGameInput();
		GameLogic logic = new P2PIWServerLoadingLogic();
		GameVisuals visuals = new P2PIWServerLoadingVisuals();
		GameContext context = new GameContext(data, input, logic, visuals);
		new GameEngine("P2P Infinite World Server", context)
				.enableLoading()
				.enableNetworking(45000)
				.enableRendering()
				.enablePrintProgress()
				.windowDimensions(32 * 50, 32 * 30)
				.run();
		System.out.println("Started P2P Infinite World Server");
	}

}
