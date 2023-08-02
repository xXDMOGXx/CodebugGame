import javax.swing.SwingUtilities;
import java.util.ArrayList;

enum SimulationStatus {
    RUNNING, STOPPED
}

public class Simulation implements Runnable {

    private volatile SimulationStatus status;
    public static ArrayList<CommunityHandler> communities;
    private final double timeU, timeF;
    private long initialTime;
    private int frames, ticks;
    private long timer;
    public static double deltaTime;
    public static boolean updateReady, renderReady;

    public Simulation() {
        status = SimulationStatus.STOPPED;
        communities = new ArrayList<>();
        initialTime = System.nanoTime();
        float simSpeedMulti = 5f;
        double UPS = 20d * simSpeedMulti;
        double FPS = 60d;
        timeU = 1000000000 / UPS;
        timeF = 1000000000 / FPS;
        frames = 0;
        ticks = 0;
        timer = System.currentTimeMillis();
        updateReady = false;
        renderReady = false;
    }

    @Override
    public void run() {
        status = SimulationStatus.RUNNING;
        SwingUtilities.invokeLater(Window::createWindow);

        communities.add(new CommunityHandler(new float[]{500.0f, 300.0f}, Creatures.createAntPops(1000, 1)));

        Thread update = new Thread(() -> {
            while (status == SimulationStatus.RUNNING) {
                if (updateReady) {
                    update();
                    updateReady = false;
                }
            }
        });
        Thread render = new Thread(() -> {
            while (status == SimulationStatus.RUNNING) {
                if (renderReady) {
                    Window.renderSimulation();
                }
            }
        });

        update.start();
        render.start();

        runLoop();
    }

    private void runLoop() {
        double deltaU = 0, deltaF = 0;
        while (status == SimulationStatus.RUNNING) {
            long currentTime = System.nanoTime();
            deltaU += (currentTime - initialTime) / timeU;
            deltaF += (currentTime - initialTime) / timeF;
            initialTime = currentTime;

            if (deltaU >= 1 && !updateReady) {
                deltaTime = deltaU;
                updateReady = true;
                ticks++;
                deltaU--;
            }

            if (deltaF >= 1 && !renderReady) {
                renderReady = true;
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                System.out.printf("UPS: %s, FPS: %s%n", ticks, frames);
                frames = 0;
                ticks = 0;
                timer += 1000;
            }
        }
    }

    private void update() {
        for (CommunityHandler ch : communities) {
            ch.advance();
        }
    }

    public void shutdown() {
        status = SimulationStatus.STOPPED;
        for (CommunityHandler ch : communities) {
            ch.shutdown();
        }
        Window.closeWindow();
    }

    static class CommunityHandler {

        ArrayList<Creatures.Ant> members;
        public boolean running;

        public CommunityHandler(float[] startingLocation, ArrayList<Creatures.Ant> members) {
            this.members = members;
            running = true;
            for (Creatures.Ant m : members) {
                m.position[0] = startingLocation[0];
                m.position[1] = startingLocation[1];
            }
        }

        public void advance() {
            if (running) {
                for (Creatures.Ant m : members) {
                    m.decideAction();
                }
            }
        }

        public void shutdown() {
            running = false;
            communities.remove(this);
        }
    }

    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        simulation.run();
    }
}
