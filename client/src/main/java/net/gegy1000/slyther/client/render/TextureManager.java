package net.gegy1000.slyther.client.render;

import net.gegy1000.slyther.util.Log;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;

import com.buckosoft.glelements.Texture;

public class TextureManager {
    private Map<String, Texture> textures = new HashMap<>();

    public Texture getTexture(String path) {
        if (textures.containsKey(path)) {
            return textures.get(path);
        } else {
            try {
                //return textures.put(path, TextureLoader.getTexture("png", TextureManager.class.getResourceAsStream(path)));
                return textures.put(path, Texture.getTexture(path));
            } catch (IOException e) {
                Log.error("Failed to load texture {}", path);
                Log.catching(e);
            }
        }
        return null;
    }

    public void bindTexture(String path) {
        Texture texture = getTexture(path);
        if (texture != null) {
            texture.bind();
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			GL11.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 
					texture.width, texture.height, 0, 
					GL_RGBA, GL_UNSIGNED_BYTE, texture.imgData);
	        }
    }
    
    public void cacheChart(Texture texture) {
    	textures.put("chart", texture);
    }
}
