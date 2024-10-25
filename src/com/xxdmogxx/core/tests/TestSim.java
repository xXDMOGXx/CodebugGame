package com.xxdmogxx.core.tests;

import com.xxdmogxx.core.engine.Launcher;
import com.xxdmogxx.core.render.Model;
import com.xxdmogxx.core.engine.ILogic;
import com.xxdmogxx.core.render.RenderManager;
import com.xxdmogxx.core.render.WindowManager;
import com.xxdmogxx.core.utils.Constants;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;

public class TestSim implements ILogic {

    private final RenderManager renderer;
    private final WindowManager window;

    private Model model;
    private final float[] translations = new float[Constants.numAnts * 2];
    private final float[] rotations = new float[Constants.numAnts];

    private int animStep = 0;

    public TestSim() {
        renderer = new RenderManager();
        window = Launcher.getWindow();
        for (int i=0; i < Constants.numAnts; i++) {
            translations[i*2] = ((float) Math.random() * 2) - 1;
            translations[i*2+1] = ((float) Math.random() * 2) - 1;
            rotations[i] = (float) ((Math.random() * 4 - 2) * Math.PI);
//            translations[i*2] = 0.0f;
//            translations[i*2+1] = 0.0f;
//            rotations[i] = 0.0f;
        }
    }

    @Override
    public void init() throws Exception {
        window.setClearColor(1, 1, 1, 1);
        model = new Model("ant", "ant", "defaultAnt", "defaultAnt");
        model.setBuffers(translations, rotations, new float[]{0.0f, 0.0f, 0.0f, 0.0f});
    }

    @Override
    public void input() {
    }

    @Override
    public void update() {
        for (int i=0; i < Constants.numAnts; i++) {
            translations[i*2] = ((float) Math.random() * 2) - 1;
            translations[i*2+1] = ((float) Math.random() * 2) - 1;
            rotations[i] = (float) ((Math.random() * 4 - 2) * Math.PI);
//            translations[i*2] = 0.0f;
//            translations[i*2+1] = 0.0f;
//            rotations[i] = 0.0f;
        }
        model.updateBuffers(translations, rotations, new float[]{0.0f*animStep, 0.0f*animStep, 0.5f*animStep, 0.1f*animStep});
        animStep++;
        if (animStep > 0) animStep = -1;
    }

    @Override
    public void render() {
        renderer.render(model);
    }

    @Override
    public void cleanup() {
        model.delete();
    }
}
