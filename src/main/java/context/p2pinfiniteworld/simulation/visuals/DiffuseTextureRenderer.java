package context.p2pinfiniteworld.simulation.visuals;

import common.math.Matrix4f;
import common.math.Vector2f;
import context.GLContext;
import context.visuals.builtin.RectangleVertexArrayObject;
import context.visuals.builtin.TextureShaderProgram;
import context.visuals.gui.RootGui;
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

	public void render(GLContext glContext, Vector2f screenDim, Texture texture, float centerX, float centerY, float scale, int diffuse) {
		Matrix4f matrix4f = new Matrix4f();
		matrix4f.translate(-1, 1).scale(2, -2).scale(1 / screenDim.x, 1 / screenDim.y)
				.translate(centerX, centerY).scale(texture.width() * scale, texture.height() * scale).translate(-0.5f, -0.5f);
		shaderProgram.bind(glContext);
		texture.bind(glContext, 0);
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setInt("textureSampler", 0);
		shaderProgram.setColour("diffuse", diffuse);
		vao.draw(glContext);
	}

	public void render(GLContext glContext, Vector2f screenDim, Texture texture, float centerX, float centerY, float depth, float scale, int diffuse) {
		Matrix4f matrix4f = new Matrix4f();
		matrix4f.translate(-1, 1, depth).scale(2, -2).scale(1 / screenDim.x, 1 / screenDim.y)
				.translate(centerX, centerY).scale(texture.width() * scale, texture.height() * scale).translate(-0.5f, -0.5f);
		shaderProgram.bind(glContext);
		texture.bind(glContext, 0);
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setInt("textureSampler", 0);
		shaderProgram.setColour("diffuse", diffuse);
		vao.draw(glContext);
	}

	public void render(GLContext glContext, RootGui rootGui, Texture texture, float x, float y, float w, float h, int diffuse) {
		Vector2f rootGuiDimensions = rootGui.dimensions();
		Matrix4f matrix4f = new Matrix4f();
		matrix4f.translate(-1, 1).scale(2, -2).scale(1 / rootGuiDimensions.x, 1 / rootGuiDimensions.y)
				.translate(x, y).scale(w, h);
		render(glContext, texture, matrix4f, diffuse);
	}

	public void render(GLContext glContext, Texture texture, Matrix4f matrix4f, int diffuse) {
		shaderProgram.bind(glContext);
		texture.bind(glContext, 0);
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setInt("textureSampler", 0);
		shaderProgram.setColour("diffuse", diffuse);
		vao.draw(glContext);
	}

}
