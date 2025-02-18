package com.xxdmogxx.creatures;

import com.xxdmogxx.core.render.RenderManager;
import com.xxdmogxx.core.render.components.AnimationHandler;
import com.xxdmogxx.core.utils.Utils;
import com.xxdmogxx.structures.Wall;

import java.util.ArrayList;
import java.util.HashMap;

public class PopulationManager {

    public final ArrayList<Creature> creatures;
    private final HashMap<Integer, Creature> creatureLookup;
    private final AnimationHandler animHandler;

    private int index = 0;

    public PopulationManager(String creatureName, int initialSize, HashMap<String, HashMap<String, String>> creatureNameLookup, HashMap<Integer, Creature> creatureLookup) throws Exception {
        this.creatureLookup = creatureLookup;
        creatures = new ArrayList<>();
        animHandler = new AnimationHandler(this, Utils.readKeyValuePairs(creatureNameLookup.get(creatureName).get("animations")));

        spawn(initialSize);
    }

    public void spawn(int amount) {
        for (int i = 0; i < amount; i++) {
            int id = Utils.generateID();
            Creature creature = new Creature(id, index);
            creatures.add(creature);
            creatureLookup.put(id, creature);

            creature.setTarget((float) (Math.random() * 2 * Math.PI));
            creature.snapRotationToTarget();
            creature.timingCounter = (int) (Math.random() * 10);
            creature.frameCounter = (int) Math.round(Math.random());

            animHandler.addCreature(creature);

            index++;
        }
        animHandler.setBuffers();
    }

    public void update(ArrayList<Wall> obstacles) {
        for (Creature creature : creatures) { creature.update(obstacles); }
        animHandler.advanceAnims();
        animHandler.updateTransformations();
        animHandler.updateBuffers();
    }

    public void render(RenderManager renderer) {
        renderer.prepareRender();
        animHandler.render(renderer);
    }

    public void delete() {
        animHandler.delete();
    }
}
