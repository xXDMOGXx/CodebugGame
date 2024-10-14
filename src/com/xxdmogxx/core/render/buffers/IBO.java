package com.xxdmogxx.core.render.buffers;

import com.xxdmogxx.core.utils.Utils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.nio.IntBuffer;

public class IBO {

    private final int ID;

    public IBO(int[] indices) {
        // Generate an IBO and get its reference id
        ID = GL15.glGenBuffers();
        // Bind the VBO specifying it's a GL_ARRAY_BUFFER
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ID);
        // Store the array of indices into an int buffer in memory
        IntBuffer buffer = Utils.storeDataInIntBuffer(indices);
        // Introduce the int buffer into the IBO
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
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
