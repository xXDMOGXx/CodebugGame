package com.xxdmogxx.core.render.components;

import com.xxdmogxx.core.render.buffers.VAO;
import com.xxdmogxx.core.render.buffers.VBO;
import com.xxdmogxx.core.utils.Constants;
import org.lwjgl.opengl.GL20;

import java.util.HashMap;

public class Group {

    private final Shader shader;
    private final Model model;
    private final VAO vertexArray;
    private final VBO translationBuffer;
    private final VBO rotationBuffer;
    private final VBO scaleBuffer;

    private final int NUM_EXTRA_ATTRIBUTES = 3;
    private int size = 0;

    public Group(HashMap<String, String> resourceLookup) throws Exception {
        shader = new Shader(resourceLookup.get("defaultVertex"), resourceLookup.get("defaultFragment"));
        vertexArray = new VAO();

        model = new Model(resourceLookup.get("farAwayModel"));
        translationBuffer = new VBO();
        rotationBuffer = new VBO();
        scaleBuffer = new VBO(Constants.scale);

        model.link(vertexArray);
        translationBuffer.link(vertexArray, 1, 2, 1);
        rotationBuffer.link(vertexArray, 2, 1, 1);
        scaleBuffer.link(vertexArray, 3, 1, Constants.numAnts);

        vertexArray.unbind();
        model.unlink();
    }

    private void bind() {
        vertexArray.bind();
    }

    private void unbind() {
        vertexArray.unbind();
    }

    public void setBuffers(int newSize, float[] translations, float[] rotations) {
        size = newSize;
        translationBuffer.set(translations);
        rotationBuffer.set(rotations);
    }

    public void updateBuffers(float[] translations, float[] rotations) {
        translationBuffer.update(translations);
        rotationBuffer.update(rotations);
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
        vertexArray.delete();;
        model.delete();
        translationBuffer.delete();
        rotationBuffer.delete();
        scaleBuffer.delete();
        shader.delete();
    }

    public int getId() {
        return vertexArray.getID();
    }

    public int getIndexCount() {
        return model.getIndexCount();
    }
}
