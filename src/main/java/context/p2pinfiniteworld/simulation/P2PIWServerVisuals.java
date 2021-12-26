package context.p2pinfiniteworld.simulation;

import static context.visuals.colour.Colour.rgb;

import context.p2pinfiniteworld.simulation.visuals.DiffuseTextureRenderer;
import context.p2pinfiniteworld.simulation.visuals.NomadTiny;
import context.visuals.GameVisuals;
import context.visuals.builtin.RectangleRenderer;
import context.visuals.builtin.TextureShaderProgram;
import context.visuals.lwjgl.Texture;
import context.visuals.renderer.LineRenderer;
import context.visuals.renderer.TextRenderer;
import context.visuals.renderer.TextureRenderer;
import context.visuals.text.GameFont;

public class P2PIWServerVisuals extends GameVisuals {

	private static final int GRID_SQUARE_SIZE = 32;
	private static final int REGION_SQUARE_SIZE = GRID_SQUARE_SIZE * 4;
	private static final int GRID_START_X = 96;

	private static final int NOMAD_COLOUR = rgb(10, 125, 175);
	private static final int NEARBY_CHUNKS_COLOUR = rgb(104, 166, 68);
	private static final int CHUNK_BORDER_COLOUR = rgb(111, 115, 122);
	private static final int REGION_BORDER_COLOUR = rgb(255, 255, 255);

	private GameFont baloo2;
	private GameFont langar;
	private TextRenderer textRenderer;
	private TextureRenderer textureRenderer;
	private DiffuseTextureRenderer diffuseTextureRenderer;
	private RectangleRenderer rectangleRenderer;
	private P2PIWServerData data;
	private LineRenderer lineRenderer;
	private Texture tinyNomad;

	@Override
	public void init() {
		data = (P2PIWServerData) context().data();
		baloo2 = resourcePack().getFont("baloo2");
		langar = resourcePack().getFont("langar");
		textRenderer = resourcePack().getRenderer("text", TextRenderer.class);
		textureRenderer = new TextureRenderer((TextureShaderProgram) resourcePack().getShaderProgram("texture"), resourcePack().rectangleVAO());
		diffuseTextureRenderer = resourcePack.getRenderer("diffuse_texture", DiffuseTextureRenderer.class);
		rectangleRenderer = new RectangleRenderer(resourcePack().defaultShaderProgram(), resourcePack().rectangleVAO());
		lineRenderer = resourcePack().getRenderer("line", LineRenderer.class);
		tinyNomad = resourcePack.getTexture("tiny_nomad");
	}

	@Override
	public void render() {
		background(rgb(54, 57, 63));
		drawGrid();
		drawNomads();
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
//		diffuseTextureRenderer.render(glContext(), rootGui, tinyNomad, GRID_START_X + nomad.x * GRID_SQUARE_SIZE + 2, nomad.y * GRID_SQUARE_SIZE + 2, 28, 28, );
		diffuseTextureRenderer.render(glContext(), rootGui, tinyNomad, GRID_START_X + nomad.x * GRID_SQUARE_SIZE + 2, nomad.y * GRID_SQUARE_SIZE + 2, 28, 28, NOMAD_COLOUR);
	}

}
