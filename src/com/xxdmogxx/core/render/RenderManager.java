package com.xxdmogxx.core.render;

import com.xxdmogxx.core.tests.Launcher;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import static org.lwjgl.opengl.GL11.*;

public class RenderManager {

    private final WindowManager window;

    public RenderManager() {
        window = Launcher.getWindow();
    }

    public void render(Model model, int uniformID, float uniformValue) {
        clear();
        model.enable();
        GL20.glUniform1f(uniformID, uniformValue);
        //GL20.glEnableVertexAttribArray(0);
        GL11.glDrawElements(GL_TRIANGLES, model.getIndexCount(), GL_UNSIGNED_INT, 0);
        //GL20.glDisableVertexAttribArray(0);
        model.disable();
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}
