package com.xxdmogxx.creatures;

import com.xxdmogxx.core.utils.Constants;
import com.xxdmogxx.core.utils.Utils;
import com.xxdmogxx.structures.Wall;

import java.util.ArrayList;

public class Creature {
    private int id;
    private int index;
    private final float[] position;
    private final float[] velocity = new float[]{0.0f, 0.0f};
    private float rotation;
    private float targetRotation;
    private float size;

    private float moveSpeed = 0.01f;
    private float rotateSpeed = 0.3f;

    private boolean recentlyCollided = false;

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

    public void update(ArrayList<Wall> obstacles) {
        decideTarget();
        checkCollisions(obstacles);

        rotateTowardsTarget();
        position[0] += velocity[0];
        position[1] += velocity[1];
    }

    private void decideTarget() {
        if (recentlyCollided) {
            setTarget((float) (Math.random() * 2 * Math.PI));
            recentlyCollided = false;
        }
    }

    private void checkCollisions(ArrayList<Wall> obstacles) {
        if ((position[0] >= 1 || position[0] <= -1) || (position[1] >= 1 || position[1] <= -1)) {
            float newDir = (float) Math.atan2(-position[1], -position[0]);
            if (newDir < 0) newDir += Constants.FLOAT_TAU;
            else if (newDir >= Constants.FLOAT_TAU) newDir -= Constants.FLOAT_TAU;
            setTarget(newDir);
            recentlyCollided = true;
        } else {
            for (Wall obstacle : obstacles) {
                if (position[0] >= obstacle.left && position[0] <= obstacle.right) {
                    if (position[1] >= obstacle.bottom && position[1] <= obstacle.top) {
                        setVelocity(-velocity[0], -velocity[1]);
                        recentlyCollided = true;
                    }
                }
            }
        }
    }

    private void rotateTowardsTarget() {
        if (rotation < 0) rotation += Constants.FLOAT_TAU;
        else if (rotation >= Constants.FLOAT_TAU) rotation -= Constants.FLOAT_TAU;

        double mod = (rotation - targetRotation + Constants.FLOAT_TAU) % Constants.FLOAT_TAU;
        float distance = Math.abs(rotation - targetRotation);
        if (Math.min(Constants.FLOAT_TAU - distance, distance) < rotateSpeed) rotation = targetRotation;
        else if (mod < Constants.FLOAT_PI) rotation -= rotateSpeed;
        else if (mod >= Constants.FLOAT_PI) rotation += rotateSpeed;
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
