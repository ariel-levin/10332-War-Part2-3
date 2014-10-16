package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;


public class WarJDBC implements WarDB {
	
//	private final String[] allTables = {"launchers","launches","irondomes",
//										"interceptions","destructors","destructions"};
	
	private Connection connection = null;
	private String dbUrl;
	private String warName;
	
	
	public WarJDBC() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			dbUrl = "jdbc:mysql://localhost/war";
			
//			clearAllTables();

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
		
	}
	
	@Override
	public void defenseLaunchMissile(String irondomeID, String missileID,
			String enemyMissileId) {
		
		try {
			
			connection = DriverManager.getConnection(dbUrl, "root", "");
			
			String sqlQuery = 	"INSERT INTO `war`.`interceptions` "
								+ "(`warName`, `irondomeID`, `targetID`, `time`, `isHit`) "
								+ "VALUES (?,?,?,?,?)";
			
			PreparedStatement ps = connection.prepareStatement(sqlQuery);
			
			ps.setString(1, this.warName);
			ps.setString(2, irondomeID);
			ps.setString(3, enemyMissileId);
			ps.setTimestamp(4, getCurrentTime());
			ps.setNull(5, java.sql.Types.BOOLEAN);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void defenseLaunchMissile(String destructorID, String type,
			String missileId, String enemyLauncherId) {
		
		try {
			
			connection = DriverManager.getConnection(dbUrl, "root", "");
			
			String sqlQuery = 	"INSERT INTO `war`.`destructions` "
								+ "(`warName`, `destructorID`, `destructorType`, `targetID`, `time`, `isHit`) "
								+ "VALUES (?,?,?,?,?,?)";
			
			PreparedStatement ps = connection.prepareStatement(sqlQuery);
			
			ps.setString(1, this.warName);
			ps.setString(2, destructorID);
			ps.setString(3, type);
			ps.setString(4, enemyLauncherId);
			ps.setTimestamp(5, getCurrentTime());
			ps.setNull(6, java.sql.Types.BOOLEAN);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void defenseHitInterceptionMissile(String whoLaunchedMeId,
			String id, String enemyMissileId) {
		
		try {
			
			connection = DriverManager.getConnection(dbUrl, "root", "");
			
			String sqlQuery =	"UPDATE interceptions "
								+ "SET isHit = ? "
								+ "WHERE warName = ? "
									+ "AND irondomeID = ? "
									+ "AND targetID = ?";
			
			PreparedStatement ps = connection.prepareStatement(sqlQuery);
			
			ps.setBoolean(1, true);
			ps.setString(2, this.warName);
			ps.setString(3, whoLaunchedMeId);
			ps.setString(4, enemyMissileId);
			
			ps.executeUpdate();
			
			
			// update launches table
			sqlQuery =	"UPDATE launches "
					+ "SET isHit = ?"
					+	", isIntercepted = ?"
					+	", whoIntercepted = ?"
					+ "WHERE warName = ? "
						+ "AND missileID = ?";

			ps = connection.prepareStatement(sqlQuery);

			ps.setBoolean(1, false);
			ps.setBoolean(2, true);
			ps.setString(3, whoLaunchedMeId);
			ps.setString(4, this.warName);
			ps.setString(5, enemyMissileId);

			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void defenseHitInterceptionLauncher(String whoLaunchedMeId,
			String Type, String id, String enemyLauncherId) {
		
		try {
			
			connection = DriverManager.getConnection(dbUrl, "root", "");
			
			String sqlQuery =	"UPDATE destructions "
								+ "SET isHit = ? "
								+ "WHERE warName = ? "
									+ "AND destructorID = ? "
									+ "AND targetID = ?";
			
			PreparedStatement ps = connection.prepareStatement(sqlQuery);
			
			ps.setBoolean(1, true);
			ps.setString(2, this.warName);
			ps.setString(3, whoLaunchedMeId);
			ps.setString(4, enemyLauncherId);
			
			ps.executeUpdate();
			
			
			// update launchers table			
			sqlQuery =	"UPDATE launchers "
						+ "SET isDestroyed = ? "
						+ "WHERE warName = ? AND launcherID = ?";

			ps = connection.prepareStatement(sqlQuery);

			ps.setBoolean(1, true);
			ps.setString(2, this.warName);
			ps.setString(3, enemyLauncherId);

			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void defenseMissInterceptionMissile(String whoLaunchedMeId,
			String id, String enemyMissileId, int damage) {

		try {
			
			connection = DriverManager.getConnection(dbUrl, "root", "");
			
			String sqlQuery =	"UPDATE interceptions "
								+ "SET isHit = ? "
								+ "WHERE warName = ? "
									+ "AND irondomeID = ? "
									+ "AND targetID = ?";
			
			PreparedStatement ps = connection.prepareStatement(sqlQuery);
			
			ps.setBoolean(1, false);
			ps.setString(2, this.warName);
			ps.setString(3, whoLaunchedMeId);
			ps.setString(4, enemyMissileId);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void defenseMissInterceptionHiddenLauncher(String whoLaunchedMeId,
			String type, String enemyLauncherId) {
		
		defenseMissInterceptionLauncher(whoLaunchedMeId,type,null,enemyLauncherId);
	}

	@Override
	public void defenseMissInterceptionLauncher(String whoLaunchedMeId,
			String Type, String id, String enemyLauncherId) {

		try {
			
			connection = DriverManager.getConnection(dbUrl, "root", "");
			
			String sqlQuery =	"UPDATE destructions "
								+ "SET isHit = ? "
								+ "WHERE warName = ? "
									+ "AND destructorID = ? "
									+ "AND targetID = ?";
			
			PreparedStatement ps = connection.prepareStatement(sqlQuery);
			
			ps.setBoolean(1, false);
			ps.setString(2, this.warName);
			ps.setString(3, whoLaunchedMeId);
			ps.setString(4, enemyLauncherId);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void enemyLaunchMissile(String launcherID, String missileID,
			String destination, int damage) {

		try {
			
			connection = DriverManager.getConnection(dbUrl, "root", "");
			
			String sqlQuery = "INSERT INTO `war`.`launches` "
							+ "(`launcherID`, `missileID`, `destination`, `time`, "
							+ "`damage`, `isHit`, `isIntercepted`, `whoIntercepted`) "
							+ "VALUES (?,?,?,?,?,?,?,?)";
			
			PreparedStatement ps = connection.prepareStatement(sqlQuery);
			
			ps.setString(1, launcherID);
			ps.setString(2, missileID);
			ps.setString(3, destination);
			ps.setTimestamp(4, getCurrentTime());
			ps.setInt(5, damage);
			ps.setNull(6, java.sql.Types.BOOLEAN);
			ps.setNull(7, java.sql.Types.BOOLEAN);
			ps.setNull(8, java.sql.Types.VARCHAR);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void enemyLauncherIsVisible(String id, boolean visible) {

		try {
			connection = DriverManager.getConnection(dbUrl, "root", "");
			
			String sqlQuery =	"UPDATE launchers "
								+ "SET isHidden = ? "
								+ "WHERE warName = ? AND launcherID = ?";

			PreparedStatement ps = connection.prepareStatement(sqlQuery);

			ps.setBoolean(1, visible);
			ps.setString(2, this.warName);
			ps.setString(3, id);

			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void enemyHitDestination(String whoLaunchedMeId, String id,
			String destination, int damage, String launchTime) {

		try {
			connection = DriverManager.getConnection(dbUrl, "root", "");

			String sqlQuery =	"UPDATE launches "
								+ "SET isHit = ?"
								+	", isIntercepted = ?"
								+ "WHERE warName = ? "
								+ 	"AND missileID = ?";

			PreparedStatement ps = connection.prepareStatement(sqlQuery);

			ps.setBoolean(1, true);
			ps.setBoolean(2, false);
			ps.setString(3, this.warName);
			ps.setString(4, id);

			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void enemyMissDestination(String whoLaunchedMeId, String id,
			String destination, String launchTime) {

		try {
			connection = DriverManager.getConnection(dbUrl, "root", "");

			String sqlQuery =	"UPDATE launches "
								+ "SET isHit = ?"
								+	", isIntercepted = ?"
								+ "WHERE warName = ? "
								+ 	"AND missileID = ?";

			PreparedStatement ps = connection.prepareStatement(sqlQuery);

			ps.setBoolean(1, false);
			ps.setBoolean(2, false);
			ps.setString(3, this.warName);
			ps.setString(4, id);

			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void warHasBeenStarted() {
		try {
			connection = DriverManager.getConnection(dbUrl, "root", "");

			String sqlQuery =	"INSERT INTO `war`.`wars` "
								+ "(`warName`, `startTime`, `endTime`)"
								+ "VALUES (?,?,?)";

			PreparedStatement ps = connection.prepareStatement(sqlQuery);

			ps.setString(1, this.warName);
			ps.setTimestamp(2, getCurrentTime());
			ps.setNull(3, java.sql.Types.TIMESTAMP);

			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void warHasBeenFinished() {
		try {
			connection = DriverManager.getConnection(dbUrl, "root", "");
			
			String sqlQuery =	"UPDATE wars "
								+ "SET endTime = ? "
								+ "WHERE warName = ?";

			PreparedStatement ps = connection.prepareStatement(sqlQuery);

			ps.setTimestamp(1, getCurrentTime());
			ps.setString(2, this.warName);

			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}

	@Override
	public void missileNotExist(String defenseLauncherId, String enemyId) {
		// Not needed
		
//		theLogger.log(Level.INFO, defenseLauncherId + "\tFAIL: missile " 
//					+ enemyId + " does not exist" + "\n");
	}

	@Override
	public void enemyLauncherNotExist(String defenseLauncherId,
			String launcherId) {
		// Not needed
		
//		theLogger.log(Level.INFO, defenseLauncherId + "\tFAIL: launcher " 
//				+ launcherId + " does not exist" + "\n");
	}

	@Override
	public void noSuchObject(String type) {
		// Not needed
	}

	public void enemyLauncherAdded(String id) {
//		try {
//			
//			connection = DriverManager.getConnection(dbUrl, "root", "");
//			
//			String sqlQuery = "INSERT INTO `war`.`launches` "
//							+ "(`launcherID`, `missileID`, `destination`, `time`, "
//							+ "`damage`, `isHit`, `isIntercepted`, `whoIntercepted`) "
//							+ "VALUES (?,?,?,?,?,?,?,?)";
//			
//			PreparedStatement ps = connection.prepareStatement(sqlQuery);
//			
//			ps.setString(1, launcherID);
//			ps.setString(2, missileID);
//			ps.setString(3, destination);
//			ps.setTimestamp(4, getCurrentTime());
//			ps.setInt(5, damage);
//			ps.setNull(6, java.sql.Types.BOOLEAN);
//			ps.setNull(7, java.sql.Types.BOOLEAN);
//			ps.setNull(8, java.sql.Types.VARCHAR);
//			
//			ps.executeUpdate();
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				connection.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	public void ironDomeAdded(String id) {
		
	}
	
	public void launcherDestructorAdded(String id, String type) {
		
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	
	
//	private void clearAllTables() throws SQLException {
//		connection = DriverManager.getConnection(dbUrl, "root", "");
//		Statement statement = connection.createStatement();
//		
//		for (String table : allTables)
//			statement.executeUpdate("DELETE FROM " + table);
//		
//		connection.close();
//	}
	
	private Timestamp getCurrentTime() {
		java.util.Date time = new java.util.Date();
		return new Timestamp(time.getTime());
	}
		
	@Override
	public boolean setWarName(String warName) {
		if (isWarNameExist(warName))
			return false;
		else {
			this.warName = warName;
			return true;
		}
	}
	
	private boolean isWarNameExist(String name) {
		try {
			connection = DriverManager.getConnection(dbUrl, "root", "");
			
			String sqlQuery =	"SELECT TOP 1 wars.warName"
								+ "FROM wars"
								+ "WHERE wars.warName = ?";
			
			PreparedStatement ps = connection.prepareStatement(sqlQuery);
			
			ps.setString(1, name);
			
			ResultSet rs = ps.executeQuery();
			
			boolean isExist = rs.first();

			return isExist;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	
	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	
	
	// for test
	public static void main(String[] args) {
		WarJDBC jdbc = new WarJDBC();
		jdbc.enemyLaunchMissile("L101","M28","gaza strip",1989);
		jdbc.defenseHitInterceptionLauncher("test",null,null,"test");
	}
	
	public void printTest() {
		
		try {
			connection = DriverManager.getConnection(dbUrl, "root", "");
			Statement statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery("SELECT * FROM launches");
			
			int count = 0;
			while (rs.next())	{
				System.out.println(	++count + ". " +
						"Launcher " + rs.getString("launcherID") +
						", Missile " + rs.getString("missileID") +
						", Destination: " + rs.getString("destination") +
						", Time: " + rs.getTimestamp("time") +
						", flyTime: " + rs.getString("flyTime") +
						", damage: " + rs.getString("damage") +
						", Intercepted? " + (rs.getInt("intercepted")==1)	
						);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		
	}

}
