package com.xxdmogxx.core.render;

import com.xxdmogxx.core.render.buffers.IBO;
import com.xxdmogxx.core.render.buffers.VAO;
import com.xxdmogxx.core.render.buffers.VBO;
import com.xxdmogxx.core.utils.Constants;
import com.xxdmogxx.core.utils.Utils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL33;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Model {

    private final Shader shader;
    private final VAO vertexArray;
    private final VBO vertexBuffer;
    private final VBO translationBuffer;
    private final IBO indexBuffer;
    private final int indexCount;

    public Model(String modelName, String shaderName, float[] translations) throws Exception {
        shader = new Shader(shaderName);


        String fileName = Constants.DEFAULT_RESOURCE_PATH + "models/" + modelName + ".obj";
        float[] vertices = readObjVertices(fileName);
        int[] indices = readObjIndices(fileName);
        indexCount = indices.length;

        vertexArray = new VAO();
        vertexBuffer = new VBO(vertices);
        vertexArray.linkAttribute(0, 3, GL11.GL_FLOAT, 0, 0);
        translationBuffer = new VBO(translations);
        vertexArray.linkAttribute(1, 3, GL11.GL_FLOAT, 0, 0);
        GL33.glVertexAttribDivisor(1, 1);
        indexBuffer = new IBO(indices);
        vertexArray.unbind();
        vertexBuffer.unbind();
        translationBuffer.unbind();
        indexBuffer.unbind();
    }

    private static float[] readObjVertices(String filePath) {
        File objFile = new File(filePath);
        Scanner scanner;
        try {
            scanner = new Scanner(objFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new float[] {};
        }

        ArrayList<Float> vertices = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            String desc = data.substring(0, 1);
            if (desc.equals("v")) {
                String valueLine = data.substring(2);
                String[] values = valueLine.split(" ");
                vertices.add(Float.parseFloat(values[0]));
                vertices.add(Float.parseFloat(values[1]));
                vertices.add(Float.parseFloat(values[2]));
            }
        }
        return Utils.unpackFloatArrayList(vertices);
    }

    private static int[] readObjIndices(String filePath) {
        File objFile = new File(filePath);
        Scanner scanner;
        try {
            scanner = new Scanner(objFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new int[] {};
        }

        ArrayList<Integer> indices = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            String desc = data.substring(0, 1);
            if (desc.equals("f")) {
                String valueLine = data.substring(2);
                String[] values = valueLine.split(" ");
                indices.add(Integer.parseInt(values[0]) - 1);
                indices.add(Integer.parseInt(values[1]) - 1);
                indices.add(Integer.parseInt(values[2]) - 1);
            }
        }
        return indices.stream().filter(Objects::nonNull).mapToInt(i -> i).toArray();
    }

    private void bind() {
        vertexArray.bind();
    }

    private void unbind() {
        vertexArray.unbind();
    }

    public void enable(float[] translations) {
        translationBuffer.update(translations);
        shader.enable();
        bind();
    }

    public void disable() {
        shader.disable();
        unbind();
    }

    public void delete() {
        vertexArray.delete();
        vertexBuffer.delete();
        translationBuffer.delete();
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
