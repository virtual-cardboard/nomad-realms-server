package context.singlebootstrap;

import context.visuals.GameVisuals;
import context.visuals.colour.Colour;
import context.visuals.renderer.TextRenderer;
import context.visuals.text.GameFont;

public class SingleBootstrapVisuals extends GameVisuals {

	private GameFont baloo2;
	private GameFont langar;
	private TextRenderer textRenderer;

	public SingleBootstrapVisuals() {
	}

	@Override
	public void init() {
		baloo2 = context().resourcePack().getFont("baloo2");
		langar = context().resourcePack().getFont("langar");
		textRenderer = context().resourcePack().getRenderer("text", TextRenderer.class);
	}

	@Override
	public void render() {
		background(Colour.rgb(255, 255, 255));
		textRenderer.render(context().glContext(), rootGui(), 300, 400, "Hello world!!!", 0, baloo2, 80, 255);
		textRenderer.render(context().glContext(), rootGui(), 200, 100, "Nomad Realms Server", 0, langar, 80, 255);
	}

}
