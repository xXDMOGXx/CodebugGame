package com.xxdmogxx.core.render;

import com.xxdmogxx.core.tests.Launcher;
import com.xxdmogxx.core.utils.Constants;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL20;

import static org.lwjgl.opengl.GL11.*;

public class RenderManager {

    private final WindowManager window;

    public RenderManager() {
        window = Launcher.getWindow();
    }

    public void render(Model model, int uniformID, float uniformValue, float[] translations) {
        clear();
        model.enable(translations);
        GL20.glUniform1f(uniformID, uniformValue);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL31.glDrawElementsInstanced(GL_TRIANGLES, model.getIndexCount(), GL_UNSIGNED_INT, 0, Constants.numAnts);
        GL20.glDisableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        model.disable();
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}
