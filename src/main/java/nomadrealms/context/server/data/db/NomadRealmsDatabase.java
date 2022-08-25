package nomadrealms.context.server.data.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.ds.PGSimpleDataSource;

public class NomadRealmsDatabase {

	private Connection connection;

	public void connect() {
		if (connection != null) {
			throw new IllegalStateException("Database already connected.");
		} else {
			PGSimpleDataSource dbSource = new PGSimpleDataSource();
			dbSource.setUrl("jdbc:postgresql://free-tier11.gcp-us-east1.cockroachlabs.cloud:26257/defaultdb" +
					"?options=--cluster%3Dnomad-realms-cluster-1145&sslmode=verify-full");
			dbSource.setUser("virtualcardboard");
			dbSource.setPassword("kzRCW7sKEpVV2d0FYulBCA");

			try {
				connection = dbSource.getConnection();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private ResultSet getOne(PreparedStatement statement) throws SQLException {
		ResultSet re = statement.executeQuery();
		re.next();
		return re;
	}

	public String getUsername(long playerId) {
		String sql = "SELECT username FROM players WHERE id=?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, playerId);
			return this.getOne(statement).getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
