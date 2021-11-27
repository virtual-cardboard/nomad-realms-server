package context.loading;

import static context.loading.NomadRealmsTextureLoadTask.SOURCE_PATH;

import java.io.File;

import common.loader.loadtask.FontLoadTask;
import context.visuals.lwjgl.Texture;

public class NomadRealmsFontLoadTask extends FontLoadTask {

	public NomadRealmsFontLoadTask(String fontPath, Texture fontTexture) {
		super(new File(SOURCE_PATH + fontPath), fontTexture);
	}

}