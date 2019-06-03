/**
 * 
 */
package com.buckosoft.glelements;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
//import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
import net.gegy1000.slyther.util.Log;

/**
 * @author dick
 *
 */
public class Texture {
	public ByteBuffer imgData;
	public int width;
	public int height;
	private String name;
	int textureId;

	public Texture() {}

	public void bind() {
		if (textureId == 0)
			textureId = createTextureID();			
		GL11.glBindTexture(GL_TEXTURE_2D, textureId);
		
	}

	public static Texture getTexture(String path) throws IOException {
		Texture t = new Texture();
		t.name = path;
		InputStream in = Texture.class.getResourceAsStream(path);
		t.load(in);
		return(t);
	}

	public static Texture getTexture(InputStream inputStream) {
		Texture t = new Texture();
		t.load(inputStream);
		return t;
	}

	private void load(InputStream inputStream) {
		PNGDecoder decoder;
		try {
			decoder = new PNGDecoder(inputStream);
			width = decoder.getWidth();
			height = decoder.getHeight();
			Log.debug("width = {} height = {} name = {}", width, height, name);
			//System.out.println("width="+decoder.getWidth());
			//System.out.println("height="+decoder.getHeight());
			imgData = ByteBuffer.allocateDirect(4*decoder.getWidth()*decoder.getHeight());
			decoder.decode(imgData, decoder.getWidth()*4, Format.RGBA);
			imgData.flip();
		} catch (IOException e) {
			Log.catching(e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				Log.catching(e);
			}
		}
	}

	private int createTextureID() 
	{ 
		IntBuffer tmp = createIntBuffer(1); 
		GL11.glGenTextures(tmp); 
		return tmp.get(0);
	} 
	/**
	 * Creates an integer buffer to hold specified ints
	 * - strictly a utility method
	 *
	 * @param size how many int to contain
	 * @return created IntBuffer
	 */
	private IntBuffer createIntBuffer(int size) {
		ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
		temp.order(ByteOrder.nativeOrder());

		return temp.asIntBuffer();
	}    

}
