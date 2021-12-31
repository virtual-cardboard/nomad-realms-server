package p2pinfiniteworld.context.simulation;

import static context.visuals.colour.Colour.rgb;
import static context.visuals.colour.Colour.rgba;
import static p2pinfiniteworld.context.simulation.P2PIWServerData.REGION_NUM_CHUNKS;

import java.util.Iterator;
import java.util.Map.Entry;

import common.math.PosDim;
import common.math.Vector2f;
import common.math.Vector2i;
import context.visuals.GameVisuals;
import context.visuals.builtin.RectangleRenderer;
import context.visuals.lwjgl.Texture;
import context.visuals.renderer.LineRenderer;
import context.visuals.renderer.TextRenderer;
import context.visuals.text.GameFont;
import p2pinfiniteworld.graphics.DiffuseTextureRenderer;
import p2pinfiniteworld.graphics.DottedLineRenderer;
import p2pinfiniteworld.model.NomadTiny;
import p2pinfiniteworld.model.P2PIWChunk;
import p2pinfiniteworld.model.P2PIWRegion;

public class P2PIWServerVisuals extends GameVisuals {

	public static final int CHUNK_PIXEL_SIZE = 64;
	public static final int REGION_PIXEL_SIZE = CHUNK_PIXEL_SIZE * REGION_NUM_CHUNKS;
	public static final int GRID_START_X = 3 * CHUNK_PIXEL_SIZE;
	public static final int GRID_START_Y = 100;
	public static final int NOMAD_PIXEL_SIZE = CHUNK_PIXEL_SIZE - 4;

	public static final int VISITED_CHUNKS_COLOUR = rgba(104, 166, 68, 128);
	public static final int VISITED_REGIONS_COLOUR = rgba(255, 201, 14, 32);
	public static final int CHUNK_BORDER_COLOUR = rgb(111, 115, 122);
	public static final int REGION_BORDER_COLOUR = rgb(255, 255, 255);

	public static final int BACKGROUND_COLOUR = rgb(54, 57, 63);
	public static final int DARK_BACKGROUND_COLOUR = rgb(47, 49, 54);

	public Vector2i gridOffset = new Vector2i();

	private GameFont baloo2;
	private TextRenderer textRenderer;
	private DiffuseTextureRenderer diffuseTextureRenderer;
	private RectangleRenderer rectangleRenderer;
	private P2PIWServerData data;
	private LineRenderer lineRenderer;
	private DottedLineRenderer dottedLineRenderer;
	private Texture tinyNomad;

	@Override
	public void init() {
		data = (P2PIWServerData) context().data();
		baloo2 = resourcePack().getFont("baloo2");
		textRenderer = resourcePack().getRenderer("text", TextRenderer.class);
		diffuseTextureRenderer = resourcePack.getRenderer("diffuse_texture", DiffuseTextureRenderer.class);
		rectangleRenderer = new RectangleRenderer(resourcePack().defaultShaderProgram(), resourcePack().rectangleVAO());
		lineRenderer = resourcePack().getRenderer("line", LineRenderer.class);
		dottedLineRenderer = resourcePack().getRenderer("dotted_line", DottedLineRenderer.class);
		tinyNomad = resourcePack.getTexture("tiny_nomad");
	}

	@Override
	public void render() {
		background(BACKGROUND_COLOUR);
		drawSelectedWorldNomadInfo();
		drawGrid();
		drawNomads();
		drawChunkInfo();
		drawInfo();
		drawQueued();
	}

	private void drawInfo() {
		rectangleRenderer.render(glContext(), rootGui.dimensions(), 0, 0, rootGui.dimensions().x, GRID_START_Y, BACKGROUND_COLOUR);
		rectangleRenderer.render(glContext(), rootGui.dimensions(), 0, GRID_START_Y, GRID_START_X, rootGui.dimensions().y - GRID_START_Y, BACKGROUND_COLOUR);
		lineRenderer.renderPixelCoords(glContext(), rootGui.dimensions(), 0, GRID_START_Y, rootGui.dimensions().x, GRID_START_Y, 10, rgb(43, 46, 51));
		lineRenderer.renderPixelCoords(glContext(), rootGui.dimensions(), GRID_START_X, GRID_START_Y, GRID_START_X, rootGui.dimensions().y, 10, rgb(43, 46, 51));
	}

	private void drawSelectedWorldNomadInfo() {
		NomadTiny nomad = data.selectedWorldNomad();
		if (nomad == null) {
			return;
		}
		for (Vector2i regionCoord : nomad.visitedRegions()) {
			Vector2i regionCoordPixelCoord = new Vector2i(regionCoord.x, regionCoord.y).scale(REGION_PIXEL_SIZE).add(GRID_START_X, GRID_START_Y).add(gridOffset);
			rectangleRenderer.render(glContext(), rootGui.dimensions(), regionCoordPixelCoord.x, regionCoordPixelCoord.y, REGION_PIXEL_SIZE, REGION_PIXEL_SIZE, VISITED_REGIONS_COLOUR);
		}
		for (Vector2i chunkCoord : nomad.visitedChunks()) {
			Vector2i chunkPixelCoord = new Vector2i(chunkCoord.x, chunkCoord.y).scale(CHUNK_PIXEL_SIZE).add(GRID_START_X, GRID_START_Y).add(gridOffset);
			rectangleRenderer.render(glContext(), rootGui.dimensions(), chunkPixelCoord.x, chunkPixelCoord.y, CHUNK_PIXEL_SIZE, CHUNK_PIXEL_SIZE, VISITED_CHUNKS_COLOUR);
		}
		rectangleRenderer.render(glContext(), rootGui.dimensions(),
				gridOffset.x + GRID_START_X + nomad.x * CHUNK_PIXEL_SIZE, gridOffset.y + GRID_START_Y + nomad.y * CHUNK_PIXEL_SIZE,
				CHUNK_PIXEL_SIZE, CHUNK_PIXEL_SIZE, DARK_BACKGROUND_COLOUR);
	}

	private void drawChunkInfo() {
		Iterator<Entry<Vector2i, P2PIWRegion>> iterator = data.regions().entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Vector2i, P2PIWRegion> item = iterator.next();
			Vector2i regionCoord = item.getKey();
			P2PIWRegion region = item.getValue();
			Vector2i regionPixelCoords = regionCoord.scale(REGION_PIXEL_SIZE).add(GRID_START_X, GRID_START_Y).add(gridOffset);
			if (regionPixelCoords.x < rootGui.dimensions().x && regionPixelCoords.y < rootGui.dimensions().y
					&& regionPixelCoords.x + REGION_PIXEL_SIZE > GRID_START_X && regionPixelCoords.y + REGION_PIXEL_SIZE > GRID_START_Y) {
				for (int i = 0; i < REGION_NUM_CHUNKS; i++) {
					for (int j = 0; j < REGION_NUM_CHUNKS; j++) {
						Vector2i chunkPixelCoord = new Vector2i(j, i).scale(CHUNK_PIXEL_SIZE).add(regionPixelCoords);
						P2PIWChunk chunk = region.chunks[i][j];
						textRenderer.render(glContext(), rootGui,
								chunkPixelCoord.x, chunkPixelCoord.y + 2,
								chunk.age + "",
								CHUNK_PIXEL_SIZE / 2, baloo2, 24, rgb(35, 146, 26));
						textRenderer.render(glContext(), rootGui,
								chunkPixelCoord.x + CHUNK_PIXEL_SIZE / 2, chunkPixelCoord.y + 2,
								chunk.value + "",
								CHUNK_PIXEL_SIZE / 2, baloo2, 24, rgb(178, 0, 0));
					}
				}
			}
		}
	}

	private void drawQueued() {
		int i = 0;
		for (NomadTiny nomad : data.queuedUsers()) {
			int colour = data.selectedQueueNomad() == nomad ? DARK_BACKGROUND_COLOUR : BACKGROUND_COLOUR;
			PosDim posDim = queuedRectangle(i);
			rectangleRenderer.render(glContext(), rootGui.dimensions(),
					posDim.x, posDim.y, posDim.w, posDim.h,
					REGION_BORDER_COLOUR);
			rectangleRenderer.render(glContext(), rootGui.dimensions(),
					posDim.x + 2, posDim.y + 2, posDim.w - 4, posDim.h - 4,
					colour);
			diffuseTextureRenderer.render(glContext(), rootGui, tinyNomad,
					(GRID_START_X - CHUNK_PIXEL_SIZE) / 2, posDim.y + 6,
					NOMAD_PIXEL_SIZE, NOMAD_PIXEL_SIZE, nomad.colour());
			textRenderer.alignCenter();
			textRenderer.render(glContext(), rootGui,
					0, (int) (posDim.y + 80),
					nomad.username(), GRID_START_X, baloo2, 24, rgb(0, 0, 0));
			i++;
		}
	}

	public PosDim queuedRectangle(int index) {
		int h = 2 * CHUNK_PIXEL_SIZE - 8;
		return new PosDim(
				2, GRID_START_Y + index * (h + 2) + 6,
				GRID_START_X - 8, h);
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
		int offsetX = (gridOffset.x % CHUNK_PIXEL_SIZE + CHUNK_PIXEL_SIZE) % CHUNK_PIXEL_SIZE;
		int offsetY = (gridOffset.y % CHUNK_PIXEL_SIZE + CHUNK_PIXEL_SIZE) % CHUNK_PIXEL_SIZE;
		int i = 0;
		while (offsetX + GRID_START_X + i * CHUNK_PIXEL_SIZE <= rootGui.dimensions().x + 5) {
			lineRenderer.renderPixelCoords(glContext(), rootGui.dimensions(),
					offsetX + GRID_START_X + i * CHUNK_PIXEL_SIZE, GRID_START_Y,
					offsetX + GRID_START_X + i * CHUNK_PIXEL_SIZE, rootGui.dimensions().y,
					5, CHUNK_BORDER_COLOUR);
			i++;
		}
		i = 0;
		while (offsetY + GRID_START_Y + i * CHUNK_PIXEL_SIZE <= rootGui.dimensions().y + 5) {
			lineRenderer.renderPixelCoords(glContext(), rootGui.dimensions(),
					GRID_START_X, offsetY + GRID_START_Y + i * CHUNK_PIXEL_SIZE,
					rootGui.dimensions().x, offsetY + GRID_START_Y + i * CHUNK_PIXEL_SIZE,
					5, CHUNK_BORDER_COLOUR);
			i++;
		}
	}

	private void drawRegions() {
		int offsetX = (gridOffset.x % REGION_PIXEL_SIZE + REGION_PIXEL_SIZE) % REGION_PIXEL_SIZE;
		int offsetY = (gridOffset.y % REGION_PIXEL_SIZE + REGION_PIXEL_SIZE) % REGION_PIXEL_SIZE;
		int i = 0;
		while (offsetX + GRID_START_X + i * REGION_PIXEL_SIZE <= rootGui.dimensions().x + 5) {
			lineRenderer.renderPixelCoords(glContext(), rootGui.dimensions(),
					offsetX + GRID_START_X + i * REGION_PIXEL_SIZE, GRID_START_Y,
					offsetX + GRID_START_X + i * REGION_PIXEL_SIZE, rootGui.dimensions().y,
					5, REGION_BORDER_COLOUR);
			i++;
		}
		i = 0;
		while (offsetY + GRID_START_Y + i * REGION_PIXEL_SIZE <= rootGui.dimensions().y + 5) {
			lineRenderer.renderPixelCoords(glContext(), rootGui.dimensions(),
					GRID_START_X, offsetY + GRID_START_Y + i * REGION_PIXEL_SIZE,
					rootGui.dimensions().x, offsetY + GRID_START_Y + i * REGION_PIXEL_SIZE,
					5, REGION_BORDER_COLOUR);
			i++;
		}
	}

	private void renderNomad(NomadTiny nomad) {
		Vector2f pos = new Vector2f(gridOffset.x + GRID_START_X + nomad.x * CHUNK_PIXEL_SIZE + 2, gridOffset.y + GRID_START_Y + nomad.y * CHUNK_PIXEL_SIZE + 2);
		if (pos.x < rootGui.dimensions().x && pos.y < rootGui.dimensions().y && pos.x + NOMAD_PIXEL_SIZE > GRID_START_X && pos.y + NOMAD_PIXEL_SIZE > GRID_START_Y) {
			diffuseTextureRenderer.render(glContext(), rootGui, tinyNomad,
					pos.x, pos.y,
					NOMAD_PIXEL_SIZE, NOMAD_PIXEL_SIZE, nomad.colour());
		}
	}

}
