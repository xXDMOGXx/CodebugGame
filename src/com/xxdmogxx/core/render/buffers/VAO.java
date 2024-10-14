package com.xxdmogxx.core.render.buffers;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class VAO {

    private final int ID;

    public VAO() {
        // Generate a VAO and get its reference id
        ID = GL30.glGenVertexArrays();
        // Make the VAO the current Vertex Array Object by binding it
        GL30.glBindVertexArray(ID);
    }

    public void linkAttribute(VBO vertexBuffer, int layout, int numComponents, int type, int stride, int offset) {
        vertexBuffer.bind();
        // Configure the Vertex Attribute so that OpenGL knows how to read the VBO
        GL20.glVertexAttribPointer(layout, numComponents, type, false, stride, offset);
        GL20.glEnableVertexAttribArray(layout);
        vertexBuffer.unbind();
    }

    public void bind() {
        // Make the VAO the current Vertex Array Object by binding it
        GL30.glBindVertexArray(ID);
    }

    public void unbind() {
        // Bind the VAO to 0 (No Buffer) so that we don't accidentally modify the VAO we created
        GL30.glBindVertexArray(0);
    }

    public void delete() {
        GL30.glDeleteVertexArrays(ID);
    }

    public int getID() {
        return ID;
    }
}
