package com.xxdmogxx.creatures;

import com.xxdmogxx.core.render.components.Group;
import com.xxdmogxx.core.render.RenderManager;
import com.xxdmogxx.core.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class PopulationManager {
    private final ArrayList<Creature> creatures;
    private final HashMap<Integer, Creature> creatureLookup;
    private final Group group;

    private final float[] translations;
    private final float[] rotations;

    private int index = 0;

    public PopulationManager(String creatureName, int initialSize, HashMap<String, HashMap<String, String>> creatureNameLookup, HashMap<Integer, Creature> creatureLookup) throws Exception {
        this.creatureLookup = creatureLookup;
        creatures = new ArrayList<>();
        translations = new float[initialSize*2];
        rotations = new float[initialSize];
        group = new Group(creatureNameLookup.get(creatureName));
        spawn(initialSize);
    }

    public void spawn(int amount) {
        for (int i = 0; i < amount; i++) {
            int id = Utils.generateID();
            Creature creature = new Creature(id, index);
            creatures.add(creature);
            creatureLookup.put(id, creature);
            translations[index*2] = creature.getPosition()[0];
            translations[index*2+1] = creature.getPosition()[1];
            rotations[index] = creature.getRotation();
            index++;

            creature.setTarget((float) (Math.random() * 2 * Math.PI));
        }
        group.setBuffers(translations, rotations);
    }

    public void update() {
        for (Creature creature : creatures) {
            creature.update();
            translations[creature.getIndex()*2] = creature.getPosition()[0];
            translations[creature.getIndex()*2+1] = creature.getPosition()[1];
            rotations[creature.getIndex()] = creature.getRotation();
        }
        group.updateBuffers(translations, rotations);
    }

    public void render(RenderManager renderer) {
        renderer.render(group);
    }

    public void delete() {
        group.delete();
    }
}
