package net.gegy1000.slyther.client;

import net.gegy1000.slyther.game.Configuration;
import net.gegy1000.slyther.game.Skin;

public class ClientConfig implements Configuration {
	public final static int	ScoreTypeNone = 0;
	public final static int	ScoreTypeOriginal = 1;
	public final static int	ScoreTypeExtended = 2;
	public final static int ScoreTypeCOUNT = 3;
	
    public String nickname = "Slyther";
    public Skin skin = Skin.RAINBOW;
    public String server;
    public boolean shouldRecord = true;
    public boolean debugMode = false;
    public boolean showDebug = false;
    public int	scoreDisplayMode = ScoreTypeOriginal;
    public boolean showFullScreen = true;
}
