package p2pinfiniteworld.graphics;

import common.math.Matrix4f;
import context.visuals.builtin.RectangleVertexArrayObject;
import context.visuals.builtin.TextureShaderProgram;
import context.visuals.lwjgl.ShaderProgram;
import context.visuals.lwjgl.Texture;
import context.visuals.renderer.GameRenderer;

public class DiffuseTextureRenderer extends GameRenderer {

	private ShaderProgram shaderProgram;
	private RectangleVertexArrayObject vao;

	public DiffuseTextureRenderer(TextureShaderProgram shaderProgram, RectangleVertexArrayObject vao) {
		this.shaderProgram = shaderProgram;
		this.vao = vao;
	}

	public void render(Texture texture, float centerX, float centerY, float scale, int diffuse) {
		Matrix4f matrix4f = new Matrix4f();
		matrix4f.translate(-1, 1).scale(2, -2).scale(1 / glContext.width(), 1 / glContext.height())
				.translate(centerX, centerY).scale(texture.width() * scale, texture.height() * scale).translate(-0.5f, -0.5f);
		shaderProgram.bind(glContext);
		texture.bind(glContext, 0);
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setInt("textureSampler", 0);
		shaderProgram.setColour("diffuse", diffuse);
		vao.draw(glContext);
	}

	public void renderCenter(Texture texture, float centerX, float centerY, float depth, float scale, int diffuse) {
		Matrix4f matrix4f = new Matrix4f();
		matrix4f.translate(-1, 1, depth).scale(2, -2).scale(1 / glContext.width(), 1 / glContext.height())
				.translate(centerX, centerY).scale(texture.width() * scale, texture.height() * scale).translate(-0.5f, -0.5f);
		shaderProgram.bind(glContext);
		texture.bind(glContext, 0);
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setInt("textureSampler", 0);
		shaderProgram.setColour("diffuse", diffuse);
		vao.draw(glContext);
	}

	public void render(Texture texture, float x, float y, float w, float h, int diffuse) {
		Matrix4f matrix4f = new Matrix4f();
		matrix4f.translate(-1, 1).scale(2, -2).scale(1 / glContext.width(), 1 / glContext.height())
				.translate(x, y).scale(w, h);
		render(texture, matrix4f, diffuse);
	}

	public void render(Texture texture, Matrix4f matrix4f, int diffuse) {
		shaderProgram.bind(glContext);
		texture.bind(glContext, 0);
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setInt("textureSampler", 0);
		shaderProgram.setColour("diffuse", diffuse);
		vao.draw(glContext);
	}

}
