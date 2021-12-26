package loading;

import static loading.NomadRealmsServerTextureLoadTask.SOURCE_PATH;

import java.io.File;

import common.loader.loadtask.FontLoadTask;
import context.visuals.lwjgl.Texture;

public class NomadRealmsServerFontLoadTask extends FontLoadTask {

	public NomadRealmsServerFontLoadTask(String fontPath, Texture fontTexture) {
		super(new File(SOURCE_PATH + fontPath), fontTexture);
	}

}
