package loading;

import static loading.NomadRealmsServerTextureLoadTask.SOURCE_PATH;

import context.visuals.lwjgl.Shader;
import context.visuals.lwjgl.ShaderType;
import engine.common.loader.loadtask.ShaderLoadTask;

public class NomadRealmsServerShaderLoadTask extends ShaderLoadTask {

	public NomadRealmsServerShaderLoadTask(ShaderType shaderType, String sourceLocation) {
		super(shaderType, SOURCE_PATH + sourceLocation);
	}

	public NomadRealmsServerShaderLoadTask(Shader shader, String sourceLocation) {
		super(shader, SOURCE_PATH + sourceLocation);
	}

}
