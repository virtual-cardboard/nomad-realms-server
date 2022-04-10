package loading;

import context.visuals.lwjgl.Texture;
import engine.common.loader.loadtask.TextureLoadTask;

public class NomadRealmsServerTextureLoadTask extends TextureLoadTask {

	static final String SOURCE_PATH = NomadRealmsServerTextureLoadTask.class.getProtectionDomain().getCodeSource().getLocation().getPath().substring(1);

	public NomadRealmsServerTextureLoadTask(String path) {
		super(SOURCE_PATH + path);
	}

	public NomadRealmsServerTextureLoadTask(Texture texture, String path) {
		super(texture, SOURCE_PATH + path);
	}

}
