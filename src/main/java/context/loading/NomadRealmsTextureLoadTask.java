package context.loading;

import common.loader.loadtask.TextureLoadTask;
import context.visuals.lwjgl.Texture;

public class NomadRealmsTextureLoadTask extends TextureLoadTask {

	static final String SOURCE_PATH = NomadRealmsTextureLoadTask.class.getProtectionDomain().getCodeSource().getLocation().getPath().substring(1);

	public NomadRealmsTextureLoadTask(int textureUnit, String path) {
		super(textureUnit, SOURCE_PATH + path);
	}

	public NomadRealmsTextureLoadTask(Texture texture, String path) {
		super(texture, SOURCE_PATH + path);
	}

}
