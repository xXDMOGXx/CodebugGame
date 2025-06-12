package com.xxdmogxx.core.utils;

import org.lwjgl.system.MemoryUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;

public class Utils {

    static final int[] permutation = { 151,160,137,91,90,15,
            131,13,201,95,96,53,194,233,7,225,140,36,103,30,69,142,8,99,37,240,21,10,23,
            190, 6,148,247,120,234,75,0,26,197,62,94,252,219,203,117,35,11,32,57,177,33,
            88,237,149,56,87,174,20,125,136,171,168, 68,175,74,165,71,134,139,48,27,166,
            77,146,158,231,83,111,229,122,60,211,133,230,220,105,92,41,55,46,245,40,244,
            102,143,54, 65,25,63,161, 1,216,80,73,209,76,132,187,208, 89,18,169,200,196,
            135,130,116,188,159,86,164,100,109,198,173,186, 3,64,52,217,226,250,124,123,
            5,202,38,147,118,126,255,82,85,212,207,206,59,227,47,16,58,17,182,189,28,42,
            223,183,170,213,119,248,152, 2,44,154,163, 70,221,153,101,155,167, 43,172,9,
            129,22,39,253, 19,98,108,110,79,113,224,232,178,185, 112,104,218,246,97,228,
            251,34,242,193,238,210,144,12,191,179,162,241, 81,51,145,235,249,14,239,107,
            49,192,214, 31,181,199,106,157,184, 84,204,176,115,121,50,45,127, 4,150,254,
            138,236,205,93,222,114,67,29,24,72,243,141,128,195,78,66,215,61,156,180
    };

    private static final Random randomGen = new Random();

    public static int[] genPermutation() {
        final int[] p = new int[512];
        for (int i=0; i < 256 ; i++) { p[256+i] = p[i] = permutation[i]; }
        return p;
    }

    public static double noise(int[] permutation, double x, double y, double z) {
        int X = (int)Math.floor(x) & 255;                  // FIND UNIT CUBE THAT
        int Y = (int)Math.floor(y) & 255;                  // CONTAINS POINT.
        int Z = (int)Math.floor(z) & 255;
        x -= Math.floor(x);                                // FIND RELATIVE X,Y,Z
        y -= Math.floor(y);                                // OF POINT IN CUBE.
        z -= Math.floor(z);
        double u = fade(x);                                // COMPUTE FADE CURVES
        double v = fade(y);                                // FOR EACH OF X,Y,Z.
        double w = fade(z);
        int A = permutation[X]+Y;
        int AA = permutation[A]+Z;
        int AB = permutation[A+1]+Z;      // HASH COORDINATES OF
        int B = permutation[X+1]+Y;
        int BA = permutation[B]+Z;
        int BB = permutation[B+1]+Z;      // THE 8 CUBE CORNERS,

        return lerp(w, lerp(v, lerp(u, grad(permutation[AA], x, y, z),  // AND ADD
                                grad(permutation[BA], x-1, y, z)), // BLENDED
                        lerp(u, grad(permutation[AB], x, y-1, z),  // RESULTS
                                grad(permutation[BB], x-1, y-1, z))),// FROM  8
                lerp(v, lerp(u, grad(permutation[AA+1], x, y, z-1),  // CORNERS
                                grad(permutation[BA+1], x-1, y, z-1)), // OF CUBE
                        lerp(u, grad(permutation[AB+1], x, y-1, z-1),
                                grad(permutation[BB+1], x-1, y-1, z-1))));
    }

    private static double fade(double t) { return t * t * t * (t * (t * 6 - 15) + 10); }

    private static double lerp(double t, double a, double b) { return a + t * (b - a); }

    private static double grad(int hash, double x, double y, double z) {
        int h = hash & 15;                      // CONVERT LO 4 BITS OF HASH CODE
        double u = h<8 ? x : y, v = h<4 ? y : h==12||h==14 ? x : z; // INTO 12 GRADIENT DIRECTIONS.
        return ((h&1) == 0 ? u : -u) + ((h&2) == 0 ? v : -v);
    }

    public static double fractalBrownianMotion(int x, int y, int resolution, int[] permutation) {
        double result = 0.0;
        double amplitude = 1.0;
        double frequency = 0.005;

        for (int octave = 0; octave < resolution; octave++) {
		double n = amplitude * noise(permutation, x * frequency, y * frequency, 0);
            result += n;

            amplitude *= 0.5;
            frequency *= 2.0;
        }

        return result;
    }

    public static FloatBuffer storeDataInBuffer(float[] data) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data).flip();
        return buffer;
    }

    public static ByteBuffer storeDataInBuffer(byte[] data) {
        ByteBuffer buffer = MemoryUtil.memAlloc(data.length);
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

    public static void updateDataInBuffer(ByteBuffer buffer, byte[] newData) {
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
                if (!ignoreZ) { vertices.add(Float.parseFloat(values[2])); }
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

    public static float[][] readAnimFiles(String dataPath, HashMap<String, Integer> dataLookup) {
        float[][] keyframes = new float[dataLookup.get("numKeyframes")][dataLookup.get("numVertices")*3];
        for (int i = 0; i < dataLookup.get("numKeyframes"); i++) {
            String keyframePath = dataPath + i + ".obj";
            Scanner scanner = constructScanner(keyframePath);
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
            scanner.close();
            keyframes[i] = unpackArrayList(vertices);
        }
        return keyframes;
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

    public static HashMap<String, Integer> populateDataLookup(String filePath) {
        Scanner scanner = constructScanner(filePath);
        HashMap<String, Integer> dataLookup = new HashMap<>();
        ArrayList<String[]> namePairs = readKeyValuePairs(filePath);
        for (String[] pair : namePairs) {
            if (!Objects.equals(pair[0], "")) {
                dataLookup.put(pair[0], Integer.parseInt(pair[1]));
            }
        }
        scanner.close();
        return dataLookup;
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
