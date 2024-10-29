package com.xxdmogxx.core.tests;

import com.xxdmogxx.core.engine.Launcher;
import com.xxdmogxx.core.engine.ILogic;
import com.xxdmogxx.core.render.RenderManager;
import com.xxdmogxx.core.render.WindowManager;
import com.xxdmogxx.core.utils.Constants;
import com.xxdmogxx.creatures.Creature;
import com.xxdmogxx.creatures.PopulationManager;

import java.util.HashMap;

public class TestSim implements ILogic {

    private final RenderManager renderer;
    private final WindowManager window;
    private PopulationManager popManager;

    private final HashMap<Integer, Creature> creatureLookup;

    public TestSim() {
        renderer = new RenderManager();
        window = Launcher.getWindow();
        creatureLookup = new HashMap<>();
    }

    @Override
    public void init() throws Exception {
        window.setClearColor(1, 1, 1, 1);
        popManager = new PopulationManager(Constants.numAnts, creatureLookup);
    }

    @Override
    public void input() {
    }

    @Override
    public void update() {
        popManager.update();
    }

    @Override
    public void render() {
        popManager.render(renderer);
    }

    @Override
    public void cleanup() {
        popManager.delete();
    }
}
