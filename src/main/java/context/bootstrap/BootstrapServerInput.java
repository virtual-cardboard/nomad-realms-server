package context.bootstrap;

import java.util.List;
import java.util.Random;

import common.math.Vector2f;
import context.bootstrap.visuals.model.NomadMini;
import context.input.GameInput;
import event.bootstrap.BootstrapResponseEvent;
import networking.protocols.NomadRealmsServerProtocolDecoder;

public class BootstrapServerInput extends GameInput {

	@Override
	protected void init() {
		BootstrapServerData data = (BootstrapServerData) context().data();
		addMousePressedFunction(event -> true, event -> {
			List<NomadMini> minis = data.minis();
			for (int i = minis.size() - 1; i >= 0; i--) {
				NomadMini nomadMini = minis.get(i);
				if (cursor().pos().toVec2f().add(-32, -32).sub(nomadMini.pos()).lengthSquared() <= 30 * 30) {
					nomadMini.drag();
					data.selectedMini = nomadMini;
					data.selectedMini.setPos(cursor().pos().toVec2f().sub(new Vector2f(32, 32)));
					System.out.println(data.selectedMini.username());
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
			NomadMini n1 = data.selectedMini;
			n1.undrag();
			List<NomadMini> minis = data.minis();
			for (int i = minis.size() - 1; i >= 0; i--) {
				NomadMini n2 = minis.get(i);
				if (n2 != n1 && n1.pos().sub(n2.pos()).lengthSquared() <= 30 * 30) {
					System.out.println("Match!");
					minis.remove(n1);
					minis.remove(n2);
					long nonce = new Random().nextLong();
					context().sendPacket(new BootstrapResponseEvent(nonce, n1.lanAddress(), n1.wanAddress(), n1.username()).toPacket(n2.wanAddress()));
					context().sendPacket(new BootstrapResponseEvent(nonce, n2.lanAddress(), n2.wanAddress(), n2.username()).toPacket(n1.wanAddress()));
					break;
				}
			}
			data.selectedMini = null;
			return null;
		}, false);
		addPacketReceivedFunction(new NomadRealmsServerProtocolDecoder());
	}

}
