package com.xxdmogxx.core.utils;

import org.lwjgl.system.MemoryUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Utils {
    private static final Random randomGen = new Random();

    public static FloatBuffer storeDataInBuffer(float[] data) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data).flip();
        return buffer;
    }

    public static IntBuffer storeDataInBuffer(int[] data) {
        IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
        buffer.put(data).flip();
        return buffer;
    }

    public static void updateDataInBuffer(FloatBuffer buffer, float[] newData) {
        buffer.clear();
        buffer.put(newData).flip();
    }

    public static void updateDataInBuffer(IntBuffer buffer, int[] newData) {
        buffer.clear();
        buffer.put(newData).flip();
    }

    public static float[] unpackArrayList(ArrayList<Float> arrayList) {
        if (arrayList == null) return new float[] {};
        float[] floatArray = new float[arrayList.size()];
        int counter = 0;
        for (float f : arrayList) {
            floatArray[counter] = f;
            counter++;
        }
        return floatArray;
    }

    public static int generateID() {
        return randomGen.nextInt();
    }

    public static float[] readObjVertices(String filePath) {
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
        return unpackArrayList(vertices);
    }

    public static int[] readObjIndices(String filePath) {
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
}
