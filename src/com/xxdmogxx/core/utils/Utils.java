package com.xxdmogxx.core.utils;

import org.lwjgl.system.MemoryUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;

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

    private static Scanner constructScanner(String filePath) {
        File file = new File(filePath);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return scanner;
    }

    public static float[] readObjVertices(String filePath, boolean ignoreZ) {
        Scanner scanner = constructScanner(filePath);

        ArrayList<Float> vertices = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            String desc = data.substring(0, 1);
            if (desc.equals("v")) {
                String valueLine = data.substring(2);
                String[] values = valueLine.split(" ");
                vertices.add(Float.parseFloat(values[0]));
                vertices.add(Float.parseFloat(values[1]));
            }
        }
        scanner.close();
        return unpackArrayList(vertices);
    }

    public static int[] readObjIndices(String filePath) {
        Scanner scanner = constructScanner(filePath);

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
        scanner.close();
        return indices.stream().filter(Objects::nonNull).mapToInt(i -> i).toArray();
    }

    public static ArrayList<String[]> readKeyValuePairs(String filePath) {
        Scanner scanner = constructScanner(filePath);

        ArrayList<String[]> pairStorage = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            String[] elements = data.split(" ");
            pairStorage.add(elements);
        }
        scanner.close();
        return pairStorage;
    }

    public static HashMap<String, String> constructCreatureLookup(String filePath) {
        Scanner scanner = constructScanner(filePath);

        HashMap<String, String> lookup = new HashMap<>();
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            String[] elements = data.split(" ");
            lookup.put(elements[0], elements[1]);
        }
        scanner.close();
        return lookup;
    }

    public static HashMap<String, HashMap<String, String>> populateNameLookup(String filePath) {
        Scanner scanner = constructScanner(filePath);
        HashMap<String, HashMap<String, String>> nameLookup = new HashMap<>();
        ArrayList<String[]> namePairs = readKeyValuePairs(filePath);
        for (String[] pair : namePairs) {
            nameLookup.put(pair[0], constructCreatureLookup(pair[1]));
        }
        scanner.close();
        return nameLookup;
    }
}
