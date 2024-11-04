package com.xxdmogxx.core.tests;

import com.xxdmogxx.core.engine.Launcher;
import com.xxdmogxx.core.engine.ILogic;
import com.xxdmogxx.core.render.RenderManager;
import com.xxdmogxx.core.render.WindowManager;
import com.xxdmogxx.core.utils.Constants;
import com.xxdmogxx.core.utils.Utils;
import com.xxdmogxx.creatures.Creature;
import com.xxdmogxx.creatures.PopulationManager;
import com.xxdmogxx.structures.Wall;

import java.util.ArrayList;
import java.util.HashMap;

public class TestSim implements ILogic {

    private final RenderManager renderer;
    private final WindowManager window;
    private PopulationManager popManager;
    private final ArrayList<Wall> obstacles;

    private final HashMap<String, HashMap<String, String>> creatureNameLookup;
    private final HashMap<Integer, Creature> creatureLookup;

    public TestSim() {
        renderer = new RenderManager();
        window = Launcher.getWindow();
        creatureNameLookup = Utils.populateNameLookup("src/com/xxdmogxx/creatures/nameLookup.txt");
        creatureLookup = new HashMap<>();
        obstacles = new ArrayList<>();
        obstacles.add(new Wall(0.2f, -0.3f, 0.1f, 0.3f));
        obstacles.add(new Wall(0.1f, 0.2f, -0.6f, 0.3f));
        obstacles.add(new Wall(-0.3f, 0.0f, -0.6f, 0.1f));
    }

    @Override
    public void init() throws Exception {
        window.setClearColor(1, 1, 1, 1);
        popManager = new PopulationManager("baseAnt", Constants.numAnts, creatureNameLookup, creatureLookup);
    }

    @Override
    public void input() {
    }

    @Override
    public void update() {
        popManager.update(obstacles);
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
