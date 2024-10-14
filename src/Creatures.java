import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Creatures {

    public static float[][] readCreatureFile(String path) {
        try {
            File creatureFile = new File(path);
            Scanner reader = new Scanner(creatureFile);
            if (reader.hasNextLine()) {
                int numReads = Integer.parseInt(reader.nextLine());
                float[][] points = new float[numReads][2];
                for (int i = 0; i < numReads; i++) {
                    String[] strPoint = reader.nextLine().split(",");
                    float[] point = new float[] {Float.parseFloat(strPoint[0]), Float.parseFloat(strPoint[1])};
                    points[i] = point;
                }
                reader.close();
                return points;
            }
            return new float[0][0];
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: " + path + " doesn't exist");
            e.printStackTrace();
            return new float[0][0];
        }
    }

    public static double randomDirection() {
        Random r = new Random();
        int min = 0;
        int max = 360;
        return min + r.nextDouble() * (max - min);
    }

    public static DataTypes.Vector[][] calculateVectors(float[] c, float[][][] anims) {
        DataTypes.Vector[][] vectors = new DataTypes.Vector[anims.length][anims[0].length];
        for (int i = 0; i < anims.length; i++) {
            for (int i2 = 0; i2 < anims[i].length; i2++) {
                float a = anims[i][i2][1] - c[1];
                float b = anims[i][i2][0] - c[0];
                DataTypes.Vector vector = new DataTypes.Vector().setXY(b, a).calcMD();
                vectors[i][i2] = vector;
            }
        }
        return vectors;
    }

    public static ArrayList<Ant> createAntPops(int amount, int sizeMulti) {
        ArrayList<Ant> ants = new ArrayList<>();
        float[] pivot = new float[] {11*sizeMulti, 8*sizeMulti};
        float[][] antAnim1 = readCreatureFile("C:\\Users\\xXDMOGXx\\IdeaProjects\\SimulationTests\\src\\antAnim1.txt");
        float[][] antAnim2 = readCreatureFile("C:\\Users\\xXDMOGXx\\IdeaProjects\\SimulationTests\\src\\antAnim2.txt");
        float[][] antAnim3 = readCreatureFile("C:\\Users\\xXDMOGXx\\IdeaProjects\\SimulationTests\\src\\antAnim3.txt");
        float[][][] antAnims = new float[][][] {antAnim1, antAnim2, antAnim3};
        for (float[][] a : antAnims) {
            for (float[] p : a) {
                p[0] *= sizeMulti;
                p[1] *= sizeMulti;
            }
        }
        DataTypes.Vector[][] antVectors = calculateVectors(pivot, antAnims);
        for (int i = 0; i < amount; i++)  {
            Ant newAnt = new Ant(antVectors, sizeMulti, new float[] {0.0f, 0.0f}, randomDirection());
            ants.add(newAnt);
        }
        return ants;
    }

    static class BaseCreature {

        public DataTypes.Vector[][] vectors;
        public int[][] points;
        public boolean alive;
        public boolean decomposed;
        public float[] position;
        public final float[] velocity;
        public int size;
        public float speed;
        public double movDir;
        public double lookDir;
        public double maxTurn;
        public int animReset;
        public int animTime;
        public int animStep;
        public int currentAnim;
        public boolean pointsChanged;

        public BaseCreature(DataTypes.Vector[][] vectors, int sizeMulti, float[] pos, double dir) {
            alive = true;
            decomposed = false;
            pointsChanged = false;
            position = pos;
            velocity = new float[] {0.0f, 0.0f};
            this.vectors = vectors;
            this.size = sizeMulti;
            this.movDir = dir;
        }

        public void move() {
            if (Simulation.deltaTime != 0) {
                double direction = Math.toRadians(movDir);
                velocity[0] = (float) (speed * Math.cos(direction));
                velocity[1] = (float) (speed * Math.sin(direction));
                double deltaVX = velocity[0] / Simulation.deltaTime;
                double deltaVY = velocity[1] / Simulation.deltaTime;
                position[0] += (float) deltaVX;
                position[1] += (float) deltaVY;
                pointsChanged = true;
            }
        }

        public void turnTowardsMove() {
            double mod = (movDir - lookDir + 360) % 360;
            if (mod < 180) {
                lookDir += maxTurn / Simulation.deltaTime;
            } else if (mod > 180) {
                lookDir -= maxTurn / Simulation.deltaTime;
            } else {
                lookDir = maxTurn;
            }
            if (lookDir < 0) {
                lookDir += 360;
            } else if (lookDir >= 360) {
                lookDir -= 360;
            }
            pointsChanged = true;
        }

        public void calculatePoints() {
            DataTypes.Vector[] v = vectors[currentAnim];
            int numPoints = v.length;
            int[] x = new int[numPoints];
            int[] y = new int[numPoints];
            for (int i = 0; i < numPoints; i++) {
                double nA = v[i].d + Math.toRadians(lookDir);
                x[i] = Math.round((float)(v[i].m * Math.cos(nA)) + position[0]);
                y[i] = Math.round((float)(v[i].m * Math.sin(nA)) + position[1]);
            }
            points = new int[][] {x, y};
        }
    }

    static class Ant extends BaseCreature {

        public Ant(DataTypes.Vector[][] vectors, int sizeMulti, float[] pos, double dir) {
            super(vectors, sizeMulti, pos, dir);
            float speedMulti = 1;
            this.speed = speedMulti*sizeMulti;
            this.lookDir = 0.0d;
            this.maxTurn = 5.0d;
            this.animReset = 2;
            this.animTime = 0;
            this.animStep = 1;
            this.currentAnim = 1;
        }

        public void decideAction() {
            if (alive) {
                pointsChanged = false;
                if (changeTarget()) {
                    movDir = randomDirection();
                }
                move();
                if (lookDir != movDir) {
                    turnTowardsMove();
                }
                if (pointsChanged) {
                    anim();
                    calculatePoints();
                }
            }
        }

        public boolean changeTarget() {
            if (position[0] < 0) {
                position[0] = 0;
                return true;
            } else if (position[0] > Window.width) {
                position[0] = Window.width;
                return true;
            }
            if (position[1] < 0) {
                position[1] = 0;
                return true;
            } else if (position[1] > Window.height) {
                position[1] = Window.height;
                return true;
            }
            Random r = new Random();
            float threshold = 0.005f;
            return r.nextDouble() < threshold;
        }

        public void anim() {
            animTime += 1;
            if (animTime >= animReset) {
                animTime = 0;
                switch (animStep) {
                    case 1, 3 -> currentAnim = 0;
                    case 2 -> currentAnim = 1;
                    case 4 -> currentAnim = 2;
                }
                animStep += 1;
                if (animStep > 4) {
                    animStep = 1;
                }
            }
        }
    }
}
