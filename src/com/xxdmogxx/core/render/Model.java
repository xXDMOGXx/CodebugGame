package com.xxdmogxx.core.render;

import com.xxdmogxx.core.render.buffers.IBO;
import com.xxdmogxx.core.render.buffers.VAO;
import com.xxdmogxx.core.render.buffers.VBO;
import com.xxdmogxx.core.utils.Constants;
import com.xxdmogxx.core.utils.Utils;

public class Model {
    private final VBO vertexBuffer;
    private final IBO indexBuffer;
    private final int indexCount;

    public Model(String creatureName, String modelName) {
        String fileName = Constants.DEFAULT_CREATURE_PATH + creatureName + "/models/" + modelName + ".obj";
        float[] vertices = Utils.readObjVertices(fileName);
        int[] indices = Utils.readObjIndices(fileName);
        indexCount = indices.length;

        vertexBuffer = new VBO(vertices);
        indexBuffer = new IBO(indices);
    }

    public void link(VAO vertexArray) {
        vertexBuffer.link(vertexArray, 0, 3);

        indexBuffer.bind();
        indexBuffer.introduceBuffer();
    }

    public void unlink() {
        indexBuffer.unbind();
    }

    public void delete() {
        vertexBuffer.delete();
        indexBuffer.delete();
    }

    public int getIndexCount() {
        return indexCount;
    }
}
