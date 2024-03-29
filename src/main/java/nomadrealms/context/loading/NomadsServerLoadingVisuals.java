package nomadrealms.context.loading;

import static context.visuals.lwjgl.ShaderType.FRAGMENT;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import context.ResourcePack;
import context.visuals.GameVisuals;
import context.visuals.builtin.RectangleRenderer;
import context.visuals.builtin.TextShaderProgram;
import context.visuals.builtin.TextureShaderProgram;
import context.visuals.builtin.TexturedTransformationVertexShader;
import context.visuals.gui.renderer.RootGuiRenderer;
import context.visuals.lwjgl.FrameBufferObject;
import context.visuals.lwjgl.Shader;
import context.visuals.lwjgl.Texture;
import context.visuals.renderer.TextRenderer;
import context.visuals.renderer.TextureRenderer;
import context.visuals.text.GameFont;
import engine.common.loader.GameLoader;
import engine.common.loader.loadtask.EmptyTextureLoadTask;
import engine.common.loader.loadtask.FrameBufferObjectLoadTask;
import engine.common.loader.loadtask.ShaderLoadTask;
import engine.common.loader.loadtask.ShaderProgramLoadTask;
import loading.NomadRealmsServerFontLoadTask;
import loading.NomadRealmsServerTextureLoadTask;

public class NomadsServerLoadingVisuals extends GameVisuals {

	public NomadsServerLoadingVisuals() {
	}

	@Override
	public void init() {
		long time = System.currentTimeMillis();
		GameLoader loader = loader();
		ResourcePack rp = resourcePack();

		TexturedTransformationVertexShader texturedTransformationVS = rp.texturedTransformationVertexShader();

		// Font textures
		Future<Texture> fBaloo2Tex = loader.submit(new NomadRealmsServerTextureLoadTask("fonts/baloo2.png"));
		Future<Texture> fLangarTex = loader.submit(new NomadRealmsServerTextureLoadTask("fonts/langar.png"));

		// Shaders
		Future<Shader> fTextFS = loader.submit(new ShaderLoadTask(FRAGMENT, "shaders/textFragmentShader.glsl"));
		Future<Shader> fTextureFS = loader.submit(new ShaderLoadTask(FRAGMENT, "shaders/textureFragmentShader.glsl"));

		Map<String, String> texMap = new HashMap<>();
		Map<String, Future<Texture>> fTexMap = new HashMap<>();
		texMap.put("server", "icons/server.png");
		texMap.put("yard", "images/yard.png");
		texMap.put("yard_bottom_fence", "images/yard_bottom_fence.png");
		texMap.put("nomad", "images/nomad.png");
		texMap.forEach((name, path) -> fTexMap.put(name, loader.submit(new NomadRealmsServerTextureLoadTask(path))));

		try {
			int w = 600;
			int h = 600;
			Texture textTexture = loader().submit(new EmptyTextureLoadTask(w, h)).get();
			FrameBufferObject textFBO = loader().submit(new FrameBufferObjectLoadTask(textTexture, null)).get();
			rp.putFBO("text", textFBO);

			Texture baloo2Tex = fBaloo2Tex.get();
			Future<GameFont> fBaloo2Font = loader.submit(new NomadRealmsServerFontLoadTask("fonts/baloo2.vcfont", baloo2Tex));
			GameFont baloo2Font = fBaloo2Font.get();
			rp.putFont("baloo2", baloo2Font);

			Texture langarTex = fLangarTex.get();
			Future<GameFont> fLangarFont = loader.submit(new NomadRealmsServerFontLoadTask("fonts/langar.vcfont", langarTex));
			GameFont langarFont = fLangarFont.get();
			rp.putFont("langar", langarFont);

			Shader textureFS = fTextureFS.get();
			TextureShaderProgram textureSP = new TextureShaderProgram(texturedTransformationVS, textureFS);
			loader.submit(new ShaderProgramLoadTask(textureSP)).get();
			rp.putShaderProgram("texture", textureSP);
			TextureRenderer textureRenderer = new TextureRenderer(textureSP, rp.rectangleVAO());
			rp.putRenderer("texture", textureRenderer);

			Shader textFS = fTextFS.get();
			TextShaderProgram textSP = new TextShaderProgram(texturedTransformationVS, textFS);
			loader.submit(new ShaderProgramLoadTask(textSP)).get();
			rp.putRenderer("text", new TextRenderer(textureRenderer, textSP, rp.rectangleVAO(), textFBO));

			rp.putRenderer("rootGui", new RootGuiRenderer());

			rp.putRenderer("rectangle", new RectangleRenderer(rp.defaultShaderProgram(), rp.rectangleVAO()));

			fTexMap.forEach((name, fTexture) -> {
				try {
					rp.putTexture(name, fTexture.get());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			});

			NomadsServerLoadingData data = (NomadsServerLoadingData) context().data();
			data.initTools();
			data.tools().logMessage("Finished loading in " + (System.currentTimeMillis() - time) + "ms.", 0x29cf3aff);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render() {
	}

}
