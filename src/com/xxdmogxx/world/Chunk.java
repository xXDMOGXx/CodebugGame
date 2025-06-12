package com.xxdmogxx.world;

import com.xxdmogxx.core.utils.Utils;

public class Chunk {
    private final int[] permutation;
    private final Tile[][] tiles;
    private static final int chunkSize = 4;
    private static final int brownianResolution = 8;

    public Chunk(int coordX, int coordY, int[] permutation) {
        this.permutation = permutation;

        tiles = new Tile[chunkSize][chunkSize];
        for (int y = 0; y < chunkSize; y++) {
            for (int x = 0; x < chunkSize; x++) {
                tiles[x][y] = genTile(x + coordX, y + coordY);
            }
        }
    }

    private Tile genTile(int x, int y) {
        double rand = Utils.fractalBrownianMotion(x, y, brownianResolution, permutation);
        int[] color = new int[]{0, (int)(rand*255), 0};
        return new Tile(Utils.generateID(), x, y, color);
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public Tile[][] getTileArrays() {
        return tiles;
    }
}
