package com.xxdmogxx.core;

import com.xxdmogxx.core.render.WindowManager;
import com.xxdmogxx.core.tests.Launcher;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.glfwTerminate;

public class EngineManager {
    public static final long NANOSECOND = 1000000000L;
    public static final float FRAMERATE = 1000;

    private static int fps;
    private static float frametime = 1 / FRAMERATE;

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
    }

    public void start() throws Exception {
        init();
        if (!isRunning) {
            run();
        }
    }

    public void run() {
        this.isRunning = true;
        int frames = 0;
        long frameCounter = 0;
        long lastTime = System.nanoTime();
        double unprocessedTime = 0;

        while (isRunning) {
            boolean render = false;
            long startTime = System.nanoTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime / (double) NANOSECOND;
            frameCounter += passedTime;

            input();

            while (unprocessedTime > frametime) {
                render = true;
                unprocessedTime -= frametime;

                if (window.windowShouldClose()) {
                    stop();
                }

                if (frameCounter >= NANOSECOND) {
                    fps = frames;
                    //System.out.println("FPS: " + fps);
                    frames = 0;
                    frameCounter = 0;
                }
            }

            if (render) {
                update();
                render();
                frames++;
            }
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
    }

    private void update() {
        gameLogic.update();
    }

    private void cleanup() {
        window.cleanup();
        gameLogic.cleanup();
        errorCallback.free();
        // Terminate GLFW before ending the program
        glfwTerminate();
    }
}
