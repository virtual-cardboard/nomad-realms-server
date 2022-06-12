package nomadrealms.model;

public class WorldInfo {

	public final long id;
	public String name;
	public final long seed;
	public final long tick0Time;

	public long lastPlayed;

	public WorldInfo(long id, String name, long seed, long tick0Time) {
		this.id = id;
		this.name = name;
		this.seed = seed;
		this.tick0Time = tick0Time;
		lastPlayed = System.currentTimeMillis();
	}

}
