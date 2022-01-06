package p2pinfiniteworld.context.simulation.visuals;

import static context.visuals.colour.Colour.rgb;
import static p2pinfiniteworld.context.simulation.P2PIWServerVisuals.BACKGROUND_COLOUR;
import static p2pinfiniteworld.context.simulation.P2PIWServerVisuals.DARK_BACKGROUND_COLOUR;
import static p2pinfiniteworld.context.simulation.P2PIWServerVisuals.GRID_START_Y;

import common.math.Vector2i;
import context.GLContext;
import context.visuals.builtin.RectangleRenderer;
import context.visuals.renderer.GameRenderer;
import context.visuals.renderer.TextRenderer;
import context.visuals.text.GameFont;
import p2pinfiniteworld.context.simulation.P2PIWServerData;

public class LogMessagesRenderer extends GameRenderer {

	public static final int LOG_MESSAGES_INE_HEIGHT = 30;
	public static final int LOG_MESSAGES_BAR_HEIGHT = 50;
	public static final int LOG_MESSAGES_WIDTH = 600;
	public static final int LOG_MESSAGES_RIGHT_PADDING = 20;
	public static final int OPEN_MESSAGES_Y = GRID_START_Y + 50;
	public static final int WHITE = rgb(255, 255, 255);

	public Vector2i gridOffset = new Vector2i();
	public float logMessagesY = OPEN_MESSAGES_Y;

	private P2PIWServerData data;
	private RectangleRenderer rectangleRenderer;
	private TextRenderer textRenderer;
	private GameFont baloo2;

	public LogMessagesRenderer(GLContext glContext, P2PIWServerData data, RectangleRenderer rectangleRenderer, TextRenderer textRenderer, GameFont baloo2) {
		super(glContext);
		this.data = data;
		this.rectangleRenderer = rectangleRenderer;
		this.textRenderer = textRenderer;
		this.baloo2 = baloo2;
	}

	public void render(boolean logOpen) {
		int padding = 8;
		rectangleRenderer.render(width() - LOG_MESSAGES_WIDTH - LOG_MESSAGES_RIGHT_PADDING,
				logMessagesY,
				LOG_MESSAGES_WIDTH,
				height() - logMessagesY,
				DARK_BACKGROUND_COLOUR);
		rectangleRenderer.render(width() - LOG_MESSAGES_WIDTH - LOG_MESSAGES_RIGHT_PADDING + padding,
				logMessagesY + LOG_MESSAGES_BAR_HEIGHT,
				LOG_MESSAGES_WIDTH - 2 * padding,
				height() - logMessagesY - LOG_MESSAGES_BAR_HEIGHT,
				BACKGROUND_COLOUR);
		int i = 0;
		while (i < data.logMessages().size() && messageYPosition(i) > logMessagesY + LOG_MESSAGES_BAR_HEIGHT - LOG_MESSAGES_INE_HEIGHT) {
			textRenderer.render(
					(int) width() - LOG_MESSAGES_WIDTH - LOG_MESSAGES_RIGHT_PADDING + 10,
					messageYPosition(i),
					data.logMessages().get(i), 100000, baloo2, 24, rgb(0, 0, 0));
			i++;
		}
		rectangleRenderer.render(width() - LOG_MESSAGES_WIDTH - LOG_MESSAGES_RIGHT_PADDING,
				logMessagesY,
				LOG_MESSAGES_WIDTH,
				LOG_MESSAGES_BAR_HEIGHT,
				DARK_BACKGROUND_COLOUR);
		textRenderer.render((int) width() - LOG_MESSAGES_WIDTH - LOG_MESSAGES_RIGHT_PADDING + 10, (int) logMessagesY,
				"Log", 100, baloo2, LOG_MESSAGES_INE_HEIGHT, WHITE);

		float target = 0;
		if (logOpen) {
			target = OPEN_MESSAGES_Y;
		} else {
			target = height() - LOG_MESSAGES_BAR_HEIGHT;
		}
		logMessagesY = logMessagesY + (target - logMessagesY) * 0.5f;
	}

	private int messageYPosition(int i) {
		return (int) (height() + logMessagesY - OPEN_MESSAGES_Y - 28 * (i + 1) - 12);
	}

}
