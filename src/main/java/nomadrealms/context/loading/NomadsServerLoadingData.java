package nomadrealms.context.loading;

import context.data.GameData;
import context.game.data.DebugTools;
import context.visuals.gui.constraint.dimension.PixelDimensionConstraint;
import context.visuals.gui.constraint.position.PixelPositionConstraint;

public class NomadsServerLoadingData extends GameData {

	private DebugTools tools;

	public DebugTools tools() {
		return tools;
	}

	public void initTools() {
		tools = new DebugTools(resourcePack());
		tools.consoleGui.setPadding(20);
		tools.consoleGui.setFontSize(20);
		tools.consoleGui.setPosY(new PixelPositionConstraint(0));
		tools.consoleGui.setWidth(new PixelDimensionConstraint(512));
		tools.consoleGui.setHeight(new PixelDimensionConstraint(-10));

		tools.logMessage("This is the console gui! Press 'T' to toggle it.", 0xfcba03ff);
	}

}
