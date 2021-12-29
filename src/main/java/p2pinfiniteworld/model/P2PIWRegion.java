package p2pinfiniteworld.model;

import static p2pinfiniteworld.context.simulation.P2PIWServerData.REGION_NUM_CHUNKS;

public class P2PIWRegion {

	public P2PIWChunk[][] chunks = new P2PIWChunk[REGION_NUM_CHUNKS][REGION_NUM_CHUNKS];

	public P2PIWRegion() {
		for (int i = 0; i < REGION_NUM_CHUNKS; i++) {
			for (int j = 0; j < REGION_NUM_CHUNKS; j++) {
				chunks[i][j] = new P2PIWChunk();
			}
		}
	}

}
