package p2pinfiniteworld.context.loading;

import static context.visuals.lwjgl.ShaderType.FRAGMENT;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import common.loader.loadtask.EmptyTextureLoadTask;
import common.loader.loadtask.FrameBufferObjectLoadTask;
import common.loader.loadtask.ShaderLoadTask;
import common.loader.loadtask.ShaderProgramLoadTask;
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
		TexturedTransformationVertexShader texturedTransformationVS = resourcePack.texturedTransformationVertexShader();

		// Font textures
		Future<Texture> fBaloo2Tex = loader.submit(new NomadRealmsServerTextureLoadTask("fonts/baloo2.png"));
		Future<Texture> fLangarTex = loader.submit(new NomadRealmsServerTextureLoadTask("fonts/langar.png"));

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
			resourcePack.putFBO("text", textFBO);

			Texture baloo2Tex = fBaloo2Tex.get();
			Future<GameFont> fBaloo2Font = loader.submit(new NomadRealmsServerFontLoadTask("fonts/baloo2.vcfont", baloo2Tex));
			GameFont baloo2Font = fBaloo2Font.get();
			resourcePack.putFont("baloo2", baloo2Font);

			Texture langarTex = fLangarTex.get();
			Future<GameFont> fLangarFont = loader.submit(new NomadRealmsServerFontLoadTask("fonts/langar.vcfont", langarTex));
			GameFont langarFont = fLangarFont.get();
			resourcePack.putFont("langar", langarFont);

			Shader lineFS = fLineFS.get();
			LineShaderProgram lineSP = new LineShaderProgram(resourcePack.transformationVertexShader(), lineFS);
			loader.submit(new ShaderProgramLoadTask(lineSP)).get();
			resourcePack.putShaderProgram("line", lineSP);
			resourcePack.putRenderer("line", new LineRenderer(lineSP, resourcePack.rectangleVAO()));

			Shader dottedLineFS = fDottedLineFS.get();
			LineShaderProgram dottedLineSP = new LineShaderProgram(resourcePack.transformationVertexShader(), dottedLineFS);
			loader.submit(new ShaderProgramLoadTask(dottedLineSP)).get();
			resourcePack.putShaderProgram("dotted_line", dottedLineSP);
			resourcePack.putRenderer("dotted_line", new DottedLineRenderer(dottedLineSP, resourcePack.rectangleVAO()));

			Shader diffuseFS = fDiffuseFS.get();
			TextureShaderProgram diffuseSP = new TextureShaderProgram(texturedTransformationVS, diffuseFS);
			loader.submit(new ShaderProgramLoadTask(diffuseSP)).get();
			resourcePack.putShaderProgram("diffuse_texture", diffuseSP);
			DiffuseTextureRenderer diffuseTextureRenderer = new DiffuseTextureRenderer(diffuseSP, resourcePack.rectangleVAO());
			resourcePack.putRenderer("diffuse_texture", diffuseTextureRenderer);

			Shader textureFS = fTextureFS.get();
			TextureShaderProgram textureSP = new TextureShaderProgram(texturedTransformationVS, textureFS);
			loader.submit(new ShaderProgramLoadTask(textureSP)).get();
			resourcePack.putShaderProgram("texture", textureSP);
			TextureRenderer textureRenderer = new TextureRenderer(textureSP, resourcePack.rectangleVAO());
			resourcePack.putRenderer("texture", textureRenderer);

			Shader textFS = fTextFS.get();
			TextShaderProgram textSP = new TextShaderProgram(texturedTransformationVS, textFS);
			loader.submit(new ShaderProgramLoadTask(textSP)).get();
			resourcePack.putRenderer("text", new TextRenderer(textureRenderer, textSP, resourcePack.rectangleVAO(), textFBO));

			resourcePack.putRenderer("rectangle", new RectangleRenderer(resourcePack().defaultShaderProgram(), resourcePack().rectangleVAO()));

			fTexMap.forEach((name, fTexture) -> {
				try {
					resourcePack.putTexture(name, fTexture.get());
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
