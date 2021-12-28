package p2pinfiniteworld.context.simulation;

import static p2pinfiniteworld.context.simulation.P2PIWServerVisuals.GRID_SQUARE_SIZE;
import static p2pinfiniteworld.context.simulation.P2PIWServerVisuals.GRID_START_X;

import common.event.GameEvent;
import context.input.GameInput;
import context.input.event.MousePressedInputEvent;
import p2pinfiniteworld.model.NomadTiny;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocolDecoder;

public class P2PIWServerInput extends GameInput {

	private P2PIWServerData data;

	@Override
	protected void init() {
		data = (P2PIWServerData) context().data();
		addPacketReceivedFunction(new P2PIWNetworkProtocolDecoder());
		addMousePressedFunction(this::handleMousePressed);
	}

	private GameEvent handleMousePressed(MousePressedInputEvent event) {
		data.setSelectedNomad(null);
		int i = 0;
		for (NomadTiny nomad : data.queuedUsers()) {
			int left = 0;
			int right = GRID_START_X;
			int top = i * 2 * GRID_SQUARE_SIZE;
			int bottom = top + 2 * GRID_SQUARE_SIZE;
			if (cursor().pos().x > left && cursor().pos().x < right && cursor().pos().y > top && cursor().pos().y < bottom) {
				data.setSelectedNomad(nomad);
				break;
			}
			i++;
		}
		return null;
	}

}
