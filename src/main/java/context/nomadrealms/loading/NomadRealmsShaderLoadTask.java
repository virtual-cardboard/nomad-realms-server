package context.nomadrealms.loading;

import static context.nomadrealms.loading.NomadRealmsTextureLoadTask.SOURCE_PATH;

import common.loader.loadtask.ShaderLoadTask;
import context.visuals.lwjgl.Shader;
import context.visuals.lwjgl.ShaderType;

public class NomadRealmsShaderLoadTask extends ShaderLoadTask {

	public NomadRealmsShaderLoadTask(ShaderType shaderType, String sourceLocation) {
		super(shaderType, SOURCE_PATH + sourceLocation);
	}

	public NomadRealmsShaderLoadTask(Shader shader, String sourceLocation) {
		super(shader, SOURCE_PATH + sourceLocation);
	}

}
