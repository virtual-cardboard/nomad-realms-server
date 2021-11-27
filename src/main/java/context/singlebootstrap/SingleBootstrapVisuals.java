package context.singlebootstrap;

import context.ResourcePack;
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
	}

	@Override
	public void render() {
		background(Colour.rgb(255, 255, 255));
		textureRenderer.render(context().glContext(), rootGui(), serverIcon, 900, 300, 500, 300);
		textRenderer.render(context().glContext(), rootGui(), 300, 400, "Hello world!!!", 0, baloo2, 80, 255);
		textRenderer.render(context().glContext(), rootGui(), 200, 100, "Nomad Realms Server", 0, langar, 80, 255);
	}

}
