package ak.planets;

import ak.planets.entity.PlayerEntity;
import ak.planets.util.Reference;
import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.awt.*;
import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Created by Aleksander on 11/10/2015.
 */
public class Main {
    // We need to strongly reference callback instances.

    private GLFWKeyCallback   keyCallback;
    private PlayerEntity playerEntity;
    private PlayerEntity playerEntity2;
    //window handle
    private long window;

    private void initObjects(){
        playerEntity = new PlayerEntity(0, 0);
        playerEntity2 = new PlayerEntity(0.2f, 0);
    }
    private void setupGL(){
        playerEntity.setup();
        playerEntity2.setup();
    }

    private void renderGL(){
        playerEntity2.draw();
        playerEntity.draw();
    }

    private void update(){
        playerEntity.update();
        playerEntity2.update();
    }

    public void run(DisplayMode displayMode) {
        System.out.println("Hello LWJGL " + Sys.getVersion() + "!");

        try {
            init(displayMode);
            initObjects();
            //Important
            GL.createCapabilities();

            // Set the clear color to black
            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

            setupGL();
            // Run the rendering loop until the user has attempted to close
            // the window or has pressed the ESCAPE key.
            while ( glfwWindowShouldClose(window) == GL_FALSE ) {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

                update();
                renderGL();
                glfwSwapBuffers(window);

                glfwPollEvents();
            }

            // Release window and window callbacks
            glfwDestroyWindow(window);
            keyCallback.release();

        } finally {
            // Terminate GLFW
            glfwTerminate();
        }
    }

    private void init(DisplayMode m) {

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( glfwInit() != GL11.GL_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");

        //window config, visible and resizable
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);

        int WIDTH = m.getWidth();
        int HEIGHT = m.getHeight();

        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, Reference.GAME_TITLE, NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
                if ( key == GLFW_KEY_RIGHT  && action == GLFW_PRESS)
                    playerEntity.updateMovement(PlayerEntity.RIGHT, true);
                if ( key == GLFW_KEY_RIGHT  && action == GLFW_RELEASE)
                    playerEntity.updateMovement(PlayerEntity.RIGHT, false);

                if ( key == GLFW_KEY_LEFT   && action == GLFW_PRESS)
                    playerEntity.updateMovement(PlayerEntity.LEFT, true);
                if ( key == GLFW_KEY_LEFT   && action == GLFW_RELEASE)
                    playerEntity.updateMovement(PlayerEntity.LEFT, false);

                if ( key == GLFW_KEY_UP     && action == GLFW_PRESS)
                    playerEntity2.updateMovement(PlayerEntity.UP, true);
                if ( key == GLFW_KEY_UP     && action == GLFW_RELEASE)
                    playerEntity2.updateMovement(PlayerEntity.UP, false);

                if ( key == GLFW_KEY_DOWN   && action == GLFW_PRESS)
                    playerEntity2.updateMovement(PlayerEntity.DOWN, true);
                if ( key == GLFW_KEY_DOWN   && action == GLFW_RELEASE)
                    playerEntity2.updateMovement(PlayerEntity.DOWN, false);


            }
        });

        //get resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        //center the window
        glfwSetWindowPos(
                window,
                (GLFWvidmode.width(vidmode) - WIDTH) / 2,
                (GLFWvidmode.height(vidmode) - HEIGHT) / 2
        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);

        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    public static void main(String[] args) {
        try{
            DisplayMode d = new DisplayMode(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
            new Main().run(d);
        }catch (NumberFormatException e){

            System.out.println("Are you sure " + args[0] + " and " + args[1] + " are integers");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
