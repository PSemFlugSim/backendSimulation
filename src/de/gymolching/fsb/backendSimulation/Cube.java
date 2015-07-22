package de.gymolching.fsb.backendSimulation;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.io.IOException;
import java.nio.*;
import java.util.*;

import org.lwjgl.*;

import com.icyfire3d.main.IF3DGame;
import com.icyfire3d.rendering.*;
import com.icyfire3d.rendering.shaders.*;

/**
 * @author Dominik
 * @created 26.03.2015
 */

public class Cube extends IF3DMesh
{
	private IF3DShader shader;

	private int vaoID;
	private int vboID;
	private int colorBufferID;
	private int mvpLocation;

	private float angle;

	// TODO <tmp>
//	private int tempCounterThing;
//	private Random random;
	private IF3DTexture texture;
	// TODO </tmp>

	/**
	 * TODO Load from file
	 */
	private float[] vertices = new float[]
	{// @formatter:off
		    -1.0f, -1.0f, -1.0f,
		    -1.0f, -1.0f, +1.0f,
		    -1.0f, +1.0f, +1.0f,
		    +1.0f, +1.0f, -1.0f,
		    -1.0f, -1.0f, -1.0f,
		    -1.0f, +1.0f, -1.0f,
		    +1.0f, -1.0f, +1.0f,
		    -1.0f, -1.0f, -1.0f,
		    +1.0f, -1.0f, -1.0f,
		    +1.0f, +1.0f, -1.0f,
		    +1.0f, -1.0f, -1.0f,
		    -1.0f, -1.0f, -1.0f,
		    -1.0f, -1.0f, -1.0f,
		    -1.0f, +1.0f, +1.0f,
		    -1.0f, +1.0f, -1.0f,
		    +1.0f, -1.0f, +1.0f,
		    -1.0f, -1.0f, +1.0f,
		    -1.0f, -1.0f, -1.0f,
		    -1.0f, +1.0f, +1.0f,
		    -1.0f, -1.0f, +1.0f,
		    +1.0f, -1.0f, +1.0f,
		    +1.0f, +1.0f, +1.0f,
		    +1.0f, -1.0f, -1.0f,
		    +1.0f, +1.0f, -1.0f,
		    +1.0f, -1.0f, -1.0f,
		    +1.0f, +1.0f, +1.0f,
		    +1.0f, -1.0f, +1.0f,
		    +1.0f, +1.0f, +1.0f,
		    +1.0f, +1.0f, -1.0f,
		    -1.0f, +1.0f, -1.0f,
		    +1.0f, +1.0f, +1.0f,
		    -1.0f, +1.0f, -1.0f,
		    -1.0f, +1.0f, +1.0f,
		    +1.0f, +1.0f, +1.0f,
		    -1.0f, +1.0f, +1.0f,
		    +1.0f, -1.0f, +1.0f
			// @formatter:on
	};

	private float[] colors =
	{// @formatter:off
			0.583f,  0.771f,  0.014f,
			0.609f,  0.115f,  0.436f,
			0.327f,  0.483f,  0.844f,
			0.822f,  0.569f,  0.201f,
			0.435f,  0.602f,  0.223f,
			0.310f,  0.747f,  0.185f,
			0.597f,  0.770f,  0.761f,
			0.559f,  0.436f,  0.730f,
			0.359f,  0.583f,  0.152f,
			0.483f,  0.596f,  0.789f,
			0.559f,  0.861f,  0.639f,
			0.195f,  0.548f,  0.859f,
			0.014f,  0.184f,  0.576f,
			0.771f,  0.328f,  0.970f,
			0.406f,  0.615f,  0.116f,
			0.676f,  0.977f,  0.133f,
			0.971f,  0.572f,  0.833f,
			0.140f,  0.616f,  0.489f,
			0.997f,  0.513f,  0.064f,
			0.945f,  0.719f,  0.592f,
			0.543f,  0.021f,  0.978f,
			0.279f,  0.317f,  0.505f,
			0.167f,  0.620f,  0.077f,
			0.347f,  0.857f,  0.137f,
			0.055f,  0.953f,  0.042f,
			0.714f,  0.505f,  0.345f,
			0.783f,  0.290f,  0.734f,
			0.722f,  0.645f,  0.174f,
			0.302f,  0.455f,  0.848f,
			0.225f,  0.587f,  0.040f,
			0.517f,  0.713f,  0.338f,
			0.053f,  0.959f,  0.120f,
			0.393f,  0.621f,  0.362f,
			0.673f,  0.211f,  0.457f,
			0.820f,  0.883f,  0.371f,
			0.982f,  0.099f,  0.879f
		    // @formatter:on
	};

	private float uv_data[] =
	{// @formatter:off
		    0.000059f, 1.0f-0.000004f,
		    0.000103f, 1.0f-0.336048f,
		    0.335973f, 1.0f-0.335903f,
		    1.000023f, 1.0f-0.000013f,
		    0.667979f, 1.0f-0.335851f,
		    0.999958f, 1.0f-0.336064f,
		    0.667979f, 1.0f-0.335851f,
		    0.336024f, 1.0f-0.671877f,
		    0.667969f, 1.0f-0.671889f,
		    1.000023f, 1.0f-0.000013f,
		    0.668104f, 1.0f-0.000013f,
		    0.667979f, 1.0f-0.335851f,
		    0.000059f, 1.0f-0.000004f,
		    0.335973f, 1.0f-0.335903f,
		    0.336098f, 1.0f-0.000071f,
		    0.667979f, 1.0f-0.335851f,
		    0.335973f, 1.0f-0.335903f,
		    0.336024f, 1.0f-0.671877f,
		    1.000004f, 1.0f-0.671847f,
		    0.999958f, 1.0f-0.336064f,
		    0.667979f, 1.0f-0.335851f,
		    0.668104f, 1.0f-0.000013f,
		    0.335973f, 1.0f-0.335903f,
		    0.667979f, 1.0f-0.335851f,
		    0.335973f, 1.0f-0.335903f,
		    0.668104f, 1.0f-0.000013f,
		    0.336098f, 1.0f-0.000071f,
		    0.000103f, 1.0f-0.336048f,
		    0.000004f, 1.0f-0.671870f,
		    0.336024f, 1.0f-0.671877f,
		    0.000103f, 1.0f-0.336048f,
		    0.336024f, 1.0f-0.671877f,
		    0.335973f, 1.0f-0.335903f,
		    0.667969f, 1.0f-0.671889f,
		    1.000004f, 1.0f-0.671847f,
		    0.667979f, 1.0f-0.335851f
		    // @formatter:on 
	};

	public Cube()
	{
//		this.random = new Random();
		this.texture = new IF3DTexture("assets/textures/CrappyTexture.png", 4);
		this.texture.asyncLoadResourceData();
		this.texture.syncLoadResourceData();

		try
		{
			this.shader = new IF3DShader("assets/shaders/VertexShader.glsl",
					"assets/shaders/FragmentShader.glsl");
		}
		catch (IF3DShaderException e)
		{
			IF3DGame.err("[IF3DCube Error] shader could not be created due to IF3DShaderException");
			e.printStackTrace();
			System.exit(1);
		}
		catch (IOException e)
		{
			IF3DGame.err("[IF3DCube Error] shader could not be created due to IOException");
			e.printStackTrace();
			System.exit(1);
		}
		this.mvpLocation = glGetUniformLocation(this.shader.getProgramID(), "vertexTransformMatrix");

		this.vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);

		FloatBuffer verticeBuffer = BufferUtils.createFloatBuffer(vertices.length);
		verticeBuffer.put(vertices).flip();

		glBufferData(GL_ARRAY_BUFFER, verticeBuffer, GL_STATIC_DRAW);

		this.vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);

		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

		// TODO: <TMP>
		// System.out.println("Color length: " + this.colors.length);
		// FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(colors.length);
		// colorBuffer.put(colors).flip();
		//
		// this.colorBufferID = glGenBuffers();
		// glBindBuffer(GL_ARRAY_BUFFER, this.colorBufferID);
		// glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
		// glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		// TODO: </TMP>

		FloatBuffer uvBuffer = BufferUtils.createFloatBuffer(uv_data.length);
		uvBuffer.put(uv_data).flip();

		this.colorBufferID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, this.colorBufferID);
		glBufferData(GL_ARRAY_BUFFER, uvBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

		glBindVertexArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	@Override
	public void update()
	{
//		tempCounterThing++;
//		if (tempCounterThing % 60 == 0)
//		{
//			// Change color buffer
//			for (int i = 0; i < 108; i++)
//			{
//				this.colors[i] = this.random.nextFloat();
//			}
//
//			// upload color data to graphics card
//			FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(this.colors.length);
//			colorBuffer.put(this.colors).flip();
//			glBindBuffer(GL_ARRAY_BUFFER, this.colorBufferID);
//			glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
//			glBindBuffer(GL_ARRAY_BUFFER, 0);
//		}
//
		angle += 0.3f;
	}

	@Override
	public void render(float lag)
	{
		IF3DRenderer activeRenderer = IF3DRenderer.getActiveRenderer();

		// bind the shader
		shader.bind();
		this.texture.bindTexture();

		glBindVertexArray(vaoID);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

		// Apply affine rotations
		activeRenderer.loadIdentity();
		activeRenderer.translate(0f, 0f, -10f);
		activeRenderer.rotate(angle, 1f, 1f, 1f);

		// send mvp matrix to shader
		glUniformMatrix4(this.mvpLocation, false, IF3DRenderer.getActiveRenderer().getMVPMatrix()
				.toFloatBuffer());

		// Draw data
		glPointSize(8f);
		glDrawArrays(GL_TRIANGLES, 0, vertices.length / 3);

		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(0);

		shader.unbind();
		this.texture.unbindTexture();
	}

	@Override
	public void dispose()
	{
		shader.dispose();
	}
}
