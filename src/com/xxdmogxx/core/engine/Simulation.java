package com.xxdmogxx.core.engine;

import com.xxdmogxx.core.render.Camera;
import com.xxdmogxx.core.render.RenderManager;
import com.xxdmogxx.core.render.Window;
import com.xxdmogxx.core.utils.Constants;
import com.xxdmogxx.core.utils.Utils;
import com.xxdmogxx.creatures.Creature;
import com.xxdmogxx.creatures.PopulationManager;
import com.xxdmogxx.world.Map;
import com.xxdmogxx.world.Wall;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Simulation {

    private final RenderManager renderer;
    private final Window window;
    private PopulationManager popManager;
    public final Map map;
    public final Camera camera;

    private final HashMap<String, HashMap<String, String>> creatureNameLookup;
    private final HashMap<Integer, Creature> creatureLookup;

    public Simulation() throws Exception {
        renderer = new RenderManager();
        window = Launcher.getWindow();
        creatureNameLookup = Utils.populateNameLookup("src/com/xxdmogxx/creatures/creatureNameLookup.txt");
        creatureLookup = new HashMap<>();
        map = new Map();
        camera = new Camera();
    }

    public void init() throws Exception {
        window.setClearColor(1, 1, 1, 1);
        map.loadChunk(-1, -1);
        map.loadChunk(-1, 0);
        map.loadChunk(-1, 1);
        map.loadChunk(0, -1);
        map.loadChunk(0, 0);
        map.loadChunk(0, 1);
        map.loadChunk(1, -1);
        map.loadChunk(1, 0);
        map.loadChunk(1, 1);
        popManager = new PopulationManager("baseAnt", creatureNameLookup, creatureLookup);
        popManager.setLocation(new Point(0, 0));
        popManager.spawn(Constants.numAnts);
    }

    public void input() {

    }

    public void update() {
        popManager.update(camera, map);
    }

    public void render() {
        //renderer.render(map.chunkGroup);
        popManager.render(renderer);
    }

    public void cleanup() {
        popManager.delete();
    }
}
