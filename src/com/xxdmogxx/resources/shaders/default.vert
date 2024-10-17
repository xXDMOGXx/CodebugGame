#version 400 core

layout (location = 0) in vec3 position;
out vec3 color;

uniform float scale;


void main() {
    gl_Position = vec4(position.x, position.y, position.z, 1.0);
    color = vec3(abs(sin(scale/1000)), position.y*2, position.z);
}