package com.xxdmogxx.core.utils;

import org.lwjgl.system.MemoryUtil;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class Utils {

    public static FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data).flip();
        return buffer;
    }

    public static IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
        buffer.put(data).flip();
        return buffer;
    }


    public static float[] unpackFloatArrayList(ArrayList<Float> floatArrayList) {
        if (floatArrayList == null) return new float[] {};
        float[] floatArray = new float[floatArrayList.size()];
        int counter = 0;
        for (float f : floatArrayList) {
            floatArray[counter] = f;
            counter++;
        }
        return floatArray;
    }
}
