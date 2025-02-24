package com.xxdmogxx.core.engine;

import com.xxdmogxx.core.render.Window;
import com.xxdmogxx.core.utils.Constants;

public class Launcher {

    private static Window window;
    private static Simulation sim;
    private static EngineManager engine;

    public static void main(String[] args) {
        // Creates the window
        window = new Window(Constants.TITLE, Constants.WIDTH, Constants.HEIGHT, Constants.VSYNC);
        // Creates the main game file
        sim = new Simulation();
        // Creates the engine that runs the game framework
        engine = new EngineManager();
        // Try to start the engine
        try {
            engine.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Window getWindow() {
        return window;
    }

    public static Simulation getSim() {
        return sim;
    }

    public static EngineManager getEngine() {
        return engine;
    }
}
