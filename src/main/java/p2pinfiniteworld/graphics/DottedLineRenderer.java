package p2pinfiniteworld.graphics;

import common.math.Matrix4f;
import common.math.Vector3f;
import context.visuals.builtin.LineShaderProgram;
import context.visuals.builtin.RectangleVertexArrayObject;
import context.visuals.lwjgl.ShaderProgram;
import context.visuals.renderer.GameRenderer;

/**
 * A {@link GameRenderer} that renders line segments.
 * 
 * @author Jay
 *
 */
public class DottedLineRenderer extends GameRenderer {

	private ShaderProgram shaderProgram;
	private RectangleVertexArrayObject vao;

	public DottedLineRenderer(LineShaderProgram shaderProgram, RectangleVertexArrayObject vao) {
		this.shaderProgram = shaderProgram;
		this.vao = vao;
	}

	public void render(float x1, float y1, float x2, float y2, float lineLength, float gapLength, float offset, float width, int colour) {
		// Calculations for matrix transformations
		width = Math.abs(width);
		float halfWidth = width * 0.5f;
		Matrix4f matrix4f = new Matrix4f();
		float rectLength = (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)) + width;
		float theta = (float) Math.atan2(y1 - y2, x2 - x1);

		// Matrix transformations
		matrix4f.translate(-1, 1);
		matrix4f.scale(2, -2).scale(1 / glContext.width(), 1 / glContext.height());
		matrix4f.translate(x1, y1);
		matrix4f.rotate(-theta, Vector3f.Z_AXIS);
		matrix4f.translate(-halfWidth, -halfWidth);
		matrix4f.scale(rectLength, width);

		// Set uniforms
		shaderProgram.bind(glContext);
		shaderProgram.setMat4("matrix4f", matrix4f);
		shaderProgram.setFloat("x1", x1);
		shaderProgram.setFloat("y1", y1);
		shaderProgram.setFloat("x2", x2);
		shaderProgram.setFloat("y2", y2);
		shaderProgram.setFloat("lineLength", lineLength);
		shaderProgram.setFloat("gapLength", gapLength);
		shaderProgram.setFloat("offset", offset);
		shaderProgram.setFloat("halfWidth", halfWidth);
		shaderProgram.setColour("fill", colour);

		// Display VAO
		vao.draw(glContext);
	}

}
