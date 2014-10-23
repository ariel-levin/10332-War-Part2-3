package database;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import database.jpa.*;


public class WarJPA implements WarDB {

	private war.War warModel;
	private database.jpa.War dbWar;
	private EntityManager em;


	public WarJPA(war.War warModel) {
		this.warModel = warModel;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("War");
		em = emf.createEntityManager();
	}

	@Override
	public void defenseLaunchMissile(String irondomeID, String missileID,
			String enemyMissileId) {

		Interception inter = new Interception();
		inter.setIrondomeID(irondomeID);
		inter.setTargetID(enemyMissileId);
		inter.setIsHit(booleanToByte(false));
		inter.setTime(getCurrentTime());
		inter.setWar(dbWar);
		dbWar.getInterceptions().add(inter);

		insertToDB(inter);
	}

	@Override
	public void defenseLaunchMissile(String destructorID, String type,
			String missileId, String enemyLauncherId) {
		
		Destruction dest = new Destruction();
		dest.setDestructorID(destructorID);
		dest.setDestructorType(type);
		dest.setTargetID(enemyLauncherId);
		dest.setTime(getCurrentTime());
		dest.setIsHit(booleanToByte(false));
		dest.setWar(dbWar);
		dbWar.getDestructions().add(dest);
		
		insertToDB(dest);
	}

	@Override
	public void defenseHitInterceptionMissile(String whoLaunchedMeId,
			String id, String enemyMissileId) {

		// update interceptions
		List<Interception> interceptions = dbWar.getInterceptions();
		for (Interception i : interceptions) {

			if (i.getIrondomeID().equals(whoLaunchedMeId)
					&& i.getTargetID().equals(enemyMissileId)) {

				synchronized (em) {
					em.getTransaction().begin();
					try {
						Interception inter = em.find(Interception.class, i.getDbID());
						inter.setIsHit(booleanToByte(true));
						em.getTransaction().commit();

					} catch (Exception e) {}
				}
				break;
			}
		}
		
		// update launches
		List<Launch> launches = dbWar.getLaunches();
		for (Launch l : launches) {
			
			if (l.getId().getMissileID().equals(enemyMissileId)) {
				
				synchronized (em) {
					em.getTransaction().begin();
					try {
						Launch launch = em.find(Launch.class, l.getId());
						launch.setIsHit(booleanToByte(false));
						launch.setIsIntercepted(booleanToByte(true));
						launch.setWhoIntercepted(whoLaunchedMeId);
						em.getTransaction().commit();

					} catch (Exception e) {}
				}
				break;
			}
		}
	}

	@Override
	public void defenseHitInterceptionLauncher(String whoLaunchedMeId,
			String Type, String id, String enemyLauncherId) {

		// update destructions
		List<Destruction> destructions = dbWar.getDestructions();
		for (Destruction d : destructions) {

			if (d.getDestructorID().equals(whoLaunchedMeId)
					&& d.getTargetID().equals(enemyLauncherId)) {

				synchronized (em) {
					em.getTransaction().begin();
					try {
						Destruction dest = em.find(Destruction.class, d.getDbID());
						dest.setIsHit(booleanToByte(true));
						em.getTransaction().commit();

					} catch (Exception e) {}
				}
				break;
			}
		}
		
		// update launchers
		List<Launcher> launchers = dbWar.getLaunchers();
		for (Launcher l : launchers) {
			
			if (l.getId().getLauncherID().equals(enemyLauncherId)) {
				
				synchronized (em) {
					em.getTransaction().begin();
					try {
						Launcher launcher = em.find(Launcher.class, l.getId());
						launcher.setIsDestroyed(booleanToByte(true));
						em.getTransaction().commit();

					} catch (Exception e) {}
				}
				break;
			}
		}
		
	}

	@Override
	public void defenseMissInterceptionMissile(String whoLaunchedMeId,
			String id, String enemyMissileId, int damage) {

		List<Interception> interceptions = dbWar.getInterceptions();
		for (Interception i : interceptions) {

			if (i.getIrondomeID().equals(whoLaunchedMeId)
					&& i.getTargetID().equals(enemyMissileId)) {

				synchronized (em) {
					em.getTransaction().begin();
					try {
						Interception inter = em.find(Interception.class, i.getDbID());
						inter.setIsHit(booleanToByte(false));
						em.getTransaction().commit();

					} catch (Exception e) {}
				}
				break;
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

		List<Destruction> destructions = dbWar.getDestructions();
		for (Destruction d : destructions) {

			if (d.getDestructorID().equals(whoLaunchedMeId)
					&& d.getTargetID().equals(enemyLauncherId)) {

				synchronized (em) {
					em.getTransaction().begin();
					try {
						Destruction dest = em.find(Destruction.class, d.getDbID());
						dest.setIsHit(booleanToByte(false));
						em.getTransaction().commit();

					} catch (Exception e) {}
				}
				break;
			}
		}
	}

	@Override
	public void enemyLaunchMissile(String launcherID, String missileID,
			String destination, int damage) {

		Launch l = new Launch();
		LaunchPK lpk = new LaunchPK();
		lpk.setMissileID(missileID);
		lpk.setWarName(dbWar.getWarName());
		l.setId(lpk);
		l.setLauncherID(launcherID);
		l.setDamage(damage);
		l.setDestination(destination);
		l.setIsHit(booleanToByte(false));
		l.setIsIntercepted(booleanToByte(false));
		l.setTime(getCurrentTime());
		l.setWar(dbWar);
		dbWar.getLaunches().add(l);
		
		insertToDB(l);
	}

	@Override
	public void enemyLauncherIsVisible(String id, boolean visible) {

		List<Launcher> launchers = dbWar.getLaunchers();
		for (Launcher l : launchers) {
			
			if (l.getId().getLauncherID().equals(id)) {
				
				synchronized (em) {
					em.getTransaction().begin();
					try {
						Launcher launcher = em.find(Launcher.class, l.getId());
						launcher.setIsHidden(booleanToByte(visible));
						em.getTransaction().commit();

					} catch (Exception e) {}
				}
				break;
			}
		}
	}

	@Override
	public void enemyHitDestination(String whoLaunchedMeId, String id,
			String destination, int damage, String launchTime) {

		List<Launch> launches = dbWar.getLaunches();
		for (Launch l : launches) {
			
			if (l.getId().getMissileID().equals(id)) {
				
				synchronized (em) {
					em.getTransaction().begin();
					try {
						Launch launch = em.find(Launch.class, l.getId());
						launch.setIsHit(booleanToByte(true));
						launch.setIsIntercepted(booleanToByte(false));
						em.getTransaction().commit();

					} catch (Exception e) {}
				}
				break;
			}
		}
		
	}

	@Override
	public void enemyMissDestination(String whoLaunchedMeId, String id,
			String destination, String launchTime) {

		List<Launch> launches = dbWar.getLaunches();
		for (Launch l : launches) {
			
			if (l.getId().getMissileID().equals(id)) {
				
				synchronized (em) {
					em.getTransaction().begin();
					try {
						Launch launch = em.find(Launch.class, l.getId());
						launch.setIsHit(booleanToByte(false));
						launch.setIsIntercepted(booleanToByte(false));
						em.getTransaction().commit();

					} catch (Exception e) {}
				}
				break;
			}
		}
		
	}

	@Override
	public void warHasBeenStarted() {

		dbWar.setStartTime(getCurrentTime());
		insertToDB(dbWar);
		
		
		// add all launchers
		Vector<String> launchers = warModel.getAllLaunchersIds();
		for (String l : launchers) {
			Boolean isHidden = warModel.isLauncherHidden(l);
			enemyLauncherAdded(l,isHidden);
		}
		
		// add all iron domes
		Vector<String> irondomes = warModel.getAllIronDomesIds();
		for (String id : irondomes)
			ironDomeAdded(id);
		
		// add all launcher destructors
		Vector<String> destructors = warModel.getAllLauncherDestructorsIdAndType();
		int size = destructors.size();
		for (int i = 0 ; i < size-1 ; i+=2) {
			String id = destructors.get(i);
			String type = destructors.get(i+1);
			launcherDestructorAdded(id,type);
		}
		
	}

	@Override
	public void warHasBeenFinished() {

		synchronized (em) {
			
			em.getTransaction().begin();
			
			try {
				
				database.jpa.War war = em.find(database.jpa.War.class, dbWar.getWarName());
				war.setEndTime(getCurrentTime());
				em.getTransaction().commit();

			} catch (Exception e) {
				
			} finally {
				
				try {
					em.close();
				} catch (Exception e) {}
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

		Launcher l = new Launcher();
		LauncherPK lpk = new LauncherPK();
		lpk.setLauncherID(id);
		lpk.setWarName(dbWar.getWarName());
		l.setId(lpk);
		l.setWar(dbWar);
		l.setIsHidden(booleanToByte(isHidden));
		l.setIsDestroyed(booleanToByte(false));
		dbWar.getLaunchers().add(l);

		insertToDB(l);
	}

	public void ironDomeAdded(String id) {

		Irondome dome = new Irondome();
		IrondomePK domepk = new IrondomePK();
		domepk.setIrondomeID(id);
		domepk.setWarName(dbWar.getWarName());
		dome.setId(domepk);
		dome.setWar(dbWar);
		dbWar.getIrondomes().add(dome);

		insertToDB(dome);
	}

	public void launcherDestructorAdded(String id, String type) {

		Destructor ld = new Destructor();
		DestructorPK ldpk = new DestructorPK();
		ldpk.setDestructorID(id);
		ldpk.setWarName(dbWar.getWarName());
		ld.setId(ldpk);
		ld.setType(type);
		ld.setWar(dbWar);
		dbWar.getDestructors().add(ld);

		insertToDB(ld);
	}


	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////


	private Timestamp getCurrentTime() {
		java.util.Date time = new java.util.Date();
		return new Timestamp(time.getTime());
	}

	@Override
	public boolean setWarName(String warName) {
		if (isWarNameExist(warName))
			return false;
		else {
			dbWar = new database.jpa.War();
			dbWar.setWarName(warName);
			return true;
		}
	}

	private boolean isWarNameExist(String name) {

		synchronized (em) {
			
			try {
				database.jpa.War war = em.find(database.jpa.War.class, name);
				
				if (war == null)
					return false;
				else
					return true;

			} catch (Exception e) {} 
		}
		
		return false;
	}
	
	private byte booleanToByte(boolean value) {
		return (value) ? (byte)1 : (byte)0 ;
	}
	
	private synchronized void insertToDB(Object obj) {
		
		synchronized (em) {
			
			em.getTransaction().begin();
			
			try {
				em.persist(obj);
				em.getTransaction().commit();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				em.getTransaction().rollback();
			}
		}
	}
	

	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////


	@Override
	public long getNumOfLaunchMissiles(Calendar startDate, Calendar endDate) {
		Timestamp sqlStart = new Timestamp(startDate.getTime().getTime());
		Timestamp sqlEnd = new Timestamp(endDate.getTime().getTime());
		
		long count = 0;
		
		
		
		
//		synchronized (connection) {
//			try {
//				String sqlQuery = "SELECT * "
//								+ "FROM launches "
//								+ "WHERE launches.time BETWEEN ? AND ? "
//								+ "ORDER BY launches.time DESC";
//
//				PreparedStatement ps = connection.prepareStatement(sqlQuery);
//				
//				ps.setTimestamp(1, sqlStart);
//				ps.setTimestamp(2, sqlEnd);
//
//				ResultSet rs = ps.executeQuery();
//				
//				while (rs.next())
//					count++;
//
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
		
		return count;
	}
	
	@Override
	public long getNumOfInterceptMissiles(Calendar startDate, Calendar endDate) {
		Timestamp sqlStart = new Timestamp(startDate.getTime().getTime());
		Timestamp sqlEnd = new Timestamp(endDate.getTime().getTime());
		
		long count = 0;
		
		
		
		
		
//		synchronized (connection) {
//			try {
//				String sqlQuery = "SELECT * "
//								+ "FROM interceptions "
//								+ "WHERE interceptions.time BETWEEN ? AND ? "
//								+ 	"AND isHit = 1 "
//								+ "ORDER BY interceptions.time DESC";
//
//				PreparedStatement ps = connection.prepareStatement(sqlQuery);
//				
//				ps.setTimestamp(1, sqlStart);
//				ps.setTimestamp(2, sqlEnd);
//
//				ResultSet rs = ps.executeQuery();
//				
//				while (rs.next())
//					count++;
//
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
		
		return count;
	}
	
	@Override
	public long getNumOfHitTargetMissiles(Calendar startDate, Calendar endDate) {
		Timestamp sqlStart = new Timestamp(startDate.getTime().getTime());
		Timestamp sqlEnd = new Timestamp(endDate.getTime().getTime());
		
		long count = 0;
		
		
		
		
		
//		synchronized (connection) {
//			try {
//				String sqlQuery = "SELECT * "
//								+ "FROM launches "
//								+ "WHERE launches.time BETWEEN ? AND ? "
//								+	"AND isHit = 1 "
//								+ "ORDER BY launches.time DESC";
//
//				PreparedStatement ps = connection.prepareStatement(sqlQuery);
//				
//				ps.setTimestamp(1, sqlStart);
//				ps.setTimestamp(2, sqlEnd);
//
//				ResultSet rs = ps.executeQuery();
//				
//				while (rs.next())
//					count++;
//
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
		
		return count;
	}
	
	@Override
	public long getNumOfLaunchersDestroyed(Calendar startDate, Calendar endDate) {
		Timestamp sqlStart = new Timestamp(startDate.getTime().getTime());
		Timestamp sqlEnd = new Timestamp(endDate.getTime().getTime());
		
		long count = 0;
		
		
		
		
		
//		synchronized (connection) {
//			try {
//				String sqlQuery = "SELECT * "
//								+ "FROM destructions "
//								+ "WHERE destructions.time BETWEEN ? AND ? "
//								+	"AND isHit = 1 "
//								+ "ORDER BY destructions.time DESC";
//
//				PreparedStatement ps = connection.prepareStatement(sqlQuery);
//				
//				ps.setTimestamp(1, sqlStart);
//				ps.setTimestamp(2, sqlEnd);
//
//				ResultSet rs = ps.executeQuery();
//				
//				while (rs.next())
//					count++;
//
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
		
		return count;
	}
	
	@Override
	public long getTotalDamage(Calendar startDate, Calendar endDate) {
		Timestamp sqlStart = new Timestamp(startDate.getTime().getTime());
		Timestamp sqlEnd = new Timestamp(endDate.getTime().getTime());
		
		long sum = 0;
		
		
		
		
		
//		synchronized (connection) {
//			try {
//				String sqlQuery = "SELECT * "
//								+ "FROM launches "
//								+ "WHERE launches.time BETWEEN ? AND ? "
//								+	"AND isHit = 1 "
//								+ "ORDER BY launches.time DESC";
//
//				PreparedStatement ps = connection.prepareStatement(sqlQuery);
//				
//				ps.setTimestamp(1, sqlStart);
//				ps.setTimestamp(2, sqlEnd);
//
//				ResultSet rs = ps.executeQuery();
//				
//				while (rs.next())
//					sum += rs.getInt("damage");
//
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
		
		return sum;
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public static void main(String[] args) {
	
		WarJPA wardb = new WarJPA(null);
		
		wardb.setWarName("ARIEL");
		wardb.warHasBeenStarted();
		
//		wardb.enemyLauncherAdded("XXX", true);
//		wardb.enemyLaunchMissile("XXX", "ARIEL", "gaza", 1000);
//		wardb.defenseLaunchMissile("ARIEL", null, "ARIEL");
//		wardb.defenseHitInterceptionMissile("ARIEL", null, "ARIEL");
//		wardb.defenseLaunchMissile("XXX", "XXX", missileId, enemyLauncherId);
		
//		System.out.println("\nIs war name exist? " + wardb.isWarNameExist("LOLI"));
		
//		wardb.test();

		wardb.warHasBeenFinished();
	}
		
	public void test() {

		Interception inter = new Interception();
        //a1.setId(8);
		inter.setIrondomeID("ARIEL");
		inter.setTargetID("ARIEL");
		inter.setIsHit(booleanToByte(false));
		inter.setTime(getCurrentTime());
		inter.setWar(dbWar);
		dbWar.getInterceptions().add(inter);
           
		em.getTransaction().begin();
		
        try {
            em.persist(inter);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("In catch: " + e.getMessage());
            em.getTransaction().rollback();
        } finally {
            System.out.println("In finally");
            em.close();
        }
        System.out.println("done");

	}

}
