package com.xxdmogxx.core.render;

import com.xxdmogxx.core.render.buffers.IBO;
import com.xxdmogxx.core.render.buffers.VAO;
import com.xxdmogxx.core.render.buffers.VBO;
import com.xxdmogxx.core.utils.Constants;
import com.xxdmogxx.core.utils.Utils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL33;

public class Model {

    private final Shader shader;
    private final VAO vertexArray;
    private final VBO vertexBuffer;
    private final VBO translationBuffer;
    private final VBO rotationBuffer;
    private final VBO scaleBuffer;
    private final VBO animationBuffer;
    private final IBO indexBuffer;
    private final int indexCount;

    public Model(String creatureName, String modelName, String vertexShaderName, String fragmentShaderName) throws Exception {
        shader = new Shader(creatureName, vertexShaderName, fragmentShaderName);

        String fileName = Constants.DEFAULT_CREATURE_PATH + creatureName + "/models/" + modelName + ".obj";
        float[] vertices = Utils.readObjVertices(fileName);
        int[] indices = Utils.readObjIndices(fileName);
        indexCount = indices.length;

        vertexArray = new VAO();
        vertexBuffer = new VBO(vertices);
        translationBuffer = new VBO();
        rotationBuffer = new VBO();
        scaleBuffer = new VBO(Constants.scale);
        animationBuffer = new VBO();

        vertexBuffer.bind();
        vertexBuffer.introduceFloatBuffer();
        vertexArray.linkAttribute(0, 3, GL11.GL_FLOAT, 0, 0);
        vertexBuffer.unbind();

        translationBuffer.bind();
        vertexArray.linkAttribute(1, 2, GL11.GL_FLOAT, 0, 0);
        GL33.glVertexAttribDivisor(1, 1);
        translationBuffer.unbind();

        rotationBuffer.bind();
        vertexArray.linkAttribute(2, 1, GL11.GL_FLOAT, 0, 0);
        GL33.glVertexAttribDivisor(2, 1);
        rotationBuffer.unbind();

        scaleBuffer.bind();
        scaleBuffer.introduceFloatBuffer();
        vertexArray.linkAttribute(3, 1, GL11.GL_FLOAT, 0, 0);
        GL33.glVertexAttribDivisor(3, Constants.numAnts);
        scaleBuffer.unbind();

        animationBuffer.bind();
        vertexArray.linkAttribute(4, 2, GL11.GL_FLOAT, 0, 0);
        animationBuffer.unbind();

        indexBuffer = new IBO(indices);
        vertexArray.unbind();
        indexBuffer.unbind();
    }

    private void bind() {
        vertexArray.bind();
    }

    private void unbind() {
        vertexArray.unbind();
    }

    public void setBuffers(float[] translations, float[] rotations) {
        translationBuffer.bind();
        translationBuffer.set(translations);
        translationBuffer.unbind();

        rotationBuffer.bind();
        rotationBuffer.set(rotations);
        rotationBuffer.unbind();
    }

    public void updateBuffers(float[] translations, float[] rotations) {
        translationBuffer.bind();
        translationBuffer.update(translations);
        translationBuffer.unbind();

        rotationBuffer.bind();
        rotationBuffer.update(rotations);
        rotationBuffer.unbind();
    }

    public void enable() {
        shader.enable();
        bind();
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL20.glEnableVertexAttribArray(3);
        GL20.glEnableVertexAttribArray(4);
    }

    public void disable() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL20.glDisableVertexAttribArray(3);
        GL20.glDisableVertexAttribArray(4);
        unbind();
        shader.disable();
    }

    public void delete() {
        vertexArray.delete();
        vertexBuffer.delete();
        translationBuffer.delete();
        rotationBuffer.delete();
        scaleBuffer.delete();
        animationBuffer.delete();
        indexBuffer.delete();
        shader.delete();
    }

    public int getId() {
        return vertexArray.getID();
    }

    public int getIndexCount() {
        return indexCount;
    }
}
