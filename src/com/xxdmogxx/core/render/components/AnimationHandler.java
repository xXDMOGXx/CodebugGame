package com.xxdmogxx.core.render.components;

import com.xxdmogxx.core.render.RenderManager;
import com.xxdmogxx.creatures.Creature;
import com.xxdmogxx.creatures.PopulationManager;

import java.util.ArrayList;
import java.util.HashMap;

// Sorts creatures into their corresponding KeyframeGroups every update
public class AnimationHandler {
    private final PopulationManager popManager;
    HashMap<String, AnimationHolder> animHolderLookup;
    ArrayList<String> anims;

    public AnimationHandler(PopulationManager popManager, ArrayList<String[]> animNameLookup) throws Exception {
        animHolderLookup = new HashMap<>();
        anims = new ArrayList<>();
        this.popManager = popManager;
        for (String[] animNamePair : animNameLookup) {
            animHolderLookup.put(animNamePair[0], new AnimationHolder(animNamePair[1]));
            anims.add(animNamePair[0]);
        }
    }

    public void addCreature(Creature creature) {
        if (creature.anim == null) { creature.anim = "walk"; }
        Group group = animHolderLookup.get(creature.anim).keyframes[creature.frameCounter];
        group.creatures.add(creature);
        creature.group = group;
    }

    public void advanceAnims() {
        for (Creature creature : popManager.creatures) {
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
                Group newGroup = animHolderLookup.get(creature.anim).keyframes[creature.frameCounter];
                newGroup.creatures.add(creature);
                creature.group = newGroup;
            }
        }
    }

    public void updateTransformations() {
        for (String anim : anims) { animHolderLookup.get(anim).updateTransformations(); }
    }

    public void setBuffers() {
        for (String anim : anims) { animHolderLookup.get(anim).setBuffers(); }
    }

    public void updateBuffers() {
        for (String anim : anims) { animHolderLookup.get(anim).updateBuffers(); }
    }

    public void render(RenderManager renderer) {
        for (String anim : anims) { animHolderLookup.get(anim).render(renderer); }
    }

    public void delete() {
        for (String anim : anims) { animHolderLookup.get(anim).delete(); }
    }
}
