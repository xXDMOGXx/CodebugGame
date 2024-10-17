package com.xxdmogxx.core;

import com.xxdmogxx.core.render.WindowManager;
import com.xxdmogxx.core.tests.Launcher;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.glfwTerminate;

public class EngineManager {
    public static final int targetFPS = 120;
    public static final int targetUPS = 20;
    public static boolean updateReady, renderReady;
    public static double deltaTime;
    private long timer;

    private boolean isRunning;
    private WindowManager window;
    private GLFWErrorCallback errorCallback;
    private ILogic gameLogic;

    private void init() throws Exception {
        GLFW.glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err).set());
        window = Launcher.getWindow();
        gameLogic = Launcher.getGame();
        window.init();
        gameLogic.init();
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

        double timeU = 1000000000 / (double) targetUPS;
        double timeF = 1000000000 / (double) targetFPS;
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
        gameLogic.input();
    }

    private void render() {
        gameLogic.render();
        window.update();
        renderReady = false;
    }

    private void update() {
        gameLogic.update();
        updateReady = false;
    }

    private void cleanup() {
        window.cleanup();
        gameLogic.cleanup();
        errorCallback.free();
        // Terminate GLFW before ending the program
        glfwTerminate();
    }
}
