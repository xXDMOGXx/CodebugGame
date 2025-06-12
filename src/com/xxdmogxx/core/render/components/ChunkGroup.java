package com.xxdmogxx.core.render.components;

import com.xxdmogxx.core.render.buffers.VAO;
import com.xxdmogxx.core.render.buffers.VBO;
import com.xxdmogxx.core.utils.Constants;
import com.xxdmogxx.world.Tile;
import org.lwjgl.opengl.GL20;

import java.awt.*;
import java.util.ArrayList;

public class ChunkGroup {

    public float[] translations;
    public float[] colors;
    public final ArrayList<Tile> tiles;

    private final Shader shader;
    private final RenderTile tileModel;
    private final VAO vertexArray;
    private final VBO translationBuffer;
    private final VBO colorBuffer;

    private int bufferSize = 0;

    private final int NUM_EXTRA_ATTRIBUTES = 2;

    public ChunkGroup() throws Exception {
        translations = new float[0];
        colors = new float[0];
        tiles = new ArrayList<>();

        shader = new Shader(Constants.DEFAULT_CHUNK_VERT_SHADER, Constants.DEFAULT_CHUNK_FRAG_SHADER);
        vertexArray = new VAO();

        tileModel = new RenderTile();
        translationBuffer = new VBO();
        colorBuffer = new VBO();

        tileModel.link(vertexArray);
        translationBuffer.link(vertexArray, 1, 2, 1);
        colorBuffer.link(vertexArray, 2, 3, 1);

        vertexArray.unbind();
        tileModel.unlink();
    }

    private void bind() {
        vertexArray.bind();
    }

    private void unbind() {
        vertexArray.unbind();
    }

    public void updateArrays() {
        if (colors.length != tiles.size() * 3) {
            translations = new float[tiles.size() * 2];
            colors = new float[tiles.size() * 3];
        }
        for (int i = 0; i < tiles.size(); i++) {
            translations[i*2] = tiles.get(i).getX();
            translations[i*2+1] = tiles.get(i).getY();
            colors[i*3] = tiles.get(i).getColor()[0];
            colors[i*3+1] = tiles.get(i).getColor()[1];
            colors[i*3+2] = tiles.get(i).getColor()[2];
        }
    }

    public void setBuffers() {
        translationBuffer.set(translations);
        colorBuffer.set(colors);
    }

    public void updateBuffers() {
        if (bufferSize == tiles.size()) {
            translationBuffer.update(translations);
            colorBuffer.update(colors);
        } else {
            translationBuffer.set(translations);
            colorBuffer.set(colors);
            bufferSize = tiles.size();
        }
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
        tileModel.delete();
        translationBuffer.delete();
        colorBuffer.delete();
        shader.delete();
    }

    public int getBufferSize() {
        return bufferSize;
    }
}
