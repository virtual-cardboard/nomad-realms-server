package context.server;

import static context.visuals.colour.Colour.rgb;
import static java.util.Comparator.comparing;

import context.ResourcePack;
import context.server.visuals.model.NomadMini;
import context.visuals.GameVisuals;
import context.visuals.builtin.RectangleRenderer;
import context.visuals.builtin.TextureShaderProgram;
import context.visuals.lwjgl.Texture;
import context.visuals.renderer.TextRenderer;
import context.visuals.renderer.TextureRenderer;
import context.visuals.text.GameFont;

public class ServerVisuals extends GameVisuals {

	private GameFont baloo2;
	private GameFont langar;
	private TextRenderer textRenderer;
	private Texture serverIcon;
	private TextureRenderer textureRenderer;
	private Texture yard;
	private Texture yardBottomFence;
	private Texture nomad;
	private RectangleRenderer rectangleRenderer;
	private ServerData data;

	public ServerVisuals() {
	}

	@Override
	public void init() {
		data = (ServerData) context().data();
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
		background(rgb(255, 255, 255));
		textureRenderer.render(context().glContext(), rootGui().dimensions(), yard, 256, 256 + 200, 1);
		data.minis().sort(comparing(NomadMini::y));
		for (int i = 0; i < data.minis().size(); i++) {
			NomadMini mini = data.minis().get(i);
			mini.update();
			drawNomad(mini);
		}
		textureRenderer.render(context().glContext(), rootGui().dimensions(), yardBottomFence, 256, 256 + 200, 1);
		textureRenderer.render(context().glContext(), rootGui(), serverIcon, 900, 300, 500, 500);
		textRenderer.render(context().glContext(), rootGui(), 300, 400, "Hello world!!!", 0, baloo2, 80, 255);
		textRenderer.render(context().glContext(), rootGui(), 200, 100, "Nomad Realms Server", 0, langar, 80, 255);
	}

	private void drawNomad(NomadMini mini) {
		float x = mini.x();
		float y = mini.y() - mini.h();
		rectangleRenderer.render(context().glContext(), rootGui().dimensions(), x + 12, y + 20, 35, 30, mini.colour());
		textureRenderer.render(context().glContext(), rootGui(), nomad, x, y, 64, 64);
	}

}
