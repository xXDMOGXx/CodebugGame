package com.xxdmogxx.core.render.buffers;

import com.xxdmogxx.core.utils.Utils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class VBO {

    private final int ID;
    private FloatBuffer buffer;
    private int size;

    public VBO() {
        // Generate a VBO and get its reference id
        ID = GL15.glGenBuffers();
        // Initialize an empty FloatBuffer to prevent it from being null
        buffer = MemoryUtil.memAllocFloat(0);
        size = 0;
    }

    public VBO(float[] data) {
        // Generate a VBO and get its reference id
        ID = GL15.glGenBuffers();
        // Store the array of vertices into a float buffer in memory
        buffer = Utils.storeDataInBuffer(data);
        size = data.length;
    }

    public void introduceBuffer() {
        // Introduce the float buffer into the VBO. MUST BE BOUND FIRST!
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_DYNAMIC_DRAW);
    }

    public void update(float[] data) {
        bind();
        // Update the data in the existing float buffer
        Utils.updateDataInBuffer(buffer, data);
        introduceBuffer();
        size = data.length;
        unbind();
    }

    public void set(float[] data) {
        bind();
        // Store the array of vertices into a float buffer in memory
        buffer = Utils.storeDataInBuffer(data);
        introduceBuffer();
        size = data.length;
        unbind();
    }

    public void bind() {
        // Bind the VBO specifying it's a GL_ARRAY_BUFFER
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, ID);
    }

    public void unbind() {
        // Bind the VBO to 0 (No Buffer) so that we don't accidentally modify the VBO we created
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public void link(VAO vertexArray, int layout, int numComponents) {
        bind();
        if (size > 0) introduceBuffer();
        vertexArray.linkAttribute(layout, numComponents, GL11.GL_FLOAT, 0, 0);
        unbind();
    }

    public void link(VAO vertexArray, int layout, int numComponents, int attributeDivisor) {
        bind();
        if (size > 0) introduceBuffer();
        vertexArray.linkAttribute(layout, numComponents, GL11.GL_FLOAT, 0, 0);
        GL33.glVertexAttribDivisor(layout, attributeDivisor);
        unbind();
    }

    public void delete() {
        GL30.glDeleteBuffers(ID);
    }

    public int getID() {
        return ID;
    }
}
