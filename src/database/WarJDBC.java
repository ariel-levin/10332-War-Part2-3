package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JOptionPane;

import model.War;


/** 
 * @author	<a href="http://about.me/ariel.levin">Ariel Levin</a><br>
 * 			<a href="mailto:ariel2011@gmail.com">ariel2011@gmail.com</a><br>
 *			<a href="http://github.com/ariel-levin">github.com/ariel-levin</a>
 * */
public class WarJDBC implements WarDB {

	private Connection connection;
	private War war;
	private String dbUrl;
	private String warName;


	public WarJDBC() {
		openConnection();
	}
	
	public WarJDBC(War war) {
		this.war = war;
		openConnection();
	}
	
	private void openConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			dbUrl = "jdbc:mysql://localhost/war";

			connection = DriverManager.getConnection(dbUrl, "root", "");

		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {

			JOptionPane.showMessageDialog(null,"Error connecting to DB\n"
					+ "Please connect to Wamp first","Database Error",JOptionPane.ERROR_MESSAGE);
			
			System.exit(0);
		}	
	}
	
	@Override
	public void defenseLaunchMissile(String irondomeID, String missileID,
			String enemyMissileId) {

		synchronized (connection) {
			try {
				String sqlQuery = 	"INSERT INTO `war`.`interceptions` "
						+ "(`warName`, `irondomeID`, `targetID`, `time`, `isHit`) "
						+ "VALUES (?,?,?,?,?)";

				PreparedStatement ps = connection.prepareStatement(sqlQuery);

				ps.setString(1, this.warName);
				ps.setString(2, irondomeID);
				ps.setString(3, enemyMissileId);
				ps.setTimestamp(4, getCurrentTime());
				ps.setBoolean(5, false);

				ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void defenseLaunchMissile(String destructorID, String type,
			String missileId, String enemyLauncherId) {

		synchronized (connection) {
			try {
				String sqlQuery = 	"INSERT INTO `war`.`destructions` "
						+ "(`warName`, `destructorID`, `destructorType`, `targetID`, `time`, `isHit`) "
						+ "VALUES (?,?,?,?,?,?)";

				PreparedStatement ps = connection.prepareStatement(sqlQuery);

				ps.setString(1, this.warName);
				ps.setString(2, destructorID);
				ps.setString(3, type);
				ps.setString(4, enemyLauncherId);
				ps.setTimestamp(5, getCurrentTime());
				ps.setBoolean(6, false);

				ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void defenseHitInterceptionMissile(String whoLaunchedMeId,
			String id, String enemyMissileId) {

		synchronized (connection) {
			try {
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
						+	", whoIntercepted = ? "
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
			}
		}

	}

	@Override
	public void defenseHitInterceptionLauncher(String whoLaunchedMeId,
			String Type, String id, String enemyLauncherId) {

		synchronized (connection) {
			try {
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
			}
		}
	}

	@Override
	public void defenseMissInterceptionMissile(String whoLaunchedMeId,
			String id, String enemyMissileId, int damage) {

		synchronized (connection) {
			try {
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

		synchronized (connection) {
			try {
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
			}
		}

	}

	@Override
	public void enemyLaunchMissile(String launcherID, String missileID,
			String destination, int damage) {

		synchronized (connection) {
			try {
				String sqlQuery = "INSERT INTO `war`.`launches` "
						+ "(`warName`, `launcherID`, `missileID`, `destination`, "
						+ "`time`, `damage`, `isHit`, `isIntercepted`, `whoIntercepted`) "
						+ "VALUES (?,?,?,?,?,?,?,?,?)";

				PreparedStatement ps = connection.prepareStatement(sqlQuery);

				ps.setString(1, this.warName);
				ps.setString(2, launcherID);
				ps.setString(3, missileID);
				ps.setString(4, destination);
				ps.setTimestamp(5, getCurrentTime());
				ps.setInt(6, damage);
				ps.setBoolean(7, false);
				ps.setBoolean(8, false);
				ps.setNull(9, java.sql.Types.VARCHAR);

				ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void enemyLauncherIsVisible(String id, boolean visible) {

		synchronized (connection) {
			try {
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
			}
		}
	}

	@Override
	public void enemyHitDestination(String whoLaunchedMeId, String id,
			String destination, int damage, String launchTime) {

		synchronized (connection) {
			try {
				String sqlQuery =	"UPDATE launches "
						+ "SET isHit = ?"
						+	", isIntercepted = ? "
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
			}
		}
	}

	@Override
	public void enemyMissDestination(String whoLaunchedMeId, String id,
			String destination, String launchTime) {

		synchronized (connection) {
			try {
				String sqlQuery =	"UPDATE launches "
						+ "SET isHit = ?"
						+	", isIntercepted = ? "
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
			}
		}
	}

	@Override
	public void warHasBeenStarted() {

		synchronized (connection) {
			try {
				String sqlQuery =	"INSERT INTO `war`.`wars` "
						+ "(`warName`, `startTime`, `endTime`) "
						+ "VALUES (?,?,?)";

				PreparedStatement ps = connection.prepareStatement(sqlQuery);

				ps.setString(1, this.warName);
				ps.setTimestamp(2, getCurrentTime());
				ps.setNull(3, java.sql.Types.TIMESTAMP);

				ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// add all launchers
		Vector<String> launchers = war.getAllLaunchersIds();
		for (String l : launchers) {
			Boolean isHidden = war.isLauncherHidden(l);
			enemyLauncherAdded(l,isHidden);
		}
		
		// add all iron domes
		Vector<String> irondomes = war.getAllIronDomesIds();
		for (String id : irondomes)
			ironDomeAdded(id);
		
		// add all launcher destructors
		Vector<String> destructors = war.getAllLauncherDestructorsIdAndType();
		int size = destructors.size();
		for (int i = 0 ; i < size-1 ; i+=2) {
			String id = destructors.get(i);
			String type = destructors.get(i+1);
			launcherDestructorAdded(id,type);
		}
		
	}

	@Override
	public void warHasBeenFinished() {

		synchronized (connection) {
			try {
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

	public void enemyLauncherAdded(String id, boolean isHidden) {

		synchronized (connection) {
			try {
				String sqlQuery = "INSERT INTO `war`.`launchers` "
						+ "(`launcherID`, `warName`, `isHidden`, `isDestroyed`) "
						+ "VALUES (?,?,?,?)";

				PreparedStatement ps = connection.prepareStatement(sqlQuery);

				ps.setString(1, id);
				ps.setString(2, this.warName);
				ps.setBoolean(3, isHidden);
				ps.setBoolean(4, false);

				ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void ironDomeAdded(String id) {

		synchronized (connection) {
			try {
				String sqlQuery = "INSERT INTO `war`.`irondomes` "
						+ "(`irondomeID`, `warName`) "
						+ "VALUES (?,?)";

				PreparedStatement ps = connection.prepareStatement(sqlQuery);

				ps.setString(1, id);
				ps.setString(2, this.warName);

				ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void launcherDestructorAdded(String id, String type) {

		synchronized (connection) {
			try {
				String sqlQuery = "INSERT INTO `war`.`destructors` "
						+ "(`destructorID`, `warName`, `type`) "
						+ "VALUES (?,?,?)";

				PreparedStatement ps = connection.prepareStatement(sqlQuery);

				ps.setString(1, id);
				ps.setString(2, this.warName);
				ps.setString(3, type);

				ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////

	
	public void setWar(War war) {
		this.war = war;
	}

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

		synchronized (connection) {
			try {
				String sqlQuery =	"SELECT wars.warName "
						+ "FROM wars "
						+ "WHERE wars.warName = ?";

				PreparedStatement ps = connection.prepareStatement(sqlQuery);

				ps.setString(1, name);

				ResultSet rs = ps.executeQuery();

				boolean isExist = rs.first();

				return isExist;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}


	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////


	@Override
	public long getNumOfLaunchMissiles(Calendar startDate, Calendar endDate) {
		Timestamp sqlStart = new Timestamp(startDate.getTime().getTime());
		Timestamp sqlEnd = new Timestamp(endDate.getTime().getTime());
		
		long count = 0;
		
		synchronized (connection) {
			try {
				String sqlQuery = "SELECT * "
								+ "FROM launches "
								+ "WHERE launches.time BETWEEN ? AND ? "
								+ "ORDER BY launches.time DESC";

				PreparedStatement ps = connection.prepareStatement(sqlQuery);
				
				ps.setTimestamp(1, sqlStart);
				ps.setTimestamp(2, sqlEnd);

				ResultSet rs = ps.executeQuery();
				
				while (rs.next())
					count++;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return count;
	}
	
	@Override
	public long getNumOfInterceptMissiles(Calendar startDate, Calendar endDate) {
		Timestamp sqlStart = new Timestamp(startDate.getTime().getTime());
		Timestamp sqlEnd = new Timestamp(endDate.getTime().getTime());
		
		long count = 0;
		
		synchronized (connection) {
			try {
				String sqlQuery = "SELECT * "
								+ "FROM interceptions "
								+ "WHERE interceptions.time BETWEEN ? AND ? "
								+ 	"AND isHit = 1 "
								+ "ORDER BY interceptions.time DESC";

				PreparedStatement ps = connection.prepareStatement(sqlQuery);
				
				ps.setTimestamp(1, sqlStart);
				ps.setTimestamp(2, sqlEnd);

				ResultSet rs = ps.executeQuery();
				
				while (rs.next())
					count++;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return count;
	}
	
	@Override
	public long getNumOfHitTargetMissiles(Calendar startDate, Calendar endDate) {
		Timestamp sqlStart = new Timestamp(startDate.getTime().getTime());
		Timestamp sqlEnd = new Timestamp(endDate.getTime().getTime());
		
		long count = 0;
		
		synchronized (connection) {
			try {
				String sqlQuery = "SELECT * "
								+ "FROM launches "
								+ "WHERE launches.time BETWEEN ? AND ? "
								+	"AND isHit = 1 "
								+ "ORDER BY launches.time DESC";

				PreparedStatement ps = connection.prepareStatement(sqlQuery);
				
				ps.setTimestamp(1, sqlStart);
				ps.setTimestamp(2, sqlEnd);

				ResultSet rs = ps.executeQuery();
				
				while (rs.next())
					count++;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return count;
	}
	
	@Override
	public long getNumOfLaunchersDestroyed(Calendar startDate, Calendar endDate) {
		Timestamp sqlStart = new Timestamp(startDate.getTime().getTime());
		Timestamp sqlEnd = new Timestamp(endDate.getTime().getTime());
		
		long count = 0;
		
		synchronized (connection) {
			try {
				String sqlQuery = "SELECT * "
								+ "FROM destructions "
								+ "WHERE destructions.time BETWEEN ? AND ? "
								+	"AND isHit = 1 "
								+ "ORDER BY destructions.time DESC";

				PreparedStatement ps = connection.prepareStatement(sqlQuery);
				
				ps.setTimestamp(1, sqlStart);
				ps.setTimestamp(2, sqlEnd);

				ResultSet rs = ps.executeQuery();
				
				while (rs.next())
					count++;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return count;
	}
	
	@Override
	public long getTotalDamage(Calendar startDate, Calendar endDate) {
		Timestamp sqlStart = new Timestamp(startDate.getTime().getTime());
		Timestamp sqlEnd = new Timestamp(endDate.getTime().getTime());
		
		long sum = 0;
		
		synchronized (connection) {
			try {
				String sqlQuery = "SELECT * "
								+ "FROM launches "
								+ "WHERE launches.time BETWEEN ? AND ? "
								+	"AND isHit = 1 "
								+ "ORDER BY launches.time DESC";

				PreparedStatement ps = connection.prepareStatement(sqlQuery);
				
				ps.setTimestamp(1, sqlStart);
				ps.setTimestamp(2, sqlEnd);

				ResultSet rs = ps.executeQuery();
				
				while (rs.next())
					sum += rs.getInt("damage");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return sum;
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public void printTest() {

		try {
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
		}	

	}

}
