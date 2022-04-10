package nomadrealms.model;

public class WorldInfo {

	public long id;
	public String name;
	public long seed;

	public long lastPlayed;

	public WorldInfo(long id, String name, long seed) {
		this.id = id;
		this.name = name;
		this.seed = seed;
		lastPlayed = System.currentTimeMillis();
	}

}
