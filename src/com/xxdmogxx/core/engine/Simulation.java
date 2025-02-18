package com.xxdmogxx.core.engine;

import com.xxdmogxx.core.render.RenderManager;
import com.xxdmogxx.core.render.WindowManager;
import com.xxdmogxx.core.utils.Constants;
import com.xxdmogxx.core.utils.Utils;
import com.xxdmogxx.creatures.Creature;
import com.xxdmogxx.creatures.PopulationManager;
import com.xxdmogxx.structures.Wall;

import java.util.ArrayList;
import java.util.HashMap;

public class Simulation {

    private final RenderManager renderer;
    private final WindowManager window;
    private PopulationManager popManager;
    private final ArrayList<Wall> obstacles;

    private final HashMap<String, HashMap<String, String>> creatureNameLookup;
    private final HashMap<Integer, Creature> creatureLookup;

    public Simulation() {
        renderer = new RenderManager();
        window = Launcher.getWindow();
        creatureNameLookup = Utils.populateNameLookup("src/com/xxdmogxx/creatures/creatureNameLookup.txt");
        creatureLookup = new HashMap<>();
        obstacles = new ArrayList<>();
        obstacles.add(new Wall(0.4f, 0.2f, 0.2f, 0.3f));
        obstacles.add(new Wall(0.1f, 0.2f, -0.1f, 0.3f));
        obstacles.add(new Wall(-0.2f, 0.2f, -0.4f, 0.3f));
    }

    public void init() throws Exception {
        window.setClearColor(1, 1, 1, 1);
        popManager = new PopulationManager("baseAnt", Constants.numAnts, creatureNameLookup, creatureLookup);
    }

    public void input() {
    }

    public void update() {
        popManager.update(obstacles);
    }

    public void render() {
        popManager.render(renderer);
    }

    public void cleanup() {
        popManager.delete();
    }
}
