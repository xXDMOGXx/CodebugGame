package com.xxdmogxx.core.render.buffers;

import com.xxdmogxx.core.utils.Utils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;

public class IBO {

    private final int ID;
    IntBuffer buffer;

    public IBO() {
        // Generate an IBO and get its reference id
        ID = GL15.glGenBuffers();
        // Store the array of indices into an int buffer in memory
        buffer = MemoryUtil.memAllocInt(0);
    }

    public IBO(int[] indices) {
        // Generate an IBO and get its reference id
        ID = GL15.glGenBuffers();
        // Store the array of indices into an int buffer in memory
        buffer = Utils.storeDataInBuffer(indices);
    }

    public void introduceBuffer() {
        // Introduce the int buffer into the IBO. MUST BE BOUND FIRST!
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    public void update(int[] data) {
        // Update the data in the existing int buffer
        Utils.updateDataInBuffer(buffer, data);
        introduceBuffer();
    }

    public void set(int[] data) {
        // Store the array of indices into an int buffer in memory
        buffer = Utils.storeDataInBuffer(data);
        introduceBuffer();
    }

    public void bind() {
        // Bind the IBO specifying it's a GL_ELEMENT_ARRAY_BUFFER
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ID);
    }

    public void unbind() {
        // Bind the VBO to 0 (No Buffer) so that we don't accidentally modify the VBO we created
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void delete() {
        GL30.glDeleteBuffers(ID);
    }
}
