package com.xxdmogxx.core.render;

import com.xxdmogxx.core.engine.Launcher;

public class Camera {
    public final float[] position;
    private final float minZoom = 0.01f;
    private final float maxZoom = 1.0f;
    private float dragReductionX;
    private float dragReductionY;

    public Camera() {
        position = new float[]{0.0f, 0.0f, 0.5f};
        dragReductionX = (float) Launcher.getWindow().getWidth() * position[2] / 2;
        dragReductionY = (float) Launcher.getWindow().getHeight() * position[2] / 2;
    }

    public void move(float x, float y) {
        position[0] -= x / dragReductionX;
        position[1] += y / dragReductionY;
    }

    public void changeZoom(float amount) {
        position[2] += amount;
        if (position[2] < minZoom) {
            position[2] = minZoom;
        } else if (position[2] > maxZoom) {
            position[2] = maxZoom;
        }
        dragReductionX = (float) Launcher.getWindow().getWidth() * position[2] / 2;
        dragReductionY = (float) Launcher.getWindow().getHeight() * position[2] / 2;
    }
}
