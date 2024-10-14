package com.xxdmogxx.core.tests;

import com.xxdmogxx.core.render.Model;
import com.xxdmogxx.core.ILogic;
import com.xxdmogxx.core.render.RenderManager;
import com.xxdmogxx.core.render.WindowManager;
import org.lwjgl.opengl.GL20;

public class TestSim implements ILogic {

    private float scale = 1.0f;
    private int uniID;
    private int counter = 0;

    private final RenderManager renderer;
    private final WindowManager window;

    private Model model;

    public TestSim() {
        renderer = new RenderManager();
        window = Launcher.getWindow();
    }


    @Override
    public void init() throws Exception {
        window.setClearColor(1, 1, 1, 1);
        model = new Model("ant", "default");
        uniID = GL20.glGetUniformLocation(model.getId(), "scale");
    }

    @Override
    public void input() {
    }

    @Override
    public void update() {
        scale = counter;
        counter++;
    }

    @Override
    public void render() {
        renderer.render(model, uniID, scale);
    }

    @Override
    public void cleanup() {
        model.delete();
    }
}
