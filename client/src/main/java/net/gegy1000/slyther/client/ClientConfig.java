package net.gegy1000.slyther.client;

import java.util.ArrayList;

import net.gegy1000.slyther.game.Configuration;
import net.gegy1000.slyther.game.SkinCustom;
import net.gegy1000.slyther.game.SkinEnum;

public class ClientConfig implements Configuration {
	public final static int	ScoreTypeNone = 0;
	public final static int	ScoreTypeOriginal = 1;
	public final static int	ScoreTypeExtended = 2;
	public final static int ScoreTypeCOUNT = 3;
	
    public String nickname = "Slyther";
    public SkinEnum skin = SkinEnum.RAINBOW;
    public SkinCustom customSkin;
    public String server;
    public boolean shouldRecord = true;
    public boolean autoSelectCloseServer = true;
    public boolean debugMode = false;
    public boolean showDebug = false;
    public int	scoreDisplayMode = ScoreTypeOriginal;
    public boolean showFullScreen = true;
    public boolean virgin = true;
    public int	numReplaysToKeep = 5;
    public ArrayList<String>	replaysToKeep = new ArrayList<String>();
}
