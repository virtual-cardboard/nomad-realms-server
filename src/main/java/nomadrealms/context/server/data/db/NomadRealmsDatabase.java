package nomadrealms.context.server.data.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.ds.PGSimpleDataSource;

public class NomadRealmsDatabase {

	private boolean connected;

	private PGSimpleDataSource dbSource;

	public void connect() {
		if (connected) {
			throw new IllegalStateException("Database already connected.");
		} else {
			dbSource = new PGSimpleDataSource();
			dbSource.setUrl("jdbc:postgresql://free-tier11.gcp-us-east1.cockroachlabs.cloud:26257/defaultdb" +
					"?options=--cluster%3Dnomad-realms-cluster-1145&sslmode=verify-full");
			dbSource.setUser("virtualcardboard");
			dbSource.setPassword("RSlnFuOi6hXgofBjvNZ95A");
			connected = true;
		}
	}

	public String getUsername(long playerId) {
		String sql = "SELECT username FROM players WHERE id=?";
		try (Connection connection = dbSource.getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setLong(1, playerId);
				ResultSet re = statement.executeQuery();
				re.next();
				return re.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
