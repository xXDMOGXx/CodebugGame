package com.xxdmogxx.core.render.components;

import com.xxdmogxx.core.utils.Constants;
import org.lwjgl.opengl.GL20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Shader {

    private final int ID;

    public Shader(String vertexShaderPath, String fragmentShaderPath) throws Exception {
        // Create Shader Program Object and get its reference id
        ID = GL20.glCreateProgram();

        // Load the vertex and fragment shaders from their files
        String vertexCode = loadFromFile(vertexShaderPath);
        String fragmentCode = loadFromFile(fragmentShaderPath);
        // Create Shader Objects and get their reference id
        int vertexID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        int fragmentID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        // Attach Shader sources to the Shader Objects
        GL20.glShaderSource(vertexID, vertexCode);
        GL20.glShaderSource(fragmentID, fragmentCode);
        // Compile the Shaders into machine code
        GL20.glCompileShader(vertexID);
        GL20.glCompileShader(fragmentID);
        // Detect if the shaders compiled successfully
        if (GL20.glGetShaderi(vertexID, GL20.GL_COMPILE_STATUS) == 0) throw new Exception("Error compiling vertex shader code. Info: " + GL20.glGetShaderInfoLog(vertexID, 1024));
        if (GL20.glGetShaderi(fragmentID, GL20.GL_COMPILE_STATUS) == 0) throw new Exception("Error compiling fragment shader code. Info: " + GL20.glGetShaderInfoLog(fragmentID, 1024));
        // Attach the shaders to the Shader Program
        GL20.glAttachShader(ID, vertexID);
        GL20.glAttachShader(ID, fragmentID);
        // Wrap-up/Link all the shaders together into the Shader Program
        GL20.glLinkProgram(ID);

        // Delete the now useless Vertex and Fragment Shader objects
        if (vertexID != 0) GL20.glDetachShader(ID, vertexID);
        if (fragmentID != 0) GL20.glDetachShader(ID, fragmentID);
    }

    private static String loadFromFile(String filePath) {
        File shaderFile = new File(filePath);
        Scanner scanner;
        try {
            scanner = new Scanner(shaderFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }

        String result;
        result = scanner.useDelimiter("\\A").next();
        scanner.close();
        return result;
    }

    public void enable() {
        // Tell OpenGL which Shader Program we want to us
        GL20.glUseProgram(ID);
    }

    public void disable() {
        // Tell OpenGL which to use Shader Program 0 (No Shader)
        GL20.glUseProgram(0);
    }

    public void delete() {
        disable();
        // Delete the Shader Program
        if (ID != 0) GL20.glDeleteProgram(ID);
    }
}
