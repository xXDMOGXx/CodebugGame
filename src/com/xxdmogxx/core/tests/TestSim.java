package com.xxdmogxx.core.tests;

import com.xxdmogxx.core.render.Model;
import com.xxdmogxx.core.ILogic;
import com.xxdmogxx.core.render.RenderManager;
import com.xxdmogxx.core.render.WindowManager;
import org.lwjgl.opengl.GL20;

public class TestSim implements ILogic {

    private float scale = 0.01f;
    private int uniID;
    private int counter = 0;
    private int numAnts = 1000000;

    private final RenderManager renderer;
    private final WindowManager window;

    private Model model;
    private final float[] translations = new float[numAnts*3];

    public TestSim() {
        renderer = new RenderManager();
        window = Launcher.getWindow();
        for (int i=0; i < numAnts; i++) {
            translations[i*3] = ((float) Math.random() * 2) - 1;
            translations[i*3+1] = ((float) Math.random() * 2) - 1;
            translations[i*3+2] = 0.0f;
        }
    }

    @Override
    public void init() throws Exception {
        window.setClearColor(1, 1, 1, 1);
        model = new Model("ant", "ant", translations);
        uniID = GL20.glGetUniformLocation(model.getId(), "scale");

    }

    @Override
    public void input() {
    }

    @Override
    public void update() {
        for (int i=0; i < numAnts; i++) {
            translations[i*3] = ((float) Math.random() * 2) - 1;
            translations[i*3+1] = ((float) Math.random() * 2) - 1;
            translations[i*3+2] = 0.0f;
        }
    }

    @Override
    public void render() {
        renderer.render(model, uniID, scale, translations);
    }

    @Override
    public void cleanup() {
        model.delete();
    }
}
