package com.xxdmogxx.world;

import com.xxdmogxx.creatures.Creature;

public class Tile {
    private final int id;
    private final int x;
    private final int y;
    private Material floor;
    private Resource wall;
    private Creature creature;
    private final int[] color;
    private boolean revealed;

    public Tile(int id, int x, int y, int[] color) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.color = color;
        revealed = false;
    }

    public void reveal() {
        revealed = true;
    }

    public int getId() { return id; }
    public int getX() { return x; }
    public int getY() { return y; }
    public Creature getCreature() { return creature; }
    public Material getFloor() { return floor; }
    public Resource getWall() { return wall; }
    public int[] getColor() { return color; }
    public boolean isRevealed() { return revealed; }
    public boolean isEmpty() { return wall == null && creature == null; }

    public void setFloor(Material floor) { this.floor = floor; }
    public void setWall(Resource wall) { this.wall = wall; }
    public void setCreature(Creature creature) { this.creature = creature; }
}
