package com.xxdmogxx.world;

import com.xxdmogxx.core.render.components.ChunkGroup;
import com.xxdmogxx.core.utils.Utils;

import java.awt.*;
import java.util.HashMap;

public class Map {

    private final int[] permutation;
    public final HashMap<Point, Chunk> chunkLookup;
    //public final ChunkGroup chunkGroup;

    public Map() throws Exception {
        permutation = Utils.genPermutation();
        chunkLookup = new HashMap<>();
        //chunkGroup = new ChunkGroup();
    }

    public Chunk findChunk(int x, int y) {
        return chunkLookup.get(new Point(x, y));
    }

    public void loadChunk(int x, int y) {
        if (findChunk(x, y) == null) {
            Chunk chunk = new Chunk(x, y, permutation);
            chunkLookup.put(new Point(x, y), chunk);
            Tile[][] tiles = chunk.getTileArrays();
            for (int iy = 0; iy < tiles.length; iy++) {
                for (int ix = 0; ix < tiles[0].length; ix++) {
                    //chunkGroup.tiles.add(tiles[ix][iy]);
                }
            }
        }
    }

    public void unloadChunk(int x, int y) {
        Chunk chunk = findChunk(x, y);
        if (chunk != null) {
            Tile[][] tiles = chunk.getTileArrays();
            for (int iy = 0; iy < tiles.length; iy++) {
                for (int ix = 0; ix < tiles[0].length; ix++) {
                    //chunkGroup.tiles.add(tiles[ix][iy]);
                }
            }
            chunkLookup.remove(new Point(x, y));
        }
    }

    public Tile findTile(int x, int y) {
        int chunkX = x >> 2;
        int chunkY = y >> 2;
        Chunk chunk = chunkLookup.get(new Point(chunkX, chunkY));
        if (chunk == null) { return null;
        } else {
            int tileX = x & 0x3;
            int tileY = y & 0x3;
            return chunk.getTile(tileX, tileY);
        }
    }

    public Tile getAdjacentTile(Point position, byte rotation) {
        return switch (rotation) {
            case 0 -> findTile(position.x + 1, position.y);
            case 1 -> findTile(position.x + 1, position.y + 1);
            case 2 -> findTile(position.x, position.y + 1);
            case 3 -> findTile(position.x - 1, position.y);
            case 4 -> findTile(position.x - 1, position.y - 1);
            case 5 -> findTile(position.x, position.y - 1);
            default -> findTile(position.x, position.y);
        };
    }

    public int[] getPermutation() { return permutation; }
}
