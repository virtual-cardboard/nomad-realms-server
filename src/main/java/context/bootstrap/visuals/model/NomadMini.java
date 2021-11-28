package context.bootstrap.visuals.model;

import static common.math.Vector2f.fromAngleLength;
import static java.lang.Math.PI;
import static java.lang.Math.random;

import common.math.Vector2f;

public class NomadMini {

	private static final int HOPPING = 0;
	private static final int RESTING = 1;
	private static final int DRAGGED = 2;

	private static final int HOP_DURATION = 20;
	private static final int HOP_HEIGHT = 10;
	private static final int MAX_HOPS = 5;
	private static final int MIN_HOPS = 2;
	private static final float SPEED = 1;
	private static final int MAX_REST = 200;
	private static final int MIN_REST = 50;

	private float x = 50 + (int) (400 * Math.random());
	private float y = 200 + 50 + (int) (400 * Math.random());
	private int h;
	private int colour;

	private Vector2f direction;
	private int numHopsLeft;

	private int timeForRest = 10;
	private int timeForHop = HOP_DURATION;

	private int state = RESTING;

	public NomadMini(int colour) {
		this.colour = colour;
	}

	public void update() {
		if (state == RESTING) {
			if (timeForRest == 0) { // Transition to hopping
				state = HOPPING;
				direction = fromAngleLength((float) (Math.random() * 2 * PI), SPEED);
				numHopsLeft = MIN_HOPS + (int) ((MAX_HOPS - MIN_HOPS) * Math.random());
			} else if (timeForRest > 0) { // Resting
				timeForRest--;
			}
		} else if (state == HOPPING) {
			if (numHopsLeft == 0) { // Transition to rest
				state = RESTING;
				timeForRest = MIN_REST + (int) ((MAX_REST - MIN_REST) * random());
			} else { // Hopping
				timeForHop--;
				h = (int) (HOP_HEIGHT * Math.sin(timeForHop * PI / HOP_DURATION));
				x += direction.x;
				y += direction.y;
				if (timeForHop == 0) {
					numHopsLeft--;
					timeForHop = HOP_DURATION;
				}
				x = Math.max(40, Math.min(410, x));
				y = Math.max(240, Math.min(600, y));
			}
		} else if (state == DRAGGED) {
		}
	}

	public float x() {
		return x;
	}

	public float y() {
		return y;
	}

	public Vector2f pos() {
		return new Vector2f(x, y);
	}

	public int h() {
		return h;
	}

	public int colour() {
		return colour;
	}

	public void drag() {
		h = 30;
		state = DRAGGED;
	}

	public void undrag() {
		h = 0;
		state = RESTING;
		timeForRest = MIN_REST + (int) ((MAX_REST - MIN_REST) * random());
	}

	public void setPos(Vector2f vec2f) {
		x = vec2f.x;
		y = vec2f.y;
		x = Math.max(40, Math.min(410, x));
		y = Math.max(240, Math.min(600, y));
	}

}
