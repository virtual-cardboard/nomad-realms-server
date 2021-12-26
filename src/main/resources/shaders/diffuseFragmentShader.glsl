#version 330 core

out vec4 fragColour;

in vec2 texCoord;

uniform sampler2D textureSampler;
uniform vec4 diffuse;

void main() {
	vec4 tex = texture(textureSampler, texCoord);
	fragColour = vec4(tex.rgb + diffuse.rgb, tex.a);
}
