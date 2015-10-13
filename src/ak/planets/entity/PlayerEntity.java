package ak.planets.entity;

import ak.planets.util.ShaderUtil;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;

public class PlayerEntity {

    //vectors are nicer to work with
    private Vector2f positionVector;
    private boolean rightPressed, leftPressed, upPressed, downPressed;
    //VAO and VBO id's for the render.
    //vertex count for the render
    private int vaoId;
    private int vboId;
    private int vertexCount;

    //Shader
    private int fragmentShader;
    private int vertexShader;

    public static final int UP = 2;
    public static final int DOWN = 3;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    public PlayerEntity(float x, float y){
        positionVector = new Vector2f(x, y);
    }

    public void updateMovement(int pos, boolean pressed){
        switch (pos){
            case 0 : leftPressed = pressed;
                break;
            case 1 : rightPressed = pressed;
                break;
            case 2 : upPressed = pressed;
                break;
            case 3 : downPressed = pressed;
                break;
        }
    }

    public void update(){
        if(upPressed)
            positionVector.y += 0.15f;
        if(downPressed)
            positionVector.y -= 0.15f;
        if(leftPressed)
            positionVector.x -= 0.15f;
        if(rightPressed)
            positionVector.x += 0.15f;

    }


    public void setup(){

        fragmentShader = ShaderUtil.loadShader("/src/ak/planets/shader/PlayerEntity.vs.glsl", GL20.GL_FRAGMENT_SHADER);
        vertexShader = ShaderUtil.loadShader("/src/ak/planets/shader/PlayerEntity.fs.glsl", GL20.GL_VERTEX_SHADER);


        /**
         * Vertices for the triangle, more than one triangle may be needed for complex models
         */
        float[] vertices = {
                // Left bottom triangle
                -0.1f, 0.1f, 0f,
                -0.1f, -0.1f, 0f,
                0.1f, -0.1f, 0f,
                // Right top triangle
                0.1f, -0.1f, 0f,
                0.1f, 0.1f, 0f,
                -0.1f, 0.1f, 0f
        };

        /**
         *  create a float buffer using the vertex array and send it to the GPU
         */

        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices);
        verticesBuffer.flip();

        vertexCount = 6;

        /**
         * create a new VAO and bind it
         */
        vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);

        /**
         * Generate a VBO
         */
        vboId = GL15.glGenBuffers();
        /**
         * Add the VBO to the VAO (As the VAO is currently bound)
         */
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        /**
         * Add the vertices buffer to the VAO
         */
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
        /**
         * Telling GL to know what's stored in the VAO and which location ( id, number of vertices, floats, normalize, scale, offset)
         */
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

        /**
         * deselect
         */
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        /**
         * unbind vertex array
         */
        GL30.glBindVertexArray(0);

    }

    public void draw(){
        GL30.glBindVertexArray(vaoId);
        GL20.glEnableVertexAttribArray(0);
        GL11.glTranslatef(positionVector.x, positionVector.y, 0);

        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);
        GL11.glTranslatef(-positionVector.x, -positionVector.y, 0);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }
}
