#version 330 core

layout (location=0) in vec3 lastKey;
layout (location=1) in vec3 nextKey;
layout (location=2) in vec2 translation;
layout (location=3) in float rotation;
layout (location=4) in float scale;
layout (location=5) in float tween;

out vec3 color;

void main() {
    vec3 currentStep = mix(lastKey, nextKey, tween);
    float m = length(currentStep) * scale;
    float d = atan(currentStep.y, currentStep.x) + rotation;
    float x = m * cos(d) + translation.x;
    float y = m * sin(d) + translation.y;
    gl_Position = vec4(x, y, currentStep.z, 1.0);
    color = vec3(rotation/6, y, x+0.5f);
}