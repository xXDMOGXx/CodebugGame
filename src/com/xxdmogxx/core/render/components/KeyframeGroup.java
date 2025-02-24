package com.xxdmogxx.core.render.components;

import com.xxdmogxx.core.render.buffers.VAO;
import com.xxdmogxx.core.render.buffers.VBO;
import com.xxdmogxx.core.utils.Constants;
import com.xxdmogxx.creatures.Creature;
import org.lwjgl.opengl.GL20;

import java.util.ArrayList;

public class KeyframeGroup {

    public float[] translations;
    public float[] rotations;
    public float[] tweens;
    public final ArrayList<Creature> creatures;

    private final Shader shader;
    private final Model model;
    private final VAO vertexArray;
    private final VBO translationBuffer;
    private final VBO rotationBuffer;
    private final VBO tweenBuffer;
    private final VBO maxTweenBuffer;
    private final VBO camBuffer;

    private int bufferSize = 0;

    private final int NUM_EXTRA_ATTRIBUTES = 6;

    public KeyframeGroup(AnimationHolder holder, int startKeyframeIndex, int endKeyframeIndex, int maxTiming) throws Exception {
        translations = new float[0];
        rotations = new float[0];
        tweens = new float[0];
        creatures = new ArrayList<>();

        shader = new Shader(Constants.DEFAULT_ANIM_VERT_SHADER, Constants.DEFAULT_FRAG_SHADER);
        vertexArray = new VAO();

        model = new Model(holder, startKeyframeIndex, endKeyframeIndex);
        translationBuffer = new VBO();
        rotationBuffer = new VBO();
        tweenBuffer = new VBO();
        maxTweenBuffer = new VBO(new float[]{maxTiming});
        camBuffer = new VBO();

        model.link(vertexArray);
        translationBuffer.link(vertexArray, 2, 2, 1);
        rotationBuffer.link(vertexArray, 3, 1, 1);
        tweenBuffer.link(vertexArray, 4, 1, 1);
        maxTweenBuffer.link(vertexArray, 5, 1, bufferSize);
        camBuffer.link(vertexArray, 6, 3, bufferSize);

        vertexArray.unbind();
        model.unlink();
    }

    private void bind() {
        vertexArray.bind();
    }

    private void unbind() {
        vertexArray.unbind();
    }

    public void updateArrays() {
        if (tweens.length != creatures.size()) {
            translations = new float[creatures.size()*2];
            rotations = new float[creatures.size()];
            tweens = new float[creatures.size()];
        }
        for (int i = 0; i < creatures.size(); i++) {
            translations[i*2] = creatures.get(i).position[0];
            translations[i*2+1] = creatures.get(i).position[1];
            rotations[i] = creatures.get(i).rotation;
            tweens[i] = creatures.get(i).timingCounter;
        }
    }

    public void setBuffers() {
        translationBuffer.set(translations);
        rotationBuffer.set(rotations);
        tweenBuffer.set(tweens);
    }

    public void updateBuffers() {
        if (bufferSize == creatures.size()) {
            translationBuffer.update(translations);
            rotationBuffer.update(rotations);
            tweenBuffer.update(tweens);
        } else {
            translationBuffer.set(translations);
            rotationBuffer.set(rotations);
            tweenBuffer.set(tweens);
            bufferSize = creatures.size();
            bind();
            maxTweenBuffer.link(vertexArray, 5, 1, bufferSize);
            camBuffer.link(vertexArray, 6, 3, bufferSize);
            unbind();
        }
    }

    public void setCamPos(float[] pos) {
        camBuffer.set(pos);
    }

    public void updateCamPos(float[] pos) {
        camBuffer.update(pos);
    }

    private void enableAttributes() {
        for (int i = 0; i <= NUM_EXTRA_ATTRIBUTES; i++) {
            GL20.glEnableVertexAttribArray(i);
        }
    }

    private void disableAttributes() {
        for (int i = 0; i <= NUM_EXTRA_ATTRIBUTES; i++) {
            GL20.glDisableVertexAttribArray(i);
        }
    }

    public void enable() {
        shader.enable();
        bind();
        enableAttributes();
    }

    public void disable() {
        disableAttributes();
        unbind();
        shader.disable();
    }

    public void delete() {
        vertexArray.delete();
        model.delete();
        translationBuffer.delete();
        rotationBuffer.delete();
        tweenBuffer.delete();
        maxTweenBuffer.delete();
        camBuffer.delete();
        shader.delete();
    }

    public int getIndexCount() {
        return model.getIndexCount();
    }

    public int getBufferSize() {
        return bufferSize;
    }
}