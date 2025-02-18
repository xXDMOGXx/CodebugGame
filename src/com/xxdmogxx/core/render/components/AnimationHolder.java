package com.xxdmogxx.core.render.components;

import com.xxdmogxx.core.render.RenderManager;
import com.xxdmogxx.core.utils.Utils;

import java.util.Arrays;
import java.util.HashMap;

public class AnimationHolder {
    public String animPath;
    public float[][] vertices;
    public int[] indices;
    public final int[] timings;
    public final Group[] keyframes;
    public int numKeyframes;

    public AnimationHolder(String animPath) throws Exception {
        this.animPath = animPath;
        // Creates a lookup holding all the extra animation data
        HashMap<String, Integer> dataLookup = Utils.populateDataLookup(animPath + "AnimData.txt");
        // Looks up the location of the animation keyframes, and condenses all the vertex data into a list of lists
        vertices = Utils.readAnimFiles(animPath, dataLookup);
        indices = Utils.readObjIndices(animPath + "0.obj");
        // Temp variable to prevent constant lookups of the number of keyframes
        numKeyframes = dataLookup.get("numKeyframes");

        // Queries the lookup for timing data, and condenses it into a list
        // Also creates a KeyframeGroup for every forward pair of keyframes (including wrap around)
        timings = new int[numKeyframes];
        keyframes = new Group[numKeyframes];
        for (int i = 0; i < numKeyframes; i++) {
            timings[i] = dataLookup.get("keyframeTiming" + i);

            Group keyframe;
            // Does the wrap around check
            if (i == numKeyframes - 1) { keyframe = new Group(this, i, 0, timings[i]);
            } else { keyframe = new Group(this, i, i + 1, timings[i]); }

            keyframes[i] = keyframe;
        }
    }

    public void updateTransformations() {
        for (Group keyframe : keyframes) { keyframe.updateArrays(); }
    }

    public void setBuffers() {
        for (Group keyframe : keyframes) { keyframe.setBuffers(); }
    }

    public void updateBuffers() {
        for (Group keyframe : keyframes) { keyframe.updateBuffers(); }
    }

    public void render(RenderManager renderer) {
        for (Group keyframe : keyframes) {
            if (!keyframe.creatures.isEmpty()) { renderer.render(keyframe); }
        }
    }

    public void delete() {
        for (Group keyframe : keyframes) { keyframe.delete(); }
    }
}
