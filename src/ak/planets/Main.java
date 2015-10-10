package ak.planets;

import ak.planets.util.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.DisplayMode;

public class Main {

    /** position of quad */
    float x = 400, y = 300;
    /** angle of quad rotation */
    float rotation = 0;

    /** time at last frame */
    long lastFrame;

    /** frames per second */
    int fps;
    /** last fps time */
    long lastFPS;

    private int sync;

    public void start(DisplayMode d) {

        PixelFormat pixelFormat = new PixelFormat();
        ContextAttribs contextAttributes = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);
        DisplayMode displayMode = new DisplayMode(d.getWidth(), d.getHeight());
        sync = d.getFrequency();
        try {
            Display.setDisplayMode(displayMode);
            Display.setTitle(Reference.GAME_TITLE);
            Display.create(pixelFormat, contextAttributes);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        //Init
        initGL();
        getDelta();
        lastFPS = getTime();

        while (!Display.isCloseRequested()) {
            int delta = getDelta();

            update(delta);
            renderGL();

            Display.update();
            Display.sync(sync); // cap fps to 60fps

        }

        Display.destroy();
    }

    public void update(int delta) {
        // rotate quad
        rotation += 0.15f * delta;

        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) x -= 0.35f * delta;
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) x += 0.35f * delta;

        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) y -= 0.35f * delta;
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) y += 0.35f * delta;

        //Update FPS
        updateFPS();
    }

    /**
     * Calculate how many milliseconds have passed
     * since last frame.
     *
     * @return milliseconds passed since last frame
     */
    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    public void initGL() {

    }

    public void renderGL() {

    }

    public static void main(String[] args) {
        try{
            DisplayMode d = new DisplayMode(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            new Main().start(d);
        }catch (Exception e){
            System.out.println("Are you sure " + args[0] + " and " + args[1] + " are not integers");
        }
    }
}