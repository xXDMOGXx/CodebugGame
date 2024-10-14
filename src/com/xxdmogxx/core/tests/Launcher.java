package com.xxdmogxx.core.tests;

import com.xxdmogxx.core.EngineManager;
import com.xxdmogxx.core.render.WindowManager;
import com.xxdmogxx.core.utils.Constants;

public class Launcher {

    private static WindowManager window;
    private static TestSim game;

    public static void main(String[] args) {
        // Creates the window
        window = new WindowManager(Constants.TITLE, Constants.WIDTH, Constants.HEIGHT, Constants.VSYNC);
        // Creates the main game file
        game = new TestSim();
        // Creates the engine that runs the game framework
        EngineManager engine = new EngineManager();
        // Try to start the engine
        try {
            engine.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WindowManager getWindow() {
        return window;
    }

    public static TestSim getGame() {
        return game;
    }
}
