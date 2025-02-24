package com.xxdmogxx.core.engine;

import com.xxdmogxx.core.render.Window;
import com.xxdmogxx.core.utils.Constants;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.glfwTerminate;

public class EngineManager {
    public static boolean updateReady, renderReady;
    public static double deltaTime;

    private long timer;
    private boolean isRunning;
    private Window window;
    private GLFWErrorCallback errorCallback;
    private Simulation sim;

    private void init() throws Exception {
        GLFW.glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err).set());
        window = Launcher.getWindow();
        sim = Launcher.getSim();
        window.init();
        sim.init();
        timer = System.currentTimeMillis();
        updateReady = false;
        renderReady = false;
    }

    public void start() throws Exception {
        init();
        if (!isRunning) run();
    }

    public void run() {
        this.isRunning = true;

        double timeU = 1000000000 / (double) Constants.UPS_TARGET;
        double timeF = 1000000000 / (double) Constants.FPS_TARGET;
        long initialTime = System.nanoTime();
        double deltaU = 0, deltaF = 0;
        int frames = 0;
        int ticks = 0;

        while (isRunning) {
            long currentTime = System.nanoTime();
            deltaU += (currentTime - initialTime) / timeU;
            deltaF += (currentTime - initialTime) / timeF;
            initialTime = currentTime;

            if (deltaU >= 1 && !updateReady) {
                deltaTime = deltaU;
                updateReady = true;
                update();
                ticks++;
                deltaU--;
            }

            if (deltaF >= 1 && !renderReady) {
                renderReady = true;
                render();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                System.out.printf("UPS: %s, FPS: %s%n", ticks, frames);
                frames = 0;
                ticks = 0;
                timer += 1000;
            }

            if (window.windowShouldClose()) stop();
        }

        cleanup();
    }

    private void stop() {
        if (isRunning) {
            isRunning = false;
        }
    }

    private void input() {
        sim.input();
    }

    private void render() {
        sim.render();
        window.update();
        renderReady = false;
    }

    private void update() {
        sim.update();
        updateReady = false;
    }

    private void cleanup() {
        window.cleanup();
        sim.cleanup();
        errorCallback.free();
        // Terminate GLFW before ending the program
        glfwTerminate();
    }
}
