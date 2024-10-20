package com.xxdmogxx.core.render.buffers;

import com.xxdmogxx.core.utils.Utils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class VBO {

    private final int ID;
    private FloatBuffer buffer;

    public VBO() {
        // Generate a VBO and get its reference id
        ID = GL15.glGenBuffers();
        // Initialize an empty FloatBuffer to prevent it from being null
        buffer = MemoryUtil.memAllocFloat(0);
    }

    public VBO(float[] data) {
        // Generate a VBO and get its reference id
        ID = GL15.glGenBuffers();
        // Store the array of vertices into a float buffer in memory
        buffer = Utils.storeDataInFloatBuffer(data);
    }

    public void introduceFloatBuffer() {
        // Introduce the float buffer into the VBO. MUST BE BOUND FIRST!
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_DYNAMIC_DRAW);
    }

    public void update(float[] data) {
        // Update the data in the existing float buffer
        Utils.updateDataInFloatBuffer(buffer, data);
        introduceFloatBuffer();
    }

    public void set(float[] data) {
        // Store the array of vertices into a float buffer in memory
        buffer = Utils.storeDataInFloatBuffer(data);
        introduceFloatBuffer();
    }

    public void bind() {
        // Bind the VBO specifying it's a GL_ARRAY_BUFFER
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, ID);
    }

    public void unbind() {
        // Bind the VBO to 0 (No Buffer) so that we don't accidentally modify the VBO we created
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public void delete() {
        GL30.glDeleteBuffers(ID);
    }

    public int getID() {
        return ID;
    }
}
