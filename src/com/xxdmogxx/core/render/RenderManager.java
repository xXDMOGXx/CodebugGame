package com.xxdmogxx.core.render;

import com.xxdmogxx.core.engine.Launcher;
import com.xxdmogxx.core.utils.Constants;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL20;

import static org.lwjgl.opengl.GL11.*;

public class RenderManager {

    private final WindowManager window;

    public RenderManager() {
        window = Launcher.getWindow();
    }

    public void render(Group group) {
        clear();
        group.enable();
        GL31.glDrawElementsInstanced(GL_TRIANGLES, group.getIndexCount(), GL_UNSIGNED_INT, 0, Constants.numAnts);
        group.disable();
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}
