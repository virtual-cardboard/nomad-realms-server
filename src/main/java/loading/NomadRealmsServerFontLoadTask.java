package loading;

import static loading.NomadRealmsServerTextureLoadTask.SOURCE_PATH;

import java.io.File;

import context.visuals.lwjgl.Texture;
import engine.common.loader.loadtask.FontLoadTask;

public class NomadRealmsServerFontLoadTask extends FontLoadTask {

	public NomadRealmsServerFontLoadTask(String fontPath, Texture fontTexture) {
		super(new File(SOURCE_PATH + fontPath), fontTexture);
	}

}
