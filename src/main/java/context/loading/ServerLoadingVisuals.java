package context.loading;

import static context.visuals.lwjgl.ShaderType.FRAGMENT;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import common.loader.GameLoader;
import common.loader.loadtask.ShaderLoadTask;
import common.loader.loadtask.ShaderProgramLoadTask;
import context.ResourcePack;
import context.visuals.GameVisuals;
import context.visuals.builtin.TextShaderProgram;
import context.visuals.builtin.TextureShaderProgram;
import context.visuals.builtin.TexturedTransformationVertexShader;
import context.visuals.lwjgl.Shader;
import context.visuals.lwjgl.Texture;
import context.visuals.renderer.TextRenderer;
import context.visuals.text.GameFont;

public class ServerLoadingVisuals extends GameVisuals {

	public ServerLoadingVisuals() {
	}

	@Override
	public void init() {
		GameLoader loader = loader();
		ResourcePack rp = context().resourcePack();

		TexturedTransformationVertexShader texturedTransformationVS = rp.texturedTransformationVertexShader();

		// Font textures
		Future<Texture> fBaloo2Tex = loader.submit(new NomadRealmsTextureLoadTask(genTexUnit(), "fonts/baloo2.png"));
		Future<Texture> fLangarTex = loader.submit(new NomadRealmsTextureLoadTask(genTexUnit(), "fonts/langar.png"));

		// Shaders
		Future<Shader> fTextFS = loader.submit(new ShaderLoadTask(FRAGMENT, "shaders/textFragmentShader.glsl"));
		Future<Shader> fTextureFS = loader.submit(new ShaderLoadTask(FRAGMENT, "shaders/textureFragmentShader.glsl"));

		Map<String, String> texMap = new HashMap<>();
		Map<String, Future<Texture>> fTexMap = new HashMap<>();
		texMap.put("server", "icons/server.png");
		texMap.put("yard", "images/yard.png");
		texMap.forEach((name, path) -> fTexMap.put(name, loader.submit(new NomadRealmsTextureLoadTask(genTexUnit(), path))));

		try {

			Texture baloo2Tex = fBaloo2Tex.get();
			Future<GameFont> fBaloo2Font = loader.submit(new NomadRealmsFontLoadTask("fonts/baloo2.vcfont", baloo2Tex));
			GameFont baloo2Font = fBaloo2Font.get();
			rp.putFont("baloo2", baloo2Font);

			Texture langarTex = fLangarTex.get();
			Future<GameFont> fLangarFont = loader.submit(new NomadRealmsFontLoadTask("fonts/langar.vcfont", langarTex));
			GameFont langarFont = fLangarFont.get();
			rp.putFont("langar", langarFont);

			Shader textFS = fTextFS.get();
			TextShaderProgram textSP = new TextShaderProgram(texturedTransformationVS, textFS);
			loader.submit(new ShaderProgramLoadTask(textSP)).get();
			rp.putRenderer("text", new TextRenderer(textSP, rp.rectangleVAO()));

			Shader textureFS = fTextureFS.get();
			TextureShaderProgram textureSP = new TextureShaderProgram(texturedTransformationVS, textureFS);
			loader.submit(new ShaderProgramLoadTask(textureSP)).get();
			rp.putShaderProgram("texture", textureSP);

			fTexMap.forEach((name, fTexture) -> {
				try {
					rp.putTexture(name, fTexture.get());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			});
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render() {
	}

	private int n;

	private int genTexUnit() {
		return (n++) % 48;
	}

}
