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
								+ "(`irondomeID`, `targetID`, `time`, `isHit`) "
								+ "VALUES (?,?,?,?)";
			
			PreparedStatement ps = connection.prepareStatement(sqlQuery);
			
			ps.setString(1, irondomeID);
			ps.setString(2, enemyMissileId);
			ps.setTimestamp(3, getCurrentTime());
			ps.setNull(4, java.sql.Types.BOOLEAN);
			
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
								+ "(`destructorID`, `destructorType`, `targetID`, `time`, `isHit`) "
								+ "VALUES (?,?,?,?,?)";
			
			PreparedStatement ps = connection.prepareStatement(sqlQuery);
			
			ps.setString(1, destructorID);
			ps.setString(2, type);
			ps.setString(3, enemyLauncherId);
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
	public void defenseHitInterceptionMissile(String whoLaunchedMeId,
			String id, String enemyMissileId) {
		
		try {
			
			connection = DriverManager.getConnection(dbUrl, "root", "");
			
			String sqlQuery =	"UPDATE interceptions "
								+ "SET isHit = ? "
								+ "WHERE irondomeID = ? AND targetID = ?";
			
			PreparedStatement ps = connection.prepareStatement(sqlQuery);
			
			ps.setBoolean(1, true);
			ps.setString(2, whoLaunchedMeId);
			ps.setString(3, enemyMissileId);
			
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
		
//		theLogger.log(Level.INFO, whoLaunchedMeId + "\tIntercept: " + enemyLauncherId
//				+ "\t\t\tStatus: SUCCESS\n");
//		theLogger.log(Level.INFO, enemyLauncherId + "\tStatus: DESTROYED\n");
		
		try {
			
			connection = DriverManager.getConnection(dbUrl, "root", "");
			
			String sqlQuery =	"UPDATE destructions "
								+ "SET isHit = ? "
								+ "WHERE destructorID = ? AND targetID = ?";
			
			PreparedStatement ps = connection.prepareStatement(sqlQuery);
			
			ps.setBoolean(1, true);
			ps.setString(2, whoLaunchedMeId);
			ps.setString(3, enemyLauncherId);
			
			ps.executeUpdate();
			
			
			sqlQuery =	"UPDATE destructions "
						+ "SET isHit = ? "
						+ "WHERE destructorID = ? AND targetID = ?";

			ps = connection.prepareStatement(sqlQuery);

			ps.setBoolean(1, true);
			ps.setString(2, whoLaunchedMeId);
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
//		theLogger.log(Level.INFO, whoLaunchedMeId + "\tIntercept: " + enemyMissileId
//				+ "\t\t\tStatus: FAIL \t\t\tDamage: " + damage + "\n");
		

	}

	@Override
	public void defenseMissInterceptionHiddenLauncher(String whoLaunchedMeId,
			String type, String enemyLauncherId) {
//		theLogger.log(Level.INFO, whoLaunchedMeId + "\tIntercept: " + enemyLauncherId
//				+ "\t\t\tStatus: FAIL\t\t\tReason: Launcher is hidden\n");
		
	}

	@Override
	public void defenseMissInterceptionLauncher(String whoLaunchedMeId,
			String Type, String id, String enemyLauncherId) {
//		theLogger.log(Level.INFO, whoLaunchedMeId + "\tIntercept: " + enemyLauncherId
//				+ "\t\t\tStatus: FAIL\n");

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
//		String str = visible ? "visible\n" : "hidden\n";
//		theLogger.log(Level.INFO, id + "\tis now: " + str);
		
		
	}

	@Override
	public void enemyHitDestination(String whoLaunchedMeId, String id,
			String destination, int damage, String launchTime) {
//		theLogger.log(Level.INFO, whoLaunchedMeId + "\tTarget: " + destination + "\t\tStatus: HIT" + 
//			 "\t\t\tLand Time: " + Utils.getCurrentTime() + "\t\t\tDamage: " + damage + "\n");
		
		
	}

	@Override
	public void enemyMissDestination(String whoLaunchedMeId, String id,
			String destination, String launchTime) {
//		theLogger.log(Level.INFO, whoLaunchedMeId + "\tTarget: " + destination + "\t\tStatus: MISS" + 
//				 "\t\t\tLand Time: " + Utils.getCurrentTime() + "\n");
		
		
	}

	@Override
	public void warHasBeenStarted() {
//		theLogger.log(Level.INFO, "====== >\tWar started\t< ======\n");
	
	}

	@Override
	public void warHasBeenFinished() {
//		theLogger.log(Level.INFO, "====== >\tWar finished\t< ====== \n");
		
	}

	@Override
	public void missileNotExist(String defenseLauncherId, String enemyId) {
//		theLogger.log(Level.INFO, defenseLauncherId + "\tFAIL: missile " 
//					+ enemyId + " does not exist" + "\n");
		
		
	}

	@Override
	public void enemyLauncherNotExist(String defenseLauncherId,
			String launcherId) {
//		theLogger.log(Level.INFO, defenseLauncherId + "\tFAIL: launcher " 
//				+ launcherId + " does not exist" + "\n");
		
		
	}

	@Override
	public void noSuchObject(String type) {
		// Not needed
	}

	public void enemyLauncherAdded(String id) {
		
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
