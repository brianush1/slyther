package net.gegy1000.slyther.client.render;

import net.gegy1000.slyther.util.Log;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        }
    }
    
    public void cacheChart(Texture texture) {
    	textures.put("chart", texture);
    }
}
