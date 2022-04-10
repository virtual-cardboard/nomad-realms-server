package p2pinfiniteworld.context.loading;

import static context.visuals.lwjgl.ShaderType.FRAGMENT;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import context.ResourcePack;
import context.visuals.GameVisuals;
import context.visuals.builtin.LineShaderProgram;
import context.visuals.builtin.RectangleRenderer;
import context.visuals.builtin.TextShaderProgram;
import context.visuals.builtin.TextureShaderProgram;
import context.visuals.builtin.TexturedTransformationVertexShader;
import context.visuals.lwjgl.FrameBufferObject;
import context.visuals.lwjgl.Shader;
import context.visuals.lwjgl.Texture;
import context.visuals.renderer.LineRenderer;
import context.visuals.renderer.TextRenderer;
import context.visuals.renderer.TextureRenderer;
import context.visuals.text.GameFont;
import engine.common.loader.loadtask.EmptyTextureLoadTask;
import engine.common.loader.loadtask.FrameBufferObjectLoadTask;
import engine.common.loader.loadtask.ShaderLoadTask;
import engine.common.loader.loadtask.ShaderProgramLoadTask;
import loading.NomadRealmsServerFontLoadTask;
import loading.NomadRealmsServerShaderLoadTask;
import loading.NomadRealmsServerTextureLoadTask;
import p2pinfiniteworld.graphics.DiffuseTextureRenderer;
import p2pinfiniteworld.graphics.DottedLineRenderer;

public class P2PIWServerLoadingVisuals extends GameVisuals {

	public P2PIWServerLoadingVisuals() {
	}

	@Override
	public void init() {
		ResourcePack rp = resourcePack();
		TexturedTransformationVertexShader texturedTransformationVS = rp.texturedTransformationVertexShader();

		// Font textures
		Future<Texture> fBaloo2Tex = loader.submit(new NomadRealmsServerTextureLoadTask("fonts/baloo2.png"));
		Future<Texture> fLangarTex = loader.submit(new NomadRealmsServerTextureLoadTask("fonts/langar.png"));
		Future<Texture> fFredokaOneTex = loader.submit(new NomadRealmsServerTextureLoadTask("fonts/fredokaOne.png"));

		// Shaders
		Future<Shader> fTextFS = loader.submit(new ShaderLoadTask(FRAGMENT, "shaders/textFragmentShader.glsl"));
		Future<Shader> fTextureFS = loader.submit(new ShaderLoadTask(FRAGMENT, "shaders/textureFragmentShader.glsl"));
		Future<Shader> fLineFS = loader.submit(new ShaderLoadTask(FRAGMENT, "shaders/lineFragmentShader.glsl"));
		Future<Shader> fDottedLineFS = loader.submit(new NomadRealmsServerShaderLoadTask(FRAGMENT, "shaders/dottedLineFragmentShader.glsl"));
		Future<Shader> fDiffuseFS = loader.submit(new NomadRealmsServerShaderLoadTask(FRAGMENT, "shaders/diffuseFragmentShader.glsl"));

		Map<String, String> texMap = new HashMap<>();
		Map<String, Future<Texture>> fTexMap = new HashMap<>();
		texMap.put("tiny_nomad", "images/tiny_nomad.png");
		texMap.forEach((name, path) -> fTexMap.put(name, loader.submit(new NomadRealmsServerTextureLoadTask(path))));

		try {
			int w = 600;
			int h = 600;
			Texture textTexture = loader.submit(new EmptyTextureLoadTask(w, h)).get();
			FrameBufferObject textFBO = loader.submit(new FrameBufferObjectLoadTask(textTexture, null)).get();
			rp.putFBO("text", textFBO);

			Texture baloo2Tex = fBaloo2Tex.get();
			Future<GameFont> fBaloo2Font = loader.submit(new NomadRealmsServerFontLoadTask("fonts/baloo2.vcfont", baloo2Tex));
			GameFont baloo2Font = fBaloo2Font.get();
			rp.putFont("baloo2", baloo2Font);

			Texture langarTex = fLangarTex.get();
			Future<GameFont> fLangarFont = loader.submit(new NomadRealmsServerFontLoadTask("fonts/langar.vcfont", langarTex));
			GameFont langarFont = fLangarFont.get();
			rp.putFont("langar", langarFont);

			Texture fredokaOneTex = fFredokaOneTex.get();
			Future<GameFont> fFredokaOneFont = loader.submit(new NomadRealmsServerFontLoadTask("fonts/fredokaOne.vcfont", fredokaOneTex));
			GameFont fredokaOneFont = fFredokaOneFont.get();
			rp.putFont("fredokaOne", fredokaOneFont);

			Shader lineFS = fLineFS.get();
			LineShaderProgram lineSP = new LineShaderProgram(rp.transformationVertexShader(), lineFS);
			loader.submit(new ShaderProgramLoadTask(lineSP)).get();
			rp.putShaderProgram("line", lineSP);
			rp.putRenderer("line", new LineRenderer(lineSP, rp.rectangleVAO()));

			Shader dottedLineFS = fDottedLineFS.get();
			LineShaderProgram dottedLineSP = new LineShaderProgram(rp.transformationVertexShader(), dottedLineFS);
			loader.submit(new ShaderProgramLoadTask(dottedLineSP)).get();
			rp.putShaderProgram("dotted_line", dottedLineSP);
			rp.putRenderer("dotted_line", new DottedLineRenderer(dottedLineSP, rp.rectangleVAO()));

			Shader diffuseFS = fDiffuseFS.get();
			TextureShaderProgram diffuseSP = new TextureShaderProgram(texturedTransformationVS, diffuseFS);
			loader.submit(new ShaderProgramLoadTask(diffuseSP)).get();
			rp.putShaderProgram("diffuse_texture", diffuseSP);
			DiffuseTextureRenderer diffuseTextureRenderer = new DiffuseTextureRenderer(diffuseSP, rp.rectangleVAO());
			rp.putRenderer("diffuse_texture", diffuseTextureRenderer);

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

			rp.putRenderer("rectangle", new RectangleRenderer(rp.defaultShaderProgram(), rp.rectangleVAO()));

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

}
