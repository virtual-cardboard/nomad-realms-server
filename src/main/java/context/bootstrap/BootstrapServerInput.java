package context.bootstrap;

import java.util.List;

import common.math.Vector2f;
import context.bootstrap.visuals.model.NomadMini;
import context.input.GameInput;

public class BootstrapServerInput extends GameInput {

	@Override
	protected void init() {
		BootstrapServerVisuals visuals = (BootstrapServerVisuals) context().visuals();
		BootstrapServerData data = (BootstrapServerData) context().data();
		addMousePressedFunction(event -> true, event -> {
			List<NomadMini> minis = visuals.minis;
			for (int i = minis.size() - 1; i >= 0; i--) {
				NomadMini nomadMini = minis.get(i);
				if (cursor().pos().toVec2f().add(-32, -32).sub(nomadMini.pos()).lengthSquared() <= 30 * 30) {
					nomadMini.drag();
					data.selectedMini = nomadMini;
					data.selectedMini.setPos(cursor().pos().toVec2f().sub(new Vector2f(32, 32)));
					break;
				}
			}
			return null;
		}, false);
		addMouseMovedFunction(event -> data.selectedMini != null, event -> {
			data.selectedMini.setPos(cursor().pos().toVec2f().sub(new Vector2f(32, 32)));
			return null;
		}, false);
		addMouseReleasedFunction(event -> data.selectedMini != null && event.button() == 0, event -> {
			NomadMini selectedMini = data.selectedMini;
			selectedMini.undrag();
			data.selectedMini = null;
			return null;
		}, false);
	}

}
