/*package ak.planets;

import ak.planets.util.*;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Maindep {

    /** position of quad
    float x = 400, y = 300;
    /** angle of quad rotation
    float rotation = 0;

    /** time at last frame
    long lastFrame;

    /** frames per second
    int fps;
    /** last fps time
    long lastFPS;

    public void start(DisplayMode d) {
        try {

            ByteBuffer[] list = new ByteBuffer[2];
            list[0] = TextureUtil.createTexture(ImageIO.read(new File("res/texture/icon/icon32.png")));
            list[1] = TextureUtil.createTexture(ImageIO.read(new File("res/texture/icon/icon64.png")));
            System.out.println("taskbaricon result: " + Display.setIcon(list));

        } catch (IOException e) {
            e.printStackTrace();
        }

        PixelFormat pixelFormat = new PixelFormat();
        ContextAttribs contextAttributes = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);
        DisplayMode displayMode = new DisplayMode(d.getWidth(), d.getHeight());

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
        setup();
        lastFPS = getTime();

        while (!Display.isCloseRequested()) {
            int delta = getDelta();

            update(delta);
            renderGL();

            Display.update(true);
            Display.sync(60); // cap fps to 60fps
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

    public void initGL() {
        GL11.glClearColor(0f, 0f, 0f, 0f);
        GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());

    }

    //Runs once, after GL context has been created
    public void setup(){
    }


    public void renderGL() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }

    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    /**
     * Calculate how many milliseconds have passed
     * since last frame.
     *
     * @return milliseconds passed since last frame


    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle(Reference.GAME_TITLE + " FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    public static void main(String[] args) {
        try{
            DisplayMode d = new DisplayMode(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            new Maindep().start(d);
        }catch (NumberFormatException e){

            System.out.println("Are you sure " + args[0] + " and " + args[1] + " are integers");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
*/