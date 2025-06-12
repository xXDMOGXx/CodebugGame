#version 330 core

layout (location=0) in vec3 tileModel;

layout (location=1) in vec2 translation;
layout (location=2) in vec3 tileColor;

out vec3 color;

void main() {
    float m = length(tileModel);
    float d = atan(tileModel.y, tileModel.x);
    float x = m * cos(d) + translation.x;
    float y = m * sin(d) + translation.y;
    gl_Position = vec4(x, y, tileModel.z, 1.0);
    color = tileColor;
}