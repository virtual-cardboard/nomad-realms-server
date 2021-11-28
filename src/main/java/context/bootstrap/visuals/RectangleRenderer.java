package context.bootstrap.visuals;

import static context.visuals.colour.Colour.toRangedVector;

import common.math.Matrix4f;
import common.math.Vector2f;
import common.math.Vector3f;
import context.GLContext;
import context.visuals.gui.RootGui;
import context.visuals.lwjgl.ShaderProgram;
import context.visuals.lwjgl.VertexArrayObject;
import context.visuals.renderer.GameRenderer;

public class RectangleRenderer extends GameRenderer {

	private ShaderProgram shaderProgram;
	private VertexArrayObject vao;

	public RectangleRenderer(ShaderProgram rectangleShaderProgram, VertexArrayObject rectangleVao) {
		this.shaderProgram = rectangleShaderProgram;
		this.vao = rectangleVao;
	}

	public void render(GLContext glContext, RootGui rootGui, float x, float y, float width, float height, float rotation, int colour) {
		Vector2f rootGuiDimensions = rootGui.dimensions();
		Matrix4f matrix4f = new Matrix4f();
		matrix4f.translate(-1, 1).scale(2, -2).scale(1 / rootGuiDimensions.x, 1 / rootGuiDimensions.y);
		matrix4f.translate(x, y).scale(width, height).rotate(rotation, Vector3f.Z_AXIS);

		shaderProgram.bind();
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setVec4("fill", toRangedVector(colour));
		vao.draw(glContext);
	}

}
