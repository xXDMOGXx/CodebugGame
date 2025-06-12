package com.xxdmogxx.creatures;

import com.xxdmogxx.core.render.components.KeyframeGroup;
import com.xxdmogxx.core.utils.Constants;
import com.xxdmogxx.world.Map;
import com.xxdmogxx.world.Tile;
import com.xxdmogxx.world.Wall;

import java.awt.*;
import java.util.ArrayList;

public class Creature {
    private final int id;
    public final Point position;
    public byte rotation;

    public String anim;
    public KeyframeGroup group;
    public int frameCounter = 0;
    public int timingCounter = 0;

    public int cooldown = 0;

    public Creature(int id) {
        this.id = id;
        position = new Point(0, 0);
    }

    public Creature(int id, Point position, byte rotation) {
        this.id = id;
        this.position = position;
        this.rotation = rotation;
    }

    public void setAnimation(String animationName) {
        anim = animationName;
        frameCounter = 0;
    }

    public void update(Map map) {
        if (cooldown <= 0) { decideAction(map);
        } else { cooldown--; }
    }

    private void decideAction(Map map) {
        Tile adjacentTile = map.getAdjacentTile(position, rotation);
        if (adjacentTile.isEmpty()) {
            adjacentTile.setCreature(this);
            map.findTile(position.x, position.y).setCreature(null);
            switch (rotation) {
                case 0 -> position.move(1, 0);
                case 1 -> position.move(1, 1);
                case 2 -> position.move(0, 1);
                case 3 -> position.move(-1, 0);
                case 4 -> position.move(-1, -1);
                case 5 -> position.move(0, -1);
            }
        } else {
            byte oldRotation = rotation;
            while (rotation == oldRotation) {
                this.rotation = (byte) Math.round(Math.random() * 6);
            }
        }
        cooldown = 10;
    }
}
