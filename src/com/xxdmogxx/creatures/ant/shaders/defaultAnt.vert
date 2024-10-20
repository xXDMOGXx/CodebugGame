#version 330 core

layout (location=0) in vec3 position;
layout (location=1) in vec2 translation;
layout (location=2) in float rotation;
layout (location=3) in float scale;
layout (location=4) in vec2 animation;

void main() {
    float m = length(position.xy) * scale;
    float d = atan(position.y, position.x) + rotation;
    float x = m * cos(d) + translation.x;
    float y = m * sin(d) + translation.y;
    gl_Position = vec4(x, y, 0.0f, 1.0);
}