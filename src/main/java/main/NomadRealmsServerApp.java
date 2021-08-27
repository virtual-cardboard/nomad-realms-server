package main;

import context.BootstrapData;
import context.BootstrapInput;
import context.BootstrapLogic;
import context.GameContext;
import context.data.GameData;
import context.input.GameInput;
import context.logic.GameLogic;
import context.visuals.DefaultGameVisuals;
import context.visuals.GameVisuals;
import engine.GameEngine;

public class NomadRealmsServerApp {

	public static void main(String[] args) {
		GameData data = new BootstrapData();
		GameInput input = new BootstrapInput();
		GameLogic logic = new BootstrapLogic();
		GameVisuals visuals = new DefaultGameVisuals();
		GameContext context = new GameContext(data, input, logic, visuals);
		new GameEngine("Nomad Realms Server", context)
				.disableLoading()
				.enableNetworking(45000)
				.disableRendering()
				.enablePrintProgress()
				.run();
		System.out.println("Started STUN Server");
	}

}
