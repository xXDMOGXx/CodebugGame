package com.xxdmogxx.creatures;

import com.xxdmogxx.core.utils.Constants;

public class Creature {
    private int id;
    private int index;
    private final float[] position;
    private final float[] velocity = new float[]{0.0f, 0.0f};
    private float rotation;
    private float targetRotation;
    private float size;

    private float moveSpeed = 0.003f;
    private float rotateSpeed = 0.3f;

    public Creature(int id, int index) {
        this.id = id;
        this.index = index;
        position = new float[]{0.0f, 0.0f};
        rotation = 0.0f;
        size = 0.1f;
    }

    public Creature(int id, int index, float[] position, float rotation) {
        this.id = id;
        this.index = index;
        this.position = position;
        this.rotation = rotation;
        size = 0.1f;
    }

    public Creature(int id, int index, float[] position, float rotation, float size) {
        this.id = id;
        this.index = index;
        this.position = position;
        this.rotation = rotation;
        this.size = size;
    }

    public void setTarget(float direction) {
        lookAt(direction);
        float x = (float) (moveSpeed * Math.cos(direction));
        float y = (float) (moveSpeed * Math.sin(direction));
        setVelocity(x, y);
    }

    public void setTarget(float x, float y) {

    }

    public void lookAt(float rotation) {
        targetRotation = (float) (rotation - Math.PI/2);
    }

    public void setVelocity(float x, float y) {
        velocity[0] = x;
        velocity[1] = y;
    }

    public void update() {
        decideTarget();

        if (rotation > targetRotation) rotation -= rotateSpeed;
        else if (rotation < targetRotation) rotation += rotateSpeed;
        position[0] += velocity[0];
        position[1] += velocity[1];
    }

    public void decideTarget() {
        if (Math.random() < (1.0f / (Constants.UPS_TARGET * 2))) {
            setTarget((float) (Math.random() * 2 * Math.PI));
        }

        if ((position[0] >= 1 || position[0] <= -1) || (position[1] >= 1 || position[1] <= -1)) {
            float newDir = (float) Math.atan2(-position[1], -position[0]);
            setTarget(newDir);
        }
    }

    public int getIndex() {
        return index;
    }

    public float[] getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }
}
