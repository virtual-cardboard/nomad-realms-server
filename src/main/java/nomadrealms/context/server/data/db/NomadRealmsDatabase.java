package nomadrealms.context.server.data.db;

import org.postgresql.ds.PGSimpleDataSource;

public class NomadRealmsDatabase {

	private boolean connected;

	public void connect() {
		if (connected) {
			throw new IllegalStateException("Database already connected.");
		} else {
			PGSimpleDataSource ds = new PGSimpleDataSource();
			ds.setUrl("jdbc:postgresql://free-tier11.gcp-us-east1.cockroachlabs.cloud:26257/defaultdb?options=--cluster%3Dnomad-realms-cluster-1145&sslmode=verify-full");
			ds.setUser("virtualcardboard");
			ds.setPassword("RSlnFuOi6hXgofBjvNZ95A");
			connected = true;
		}
	}

	public void testing() {
	}

}
