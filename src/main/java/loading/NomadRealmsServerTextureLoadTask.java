package loading;

import common.loader.loadtask.TextureLoadTask;
import context.visuals.lwjgl.Texture;

public class NomadRealmsServerTextureLoadTask extends TextureLoadTask {

	static final String SOURCE_PATH = NomadRealmsServerTextureLoadTask.class.getProtectionDomain().getCodeSource().getLocation().getPath().substring(1);

	public NomadRealmsServerTextureLoadTask(String path) {
		super(SOURCE_PATH + path);
	}

	public NomadRealmsServerTextureLoadTask(Texture texture, String path) {
		super(texture, SOURCE_PATH + path);
	}

}
