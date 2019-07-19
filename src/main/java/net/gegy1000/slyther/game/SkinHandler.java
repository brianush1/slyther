package net.gegy1000.slyther.game;

import com.google.gson.Gson;
import net.gegy1000.slyther.util.Log;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum SkinHandler {
	INSTANCE;

	private final Map<Skin, SkinDetails> PATTERNS = new HashMap<>();
	private final Map<Integer, SkinDetails> CUSTOM_PATTERNS = new HashMap<>();

	SkinHandler() {
		for (SkinEnum skin : SkinEnum.values()) {
			PATTERNS.put(skin, new SkinDetails(new Color[] { Color.values()[skin.ordinal() % Color.values().length] }));
		}
		try {
			SkinPatternsContainer patterns = new Gson().fromJson(new InputStreamReader(SkinHandler.class.getResourceAsStream("/data/skin_patterns.json")), SkinPatternsContainer.class);
			if (patterns != null) {
				for (SkinJsonContainer container : patterns.patterns) {
					PATTERNS.put(container.skin, container.details);
				}
			}
		} catch (Exception e) {
			Log.catching(e);
		}
	}

	public SkinDetails getDetails(Skin skin) {
		return PATTERNS.get(skin);
	}

	public void putCustomDetails(Integer id, SkinDetails details) {
		CUSTOM_PATTERNS.put(id,  details);
	}

	public SkinDetails getCustomDetails(Integer id) {
		return(CUSTOM_PATTERNS.get(id));
	}

	public void removeCustomDetails(Integer id) {
		CUSTOM_PATTERNS.remove(id);
	}

	private class SkinPatternsContainer {
		public List<SkinJsonContainer> patterns;
	}

	private class SkinJsonContainer {
		public Skin skin;
		public SkinDetails details;
	}
}
