package com.xxdmogxx.core.render.components;

import com.xxdmogxx.core.render.buffers.IBO;
import com.xxdmogxx.core.render.buffers.VAO;
import com.xxdmogxx.core.render.buffers.VBO;
import com.xxdmogxx.core.utils.Utils;

public class Model {
    private final VBO startVertexBuffer;
    private final VBO endVertexBuffer;
    private final IBO indexBuffer;
    private final int indexCount;

    public Model(AnimationHolder holder, int startKeyframeIndex, int endKeyframeIndex) {
        indexCount = holder.indices.length;

        startVertexBuffer = new VBO(holder.vertices[startKeyframeIndex]);
        endVertexBuffer = new VBO(holder.vertices[endKeyframeIndex]);
        indexBuffer = new IBO(holder.indices);
    }

    public void link(VAO vertexArray) {
        startVertexBuffer.link(vertexArray, 0, 3);
        endVertexBuffer.link(vertexArray, 1, 3);

        indexBuffer.bind();
        indexBuffer.introduceBuffer();
    }

    public void unlink() {
        indexBuffer.unbind();
    }

    public void delete() {
        startVertexBuffer.delete();
        endVertexBuffer.delete();
        indexBuffer.delete();
    }

    public int getIndexCount() {
        return indexCount;
    }
}
