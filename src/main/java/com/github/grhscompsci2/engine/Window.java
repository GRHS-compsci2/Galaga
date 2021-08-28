package com.github.grhscompsci2.engine;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;


public class Window {
    
    private int height;
    private int width;
    private String name;
    private long glfwWindow;

    public static Window window = null;

    private Window() {
        this.height = 480;
        this.width = 640;
        this.name = "Bibble!";

    }

    public static Window get() {
        Window.window = (Window.window == null) ? new Window() : Window.window;
        return Window.window;
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

    }

    public void init() {
        // error callback
        GLFWErrorCallback.createPrint(System.err).set();
        
        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.name, NULL, NULL);
        
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW Window");
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);

        // enable v-sync
        glfwSwapInterval(1);

        // show window
        glfwShowWindow(glfwWindow);

        /**
         * This line is critical for LWJGL's interoperation with GLFW's
         * OpenGL context, or any context that is managed externally.
         * LWJGL detects the context that is current in the current thread,
         * creates the GLCapabilities instance and makes the OpenGL
         * bindings available for use.
         */

        GL.createCapabilities();
    }

    public void loop() {

        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll events
            glfwPollEvents();

            glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            glfwSwapBuffers(glfwWindow); 

        }

    }
}