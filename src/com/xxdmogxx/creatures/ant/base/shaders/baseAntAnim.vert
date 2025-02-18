#version 330 core

layout (location=0) in vec3 startKeyframe;
layout (location=1) in vec3 endKeyframe;
layout (location=2) in vec2 translation;
layout (location=3) in float rotation;
layout (location=4) in float tween;
layout (location=5) in float maxTween;
layout (location=6) in float scale;

out vec3 color;

void main() {
    vec3 currentStep = mix(startKeyframe, endKeyframe, tween/maxTween);
    float m = length(currentStep.xy) * scale;
    float d = atan(currentStep.y, currentStep.x) + rotation;
    float x = m * cos(d) + translation.x;
    float y = m * sin(d) + translation.y;
    gl_Position = vec4(x, y, currentStep.z, 1.0);
    color = vec3(rotation/6, y, x+0.5f);
}