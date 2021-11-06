package main;

import context.GameContext;
import context.data.GameData;
import context.input.GameInput;
import context.logic.GameLogic;
import context.singlebootstrap.SingleBootstrapData;
import context.singlebootstrap.SingleBootstrapInput;
import context.singlebootstrap.SingleBootstrapLogic;
import context.visuals.DefaultGameVisuals;
import context.visuals.GameVisuals;
import engine.GameEngine;

public class NomadRealmsServerApp {

	public static void main(String[] args) {
		GameData data = new SingleBootstrapData();
		GameInput input = new SingleBootstrapInput();
		GameLogic logic = new SingleBootstrapLogic();
		GameVisuals visuals = new DefaultGameVisuals();
		GameContext context = new GameContext(data, input, logic, visuals);
		new GameEngine("Nomad Realms Server", context)
				.disableLoading()
				.enableNetworking(45000)
				.disableRendering()
				.enablePrintProgress()
				.run();
		System.out.println("Started UDP Holepunch Server");
	}

}
