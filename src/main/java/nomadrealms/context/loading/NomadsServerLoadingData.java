package nomadrealms.context.loading;

import context.data.GameData;
import context.game.data.Tools;
import context.visuals.gui.constraint.dimension.PixelDimensionConstraint;
import context.visuals.gui.constraint.position.PixelPositionConstraint;

public class NomadsServerLoadingData extends GameData {

	private Tools tools;

	public Tools tools() {
		return tools;
	}

	public void initTools() {
		tools = new Tools(resourcePack());
		tools.consoleGui.setPadding(20);
		tools.consoleGui.setFontSize(20);
		tools.consoleGui.setPosY(new PixelPositionConstraint(0));
		tools.consoleGui.setWidth(new PixelDimensionConstraint(512));
		tools.consoleGui.setHeight(new PixelDimensionConstraint(-10));

		tools.logMessage("This is the console gui! Press 'T' to toggle it.", 0xfcba03ff);
	}

}
