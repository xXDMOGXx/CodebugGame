package com.xxdmogxx.core.render.components;

import com.xxdmogxx.core.utils.Utils;

public class Animation {
    private
    private float[] keyframes;

    public Animation(String animPath) {
        float[] vertices = Utils.readAnimFiles(animPath);
    }
}
