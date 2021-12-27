package p2pinfiniteworld.context.simulation.visuals;

import static java.lang.Math.floorDiv;

import common.math.Vector2i;

public class NomadTiny {

	public int x, y;

	public NomadTiny(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vector2i region() {
		return new Vector2i(floorDiv(x, 256), floorDiv(x, 256));
	}

	public Vector2i chunk() {
		return new Vector2i(floorDiv(x, 16), floorDiv(x, 16));
	}

}
