package context.singlebootstrap;

import static context.visuals.colour.Colour.rgb;

import context.ResourcePack;
import context.singlebootstrap.visuals.RectangleRenderer;
import context.visuals.GameVisuals;
import context.visuals.builtin.TextureShaderProgram;
import context.visuals.colour.Colour;
import context.visuals.lwjgl.Texture;
import context.visuals.renderer.TextRenderer;
import context.visuals.renderer.TextureRenderer;
import context.visuals.text.GameFont;

public class SingleBootstrapVisuals extends GameVisuals {

	private GameFont baloo2;
	private GameFont langar;
	private TextRenderer textRenderer;
	private Texture serverIcon;
	private TextureRenderer textureRenderer;
	private Texture yard;
	private Texture yardBottomFence;
	private Texture nomad;
	private RectangleRenderer rectangleRenderer;

	public SingleBootstrapVisuals() {
	}

	@Override
	public void init() {
		ResourcePack rp = context().resourcePack();
		baloo2 = rp.getFont("baloo2");
		langar = rp.getFont("langar");
		textRenderer = rp.getRenderer("text", TextRenderer.class);
		textureRenderer = new TextureRenderer((TextureShaderProgram) rp.getShaderProgram("texture"), rp.rectangleVAO());
		serverIcon = rp.getTexture("server");
		yard = rp.getTexture("yard");
		yardBottomFence = rp.getTexture("yard_bottom_fence");
		nomad = rp.getTexture("nomad");
		rectangleRenderer = new RectangleRenderer(rp.defaultShaderProgram(), rp.rectangleVAO());
	}

	@Override
	public void render() {
		background(Colour.rgb(255, 255, 255));
		textureRenderer.render(context().glContext(), rootGui().dimensions(), yard, 256, 256 + 200, 1);
		drawNomad(100, 100, rgb(255, 162, 31));
		textureRenderer.render(context().glContext(), rootGui().dimensions(), yardBottomFence, 256, 256 + 200, 1);
		textureRenderer.render(context().glContext(), rootGui(), serverIcon, 900, 300, 500, 500);
		textRenderer.render(context().glContext(), rootGui(), 300, 400, "Hello world!!!", 0, baloo2, 80, 255);
		textRenderer.render(context().glContext(), rootGui(), 200, 100, "Nomad Realms Server", 0, langar, 80, 255);
	}

	private void drawNomad(int x, int y, int colour) {
		rectangleRenderer.render(context().glContext(), rootGui(), x + 12, y + 20, 35, 30, 0, colour);
		textureRenderer.render(context().glContext(), rootGui(), nomad, x, y, 64, 64);
	}

}
