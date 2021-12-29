package p2pinfiniteworld.context.simulation;

import static p2pinfiniteworld.context.simulation.P2PIWServerVisuals.CHUNK_PIXEL_SIZE;
import static p2pinfiniteworld.context.simulation.P2PIWServerVisuals.GRID_START_X;

import common.event.GameEvent;
import context.input.GameInput;
import context.input.event.MousePressedInputEvent;
import p2pinfiniteworld.event.P2PIWEnterWorldNotificationEvent;
import p2pinfiniteworld.model.NomadTiny;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocolDecoder;

public class P2PIWServerInput extends GameInput {

	private P2PIWServerData data;
	private P2PIWServerLogic logic;

	@Override
	protected void init() {
		logic = (P2PIWServerLogic) context().logic();
		data = (P2PIWServerData) context().data();
		addPacketReceivedFunction(new P2PIWNetworkProtocolDecoder());
		addMousePressedFunction(this::handleMousePressed);
	}

	private GameEvent handleMousePressed(MousePressedInputEvent event) {
		if (cursor().pos().x < GRID_START_X) {
			data.setSelectedNomad(null);
			int i = 0;
			for (NomadTiny nomad : data.queuedUsers()) {
				int left = 0;
				int right = GRID_START_X;
				int top = i * 2 * CHUNK_PIXEL_SIZE;
				int bottom = top + 2 * CHUNK_PIXEL_SIZE;
				if (cursor().pos().x > left && cursor().pos().x < right && cursor().pos().y > top && cursor().pos().y < bottom) {
					data.setSelectedNomad(nomad);
					return null;
				}
				i++;
			}
		}
		if (data.selectedNomad() != null) {
			int chunkX = (cursor().pos().x - GRID_START_X) / CHUNK_PIXEL_SIZE;
			int chunkY = cursor().pos().y / CHUNK_PIXEL_SIZE;
			data.queuedUsers().remove(data.selectedNomad());
			data.nomads().add(data.selectedNomad());
			data.selectedNomad().x = chunkX;
			data.selectedNomad().y = chunkY;
			context().sendPacket(new P2PIWEnterWorldNotificationEvent(chunkX, chunkY, logic.tick0Time()).toPacket(data.selectedNomad().address()));
			data.setSelectedNomad(null);
		} else {
			for (NomadTiny nomad : data.nomads()) {
				int left = GRID_START_X + nomad.x * CHUNK_PIXEL_SIZE;
				int right = left + CHUNK_PIXEL_SIZE;
				int top = nomad.y * CHUNK_PIXEL_SIZE;
				int bottom = top + CHUNK_PIXEL_SIZE;
				if (cursor().pos().x > left && cursor().pos().x < right && cursor().pos().y > top && cursor().pos().y < bottom) {
					data.setSelectedNomad(nomad);
					return null;
				}
			}
		}
		return null;
	}

}
