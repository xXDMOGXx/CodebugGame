#version 400 core

layout (location=0) in vec3 position;
layout (location=1) in vec3 translation;

uniform float scale;

void main() {
    gl_Position = vec4(position.x*scale + translation.x,
                       position.y*scale + translation.y,
                       position.z*scale + translation.z, 1.0);
}