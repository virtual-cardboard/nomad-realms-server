package p2pinfiniteworld.context.simulation;

import static context.visuals.colour.Colour.rgb;
import static java.lang.Math.floorDiv;
import static java.lang.Math.random;
import static p2pinfiniteworld.context.simulation.P2PIWServerVisuals.CHUNK_PIXEL_SIZE;
import static p2pinfiniteworld.context.simulation.P2PIWServerVisuals.GRID_START_X;
import static p2pinfiniteworld.context.simulation.P2PIWServerVisuals.GRID_START_Y;
import static p2pinfiniteworld.context.simulation.visuals.LogMessagesRenderer.LOG_MESSAGES_BAR_HEIGHT;
import static p2pinfiniteworld.context.simulation.visuals.LogMessagesRenderer.LOG_MESSAGES_RIGHT_PADDING;
import static p2pinfiniteworld.context.simulation.visuals.LogMessagesRenderer.LOG_MESSAGES_WIDTH;

import context.input.GameInput;
import context.input.event.MouseMovedInputEvent;
import context.input.event.MousePressedInputEvent;
import context.input.event.MouseReleasedInputEvent;
import engine.common.event.GameEvent;
import engine.common.math.PosDim;
import engine.common.math.Vector2i;
import org.lwjgl.glfw.GLFW;
import p2pinfiniteworld.context.simulation.visuals.LogMessagesRenderer;
import p2pinfiniteworld.model.NomadTiny;
import p2pinfiniteworld.protocols.P2PIWNetworkProtocolDecoder;

public class P2PIWServerInput extends GameInput {

	private P2PIWServerData data;
	private P2PIWServerVisuals visuals;

	private Vector2i previousCursorPos;
	private boolean pressed = false;

	@Override
	protected void init() {
		data = (P2PIWServerData) context().data();
		visuals = (P2PIWServerVisuals) context().visuals();
		addPacketReceivedFunction(new P2PIWNetworkProtocolDecoder());
		addMousePressedFunction(this::handleMousePressed);
		addMouseReleasedFunction(this::handleMouseReleased);
		addMouseMovedFunction((e) -> pressed, this::handleMouseMoved, true);
		addKeyPressedFunction((e) -> e.code() == GLFW.GLFW_KEY_Q, (e) -> {
			NomadTiny newNomad = new NomadTiny(0, 0, null, "Bob", randomColour());
			data.queuedUsers().add(newNomad);
			data.setSelectedQueueNomad(newNomad);
			return null;
		}, true);
	}

	private int randomColour() {
		return rgb((int) (255 * random()), (int) (255 * random()), (int) (255 * random()));
	}

	private GameEvent handleMousePressed(MousePressedInputEvent event) {
		LogMessagesRenderer logRenderer = visuals.logMessagesRenderer();
		if (cursor().pos().y > logRenderer.logMessagesY
				&& cursor().pos().x > visuals.rootGui().dimensions().x - LOG_MESSAGES_WIDTH - LOG_MESSAGES_RIGHT_PADDING
				&& cursor().pos().x < visuals.rootGui().dimensions().x - LOG_MESSAGES_RIGHT_PADDING) {
			if (cursor().pos().y < logRenderer.logMessagesY + LOG_MESSAGES_BAR_HEIGHT) {
				visuals.logOpen = !visuals.logOpen;
			}
		} else if (cursor().pos().y < GRID_START_Y) {
		} else if (cursor().pos().x < GRID_START_X) {
			handleQueuePressed();
		} else {
			pressed = true;
			previousCursorPos = cursor().pos();
			handleWorldPressed();
		}
		return null;
	}

	private void handleWorldPressed() {
		int worldClickX = cursor().pos().x - GRID_START_X - visuals.gridOffset.x;
		int worldClickY = cursor().pos().y - GRID_START_Y - visuals.gridOffset.y;
		if (data.selectedQueueNomad() != null) {
			int chunkX = floorDiv(worldClickX, CHUNK_PIXEL_SIZE);
			int chunkY = floorDiv(worldClickY, CHUNK_PIXEL_SIZE);
			data.queuedUsers().remove(data.selectedQueueNomad());
			data.nomads().add(data.selectedQueueNomad());
			data.selectedQueueNomad().x = chunkX;
			data.selectedQueueNomad().y = chunkY;
			if (data.selectedQueueNomad().address() != null) {
				context().sendPacket(new P2PIWReadyToJoinNetworkResponse(chunkX, chunkY, data.addressList()).toPacketModel(data.selectedQueueNomad().address()));
			} else {
				System.out.println("Added in nomad without address");
			}
			data.setSelectedQueueNomad(null);
		} else {
			for (NomadTiny nomad : data.nomads()) {
				int left = nomad.x * CHUNK_PIXEL_SIZE;
				int right = left + CHUNK_PIXEL_SIZE;
				int top = nomad.y * CHUNK_PIXEL_SIZE;
				int bottom = top + CHUNK_PIXEL_SIZE;
				if (worldClickX > left && worldClickX < right && worldClickY > top && worldClickY < bottom) {
					data.setSelectedWorldNomad(nomad);
				}
			}
		}
	}

	private void handleQueuePressed() {
		data.setSelectedQueueNomad(null);
		int i = 0;
		for (NomadTiny nomad : data.queuedUsers()) {
			PosDim posdim = visuals.queuedRectangle(i);
			if (posdim.contains(cursor().pos())) {
				data.setSelectedQueueNomad(nomad);
				return;
			}
			i++;
		}
	}

	private GameEvent handleMouseReleased(MouseReleasedInputEvent mousereleasedinputevent1) {
		pressed = false;
		return null;
	}

	private GameEvent handleMouseMoved(MouseMovedInputEvent event) {
		visuals.gridOffset = visuals.gridOffset.sub(previousCursorPos).add(cursor().pos());
		previousCursorPos = cursor().pos();
		return null;
	}

}
