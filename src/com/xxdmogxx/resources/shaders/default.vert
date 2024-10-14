#version 400 core

layout (location = 0) in vec3 position;
out vec3 color;

uniform float scale;


void main() {
    gl_Position = vec4(position.x + sin(scale + (position.y/10))/500, position.y, position.z, 1.0);
    //gl_Position = vec4(position.x, position.y, position.z, 1.0);
    color = vec3(mod(scale, 10.0f), position.y, position.z);
}