package p2pinfiniteworld.context.simulation;

import static context.visuals.colour.Colour.rgb;
import static context.visuals.colour.Colour.rgba;
import static p2pinfiniteworld.context.simulation.P2PIWServerData.REGION_NUM_CHUNKS;

import common.math.Vector2i;
import context.visuals.GameVisuals;
import context.visuals.builtin.RectangleRenderer;
import context.visuals.lwjgl.Texture;
import context.visuals.renderer.LineRenderer;
import context.visuals.renderer.TextRenderer;
import context.visuals.text.GameFont;
import p2pinfiniteworld.graphics.DiffuseTextureRenderer;
import p2pinfiniteworld.model.NomadTiny;
import p2pinfiniteworld.model.P2PIWChunk;
import p2pinfiniteworld.model.P2PIWRegion;

public class P2PIWServerVisuals extends GameVisuals {

	public static final int GRID_SQUARE_SIZE = 32;
	public static final int REGION_SQUARE_SIZE = GRID_SQUARE_SIZE * 4;
	public static final int GRID_START_X = 96;
	private static final int GRID_START_Y = 0;

	public static final int NEARBY_CHUNKS_COLOUR = rgba(104, 166, 68, 128);
	public static final int CHUNK_BORDER_COLOUR = rgb(111, 115, 122);
	public static final int REGION_BORDER_COLOUR = rgb(255, 255, 255);

	public static final int BACKGROUND_COLOUR = rgb(54, 57, 63);
	public static final int DARK_BACKGROUND_COLOUR = rgb(47, 49, 54);

	private GameFont baloo2;
	private TextRenderer textRenderer;
	private DiffuseTextureRenderer diffuseTextureRenderer;
	private RectangleRenderer rectangleRenderer;
	private P2PIWServerData data;
	private LineRenderer lineRenderer;
	private Texture tinyNomad;

	@Override
	public void init() {
		data = (P2PIWServerData) context().data();
		baloo2 = resourcePack().getFont("baloo2");
		textRenderer = resourcePack().getRenderer("text", TextRenderer.class);
		diffuseTextureRenderer = resourcePack.getRenderer("diffuse_texture", DiffuseTextureRenderer.class);
		rectangleRenderer = new RectangleRenderer(resourcePack().defaultShaderProgram(), resourcePack().rectangleVAO());
		lineRenderer = resourcePack().getRenderer("line", LineRenderer.class);
		tinyNomad = resourcePack.getTexture("tiny_nomad");
	}

	@Override
	public void render() {
		background(BACKGROUND_COLOUR);
		drawGrid();
		drawNomads();
		drawQueued();
	}

	private void drawQueued() {
		int i = 0;
		for (NomadTiny nomad : data.queuedUsers()) {
			int colour = data.selectedNomad() == nomad ? DARK_BACKGROUND_COLOUR : BACKGROUND_COLOUR;
			rectangleRenderer.render(glContext(), rootGui.dimensions(), 0, i * 2 * GRID_SQUARE_SIZE, GRID_START_X, 2 * GRID_SQUARE_SIZE, REGION_BORDER_COLOUR);
			rectangleRenderer.render(glContext(), rootGui.dimensions(), 2, i * 2 * GRID_SQUARE_SIZE + 2, GRID_START_X - 4, 2 * GRID_SQUARE_SIZE - 4, colour);
			diffuseTextureRenderer.render(glContext(), rootGui, tinyNomad, (GRID_START_X - GRID_SQUARE_SIZE) / 2, i * 2 * GRID_SQUARE_SIZE + 6, 28, 28, nomad.colour());
			textRenderer.alignCenter();
			textRenderer.render(glContext(), rootGui, 0, (int) ((i * 2 + 0.5f) * GRID_SQUARE_SIZE + 16), nomad.username(), 100, baloo2, 24, rgb(0, 0, 0));
			i++;
		}
	}

	private void drawNomads() {
		for (NomadTiny nomad : data.nomads()) {
			renderNomad(nomad);
		}
	}

	private void drawGrid() {
		drawChunks();
		drawRegions();
	}

	private void drawChunks() {
		for (int i = 0; i < 60; i++) {
			lineRenderer.renderPixelCoords(glContext(), rootGui.dimensions(), GRID_START_X + i * GRID_SQUARE_SIZE, 0, GRID_START_X + i * GRID_SQUARE_SIZE, rootGui.dimensions().y, 5, CHUNK_BORDER_COLOUR);
		}
		for (int i = 0; i < 40; i++) {
			lineRenderer.renderPixelCoords(glContext(), rootGui.dimensions(), GRID_START_X, i * GRID_SQUARE_SIZE, rootGui.dimensions().x, i * GRID_SQUARE_SIZE, 5, CHUNK_BORDER_COLOUR);
		}
		for (Vector2i regionCoord : data.regions().keySet()) {
			P2PIWRegion region = data.regions().get(regionCoord);
			Vector2i regionPixelCoords = regionCoord.scale(GRID_SQUARE_SIZE * REGION_NUM_CHUNKS).add(GRID_START_X, GRID_START_Y);
			for (int i = 0; i < REGION_NUM_CHUNKS; i++) {
				for (int j = 0; j < REGION_NUM_CHUNKS; j++) {
					Vector2i chunkPixelCoord = new Vector2i(j, i).scale(GRID_SQUARE_SIZE).add(regionPixelCoords);
					P2PIWChunk chunk = region.chunks[i][j];
					rectangleRenderer.render(glContext(), rootGui.dimensions(), chunkPixelCoord.x, chunkPixelCoord.y, GRID_SQUARE_SIZE, GRID_SQUARE_SIZE, NEARBY_CHUNKS_COLOUR);
					textRenderer.render(glContext(), rootGui,
							chunkPixelCoord.x, chunkPixelCoord.y + 2,
							chunk.age + "",
							GRID_SQUARE_SIZE / 2, baloo2, 12, rgb(35, 146, 26));
					textRenderer.render(glContext(), rootGui,
							chunkPixelCoord.x + GRID_SQUARE_SIZE / 2, chunkPixelCoord.y + 2,
							chunk.value + "",
							GRID_SQUARE_SIZE / 2, baloo2, 12, rgb(178, 0, 0));
				}
			}
		}
	}

	private void drawRegions() {
		for (int i = 0; i < 60; i++) {
			lineRenderer.renderPixelCoords(glContext(), rootGui.dimensions(), GRID_START_X + i * REGION_SQUARE_SIZE, 0, GRID_START_X + i * REGION_SQUARE_SIZE, rootGui.dimensions().y, 5, REGION_BORDER_COLOUR);
		}
		for (int i = 0; i < 40; i++) {
			lineRenderer.renderPixelCoords(glContext(), rootGui.dimensions(), GRID_START_X, i * REGION_SQUARE_SIZE, rootGui.dimensions().x, i * REGION_SQUARE_SIZE, 5, REGION_BORDER_COLOUR);
		}
	}

	private void renderNomad(NomadTiny nomad) {
		diffuseTextureRenderer.render(glContext(), rootGui, tinyNomad,
				GRID_START_X + nomad.x * GRID_SQUARE_SIZE + 4, GRID_START_Y + nomad.y * GRID_SQUARE_SIZE + 4,
				24, 24, nomad.colour());
	}

}
