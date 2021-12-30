#version 330 core

layout(origin_upper_left, pixel_center_integer) in vec4 gl_FragCoord;

out vec4 fragmentColor;

// The following floats are in PIXEL COORDINATES
uniform float x1;
uniform float y1;
uniform float x2;
uniform float y2;
uniform float offset;
uniform float lineLength;
uniform float gapLength;
uniform float halfWidth;
uniform vec4 fill;

float lengthSquared(float x1, float y1, float x2, float y2) {
	return (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
}

float getDist(vec2 p, float x1, float y1, float x2, float y2) {
	// 0-length line
	float lenSquared = lengthSquared(x1, y1, x2, y2);
	if (lenSquared == 0.0) {
		return sqrt(lengthSquared(p.x, p.y, x1, y1));
	}

	float length = sqrt(lenSquared);
	float dot = (p.x - x1) * (x2 - x1) + (p.y - y1) * (y2 - y1);
	float projectionLength = dot / length;

	float quotient = floor((projectionLength + offset) / (lineLength + gapLength));
	float remainder = (projectionLength + offset) - quotient * (lineLength + gapLength);
	while (remainder < 0) {
		remainder += lineLength + gapLength;
	}
	bool inGap = remainder <= lineLength;
	float target = 0;

	if (remainder <= lineLength) {
		target = remainder;
	} else {
		if (remainder <= lineLength + gapLength / 2) {
			target = lineLength;
		} else {
			target = lineLength + gapLength;
		}
	}

	projectionLength = projectionLength - remainder + target;

	float t = projectionLength / length;
	float projectionX = x1 + t * (x2 - x1);
	float projectionY = y1 + t * (y2 - y1); // Projection falls on the segment

	return sqrt(lengthSquared(p.x, p.y, projectionX, projectionY));
}

void main() {
	float dist = getDist(gl_FragCoord.xy, x1, y1, x2, y2);
	fragmentColor = fill;
	float delta = fwidth(dist);
	fragmentColor.a *= 1 - smoothstep(halfWidth - 2 * delta, halfWidth, dist);
}
