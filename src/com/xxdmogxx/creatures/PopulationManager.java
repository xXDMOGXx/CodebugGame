package com.xxdmogxx.creatures;

import com.xxdmogxx.core.render.Camera;
import com.xxdmogxx.core.render.RenderManager;
import com.xxdmogxx.core.render.components.AnimationHolder;
import com.xxdmogxx.core.render.components.KeyframeGroup;
import com.xxdmogxx.core.utils.Utils;
import com.xxdmogxx.structures.Wall;

import java.util.ArrayList;
import java.util.HashMap;

public class PopulationManager {

    public final ArrayList<Creature> creatures;
    private final HashMap<Integer, Creature> creatureLookup;
    HashMap<String, AnimationHolder> animHolderLookup;
    ArrayList<String> anims;

    private int index = 0;

    public PopulationManager(String creatureName, int initialSize, HashMap<String, HashMap<String, String>> creatureNameLookup, HashMap<Integer, Creature> creatureLookup) throws Exception {
        this.creatureLookup = creatureLookup;
        creatures = new ArrayList<>();
        animHolderLookup = new HashMap<>();
        anims = new ArrayList<>();
        ArrayList<String[]> animNameLookup = Utils.readKeyValuePairs(creatureNameLookup.get(creatureName).get("animations"));
        for (String[] animNamePair : animNameLookup) {
            animHolderLookup.put(animNamePair[0], new AnimationHolder(animNamePair[1]));
            anims.add(animNamePair[0]);
        }


        for (String anim : anims) { animHolderLookup.get(anim).setCamPos(new float[]{0.0f, 0.0f, 0.0f}); }
        spawn(initialSize);
    }

    public void advanceAnims() {
        for (Creature creature : creatures) {
            creature.timingCounter++;
            // Checks whether the creature is finished with the current animation keyframe
            if (creature.timingCounter >= animHolderLookup.get(creature.anim).timings[creature.frameCounter]) {
                creature.timingCounter = 0;
                // Remove creature from current KeyframeGroup
                animHolderLookup.get(creature.anim).keyframes[creature.frameCounter].creatures.remove(creature);
                creature.frameCounter++;
                // If the creature reaches the end of the animation, loop it back to the start
                if (creature.frameCounter >= animHolderLookup.get(creature.anim).numKeyframes) {
                    creature.frameCounter = 0;
                }
                // Link the creature with its new group
                KeyframeGroup newGroup = animHolderLookup.get(creature.anim).keyframes[creature.frameCounter];
                newGroup.creatures.add(creature);
                creature.group = newGroup;
            }
        }
    }

    public void update(Camera camera, ArrayList<Wall> obstacles) {
        for (Creature creature : creatures) {
            creature.update(obstacles);

            AnimationHolder currentHolder = animHolderLookup.get(creature.anim);
            creature.timingCounter++;
            // Checks whether the creature is finished with the current animation keyframe
            if (creature.timingCounter >= currentHolder.timings[creature.frameCounter]) {
                creature.timingCounter = 0;
                // Remove creature from current KeyframeGroup
                currentHolder.keyframes[creature.frameCounter].creatures.remove(creature);
                creature.frameCounter++;

                // If the creature reaches the end of the animation, loop it back to the start
                if (creature.frameCounter >= currentHolder.numKeyframes) { creature.frameCounter = 0; }

                // Link the creature with its new group
                KeyframeGroup newGroup = currentHolder.keyframes[creature.frameCounter];
                newGroup.creatures.add(creature);
                creature.group = newGroup;
            }
        }

        // Go through each KeyframeGroup and update the arrays and buffers with the values of the new creatures
        for (String anim : anims) {
            animHolderLookup.get(anim).updateArrays();
            animHolderLookup.get(anim).updateBuffers();
            animHolderLookup.get(anim).updateCamPos(camera.position);
        }
    }

    public void setBuffers() {
        for (String anim : anims) { animHolderLookup.get(anim).setBuffers(); }
    }

    public void spawn(int amount) {
        for (int i = 0; i < amount; i++) {
            int id = Utils.generateID();
            Creature creature = new Creature(id);
            creatures.add(creature);
            creatureLookup.put(id, creature);

            creature.setTarget((float) (Math.random() * 2 * Math.PI));
            creature.snapRotationToTarget();
            creature.timingCounter = (int) (Math.random() * 10);
            creature.frameCounter = (int) Math.round(Math.random());

            if (creature.anim == null) { creature.anim = "walk"; }
            KeyframeGroup group = animHolderLookup.get(creature.anim).keyframes[creature.frameCounter];
            group.creatures.add(creature);
            creature.group = group;

            index++;
        }
        setBuffers();
    }

    public void render(RenderManager renderer) {
        renderer.prepareRender();
        for (String anim : anims) { animHolderLookup.get(anim).render(renderer); }
    }

    public void delete() {
        for (String anim : anims) { animHolderLookup.get(anim).delete(); }
    }
}
