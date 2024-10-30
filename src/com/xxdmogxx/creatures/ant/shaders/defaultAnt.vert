#version 330 core

layout (location=0) in vec2 position;
layout (location=1) in vec2 translation;
layout (location=2) in float rotation;
layout (location=3) in float scale;

out vec3 color;

void main() {
    float m = length(position) * scale;
    float d = atan(position.y, position.x) + rotation;
    float x = m * cos(d) + translation.x;
    float y = m * sin(d) + translation.y;
    gl_Position = vec4(x, y, 0.0f, 1.0);
    color = vec3(rotation/6, y, x+0.5f);
}