package database;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TemporalType;

import database.jpa.*;


public class WarJPA implements WarDB {

	private model.War warModel;
	private database.jpa.War dbWar;
	private EntityManagerFactory emf;
	

	public WarJPA() {
		emf = Persistence.createEntityManagerFactory("War");
	}
	
	public WarJPA(model.War warModel) {
		this.warModel = warModel;
		emf = Persistence.createEntityManagerFactory("War");
	}

	@Override
	public void defenseLaunchMissile(String irondomeID, String missileID,
			String enemyMissileId) {

		Interception inter = new Interception();
		inter.setIrondomeID(irondomeID);
		inter.setTargetID(enemyMissileId);
		inter.setIsHit(false);
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
		dest.setIsHit(false);
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

				synchronized (emf) {
					EntityManager em = emf.createEntityManager();
					em.getTransaction().begin();
					try {
						Interception inter = em.find(Interception.class, i.getDbID());
						inter.setIsHit(true);
						em.getTransaction().commit();

					} catch (Exception e) {
						
					} finally {
						em.close();
					}
				}
				break;
			}
		}
		
		// update launches
		List<Launch> launches = dbWar.getLaunches();
		for (Launch l : launches) {
			
			if (l.getId().getMissileID().equals(enemyMissileId)) {
				
				synchronized (emf) {
					EntityManager em = emf.createEntityManager();
					em.getTransaction().begin();
					try {
						Launch launch = em.find(Launch.class, l.getId());
						launch.setIsHit(false);
						launch.setIsIntercepted(true);
						launch.setWhoIntercepted(whoLaunchedMeId);
						em.getTransaction().commit();

					} catch (Exception e) {
						
					} finally {
						em.close();
					}
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

				synchronized (emf) {
					EntityManager em = emf.createEntityManager();
					em.getTransaction().begin();
					try {
						Destruction dest = em.find(Destruction.class, d.getDbID());
						dest.setIsHit(true);
						em.getTransaction().commit();

					} catch (Exception e) {
						
					} finally {
						em.close();
					}
				}
				break;
			}
		}
		
		// update launchers
		List<Launcher> launchers = dbWar.getLaunchers();
		for (Launcher l : launchers) {
			
			if (l.getId().getLauncherID().equals(enemyLauncherId)) {
				
				synchronized (emf) {
					EntityManager em = emf.createEntityManager();
					em.getTransaction().begin();
					try {
						Launcher launcher = em.find(Launcher.class, l.getId());
						launcher.setIsDestroyed(true);
						em.getTransaction().commit();

					} catch (Exception e) {
						
					} finally {
						em.close();
					}
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

				synchronized (emf) {
					EntityManager em = emf.createEntityManager();
					em.getTransaction().begin();
					try {
						Interception inter = em.find(Interception.class, i.getDbID());
						inter.setIsHit(false);
						em.getTransaction().commit();

					} catch (Exception e) {
						
					} finally {
						em.close();
					}
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

				synchronized (emf) {
					EntityManager em = emf.createEntityManager();
					em.getTransaction().begin();
					try {
						Destruction dest = em.find(Destruction.class, d.getDbID());
						dest.setIsHit(false);
						em.getTransaction().commit();

					} catch (Exception e) {
						
					} finally {
						em.close();
					}
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
		l.setIsHit(false);
		l.setIsIntercepted(false);
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
				
				synchronized (emf) {
					EntityManager em = emf.createEntityManager();
					em.getTransaction().begin();
					try {
						Launcher launcher = em.find(Launcher.class, l.getId());
						launcher.setIsHidden(visible);
						em.getTransaction().commit();

					} catch (Exception e) {
						
					} finally {
						em.close();
					}
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
				
				synchronized (emf) {
					EntityManager em = emf.createEntityManager();
					em.getTransaction().begin();
					try {
						Launch launch = em.find(Launch.class, l.getId());
						launch.setIsHit(true);
						launch.setIsIntercepted(false);
						em.getTransaction().commit();

					} catch (Exception e) {
						
					} finally {
						em.close();
					}
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
				
				synchronized (emf) {
					EntityManager em = emf.createEntityManager();
					em.getTransaction().begin();
					try {
						Launch launch = em.find(Launch.class, l.getId());
						launch.setIsHit(false);
						launch.setIsIntercepted(false);
						em.getTransaction().commit();

					} catch (Exception e) {
						
					} finally {
						em.close();
					}
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

		synchronized (emf) {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			
			try {
				
				database.jpa.War war = em.find(database.jpa.War.class, dbWar.getWarName());
				war.setEndTime(getCurrentTime());
				em.getTransaction().commit();

			} catch (Exception e) {
				
			} finally {
				
				try {
					em.close();
					emf.close();
					
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
		l.setIsHidden(isHidden);
		l.setIsDestroyed(false);
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

	public void setWar(model.War war) {
		this.warModel = war;
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
			dbWar = new database.jpa.War();
			dbWar.setWarName(warName);
			return true;
		}
	}

	private boolean isWarNameExist(String name) {

		synchronized (emf) {
			EntityManager em = emf.createEntityManager();
			
			try {
				database.jpa.War war = em.find(database.jpa.War.class, name);
				
				if (war == null)
					return false;
				else
					return true;

			} catch (Exception e) {
				
			} finally {
				em.close();
			}
		}
		
		return false;
	}
	
	private void insertToDB(Object obj) {
		
		synchronized (emf) {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			
			try {
				em.persist(obj);
				em.getTransaction().commit();
			} catch (Exception e) {}
			finally {
				em.close();
			}
		}
	}
	

	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////


	@SuppressWarnings("unchecked")
	@Override
	public long getNumOfLaunchMissiles(Calendar startDate, Calendar endDate) {
		Timestamp sqlStart = new Timestamp(startDate.getTime().getTime());
		Timestamp sqlEnd = new Timestamp(endDate.getTime().getTime());

		List<Launch> launches = null;
		
		synchronized (emf) {
			EntityManager em = emf.createEntityManager();
			
			launches = em.createQuery(
					"SELECT l FROM Launch l "
						+ "WHERE l.time BETWEEN :startDate AND :endDate "
						+ "ORDER BY l.time DESC")

						.setParameter("startDate", sqlStart, TemporalType.DATE)
						.setParameter("endDate", sqlEnd, TemporalType.DATE).getResultList();
			
			em.close();
		}
		
		if (launches == null)
			return 0;
		else
			return launches.size();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public long getNumOfInterceptMissiles(Calendar startDate, Calendar endDate) {
		Timestamp sqlStart = new Timestamp(startDate.getTime().getTime());
		Timestamp sqlEnd = new Timestamp(endDate.getTime().getTime());
		
		List<Interception> interceptions = null;
		
		synchronized (emf) {
			EntityManager em = emf.createEntityManager();
			
			interceptions = em.createQuery(
					"SELECT i FROM Interception i "
						+ "WHERE i.time BETWEEN :startDate AND :endDate "
						+ "AND i.isHit = 1 "
						+ "ORDER BY i.time DESC")

						.setParameter("startDate", sqlStart)
						.setParameter("endDate", sqlEnd).getResultList();
		
			em.close();
		}

		if (interceptions == null)
			return 0;
		else
			return interceptions.size();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public long getNumOfHitTargetMissiles(Calendar startDate, Calendar endDate) {
		Timestamp sqlStart = new Timestamp(startDate.getTime().getTime());
		Timestamp sqlEnd = new Timestamp(endDate.getTime().getTime());

		List<Launch> launches = null;
		
		synchronized (emf) {
			EntityManager em = emf.createEntityManager();
			
			launches = em.createQuery(
					"SELECT l FROM Launch l "
						+ "WHERE l.time BETWEEN :startDate AND :endDate "
						+ "AND l.isHit = 1 "
						+ "ORDER BY l.time DESC")

						.setParameter("startDate", sqlStart)
						.setParameter("endDate", sqlEnd).getResultList();
			
			em.close();
		}
		
		if (launches == null)
			return 0;
		else
			return launches.size();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public long getNumOfLaunchersDestroyed(Calendar startDate, Calendar endDate) {
		Timestamp sqlStart = new Timestamp(startDate.getTime().getTime());
		Timestamp sqlEnd = new Timestamp(endDate.getTime().getTime());
		
		List<Destruction> destructions = null;
		
		synchronized (emf) {
			EntityManager em = emf.createEntityManager();
			
			destructions = em.createQuery(
					"SELECT d FROM Destruction d "
						+ "WHERE d.time BETWEEN :startDate AND :endDate "
						+ "AND d.isHit = 1 "
						+ "ORDER BY d.time DESC")

						.setParameter("startDate", sqlStart)
						.setParameter("endDate", sqlEnd).getResultList();
			
			em.close();
		}
		
		if (destructions == null)
			return 0;
		else
			return destructions.size();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public long getTotalDamage(Calendar startDate, Calendar endDate) {
		Timestamp sqlStart = new Timestamp(startDate.getTime().getTime());
		Timestamp sqlEnd = new Timestamp(endDate.getTime().getTime());
		
		long sum = 0;
		List<Launch> launches = null;
		
		synchronized (emf) {
			EntityManager em = emf.createEntityManager();
			
			launches = em.createQuery(
					"SELECT l FROM Launch l "
						+ "WHERE l.time BETWEEN :startDate AND :endDate "
						+ "AND l.isHit = 1 "
						+ "ORDER BY l.time DESC")

						.setParameter("startDate", sqlStart)
						.setParameter("endDate", sqlEnd).getResultList();
			
			em.close();
		}
		
		if (launches != null) {
			for (Launch l : launches)
				sum += l.getDamage();
		}
		
		return sum;
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	
	
	// for test
	public static void main(String[] args) {
	
		WarJPA wardb = new WarJPA(null);
		
		wardb.setWarName("ARIEL");
		wardb.warHasBeenStarted();
		
		wardb.enemyLauncherAdded("XXX", true);
		wardb.enemyLaunchMissile("XXX", "shmugudu", "gaza", 1000);
//		wardb.defenseLaunchMissile("ARIEL", null, "ARIEL");
//		wardb.defenseHitInterceptionMissile("ARIEL", null, "ARIEL");
//		wardb.defenseLaunchMissile("XXX", "XXX", missileId, enemyLauncherId);
		
//		System.out.println("\nIs war name exist? " + wardb.isWarNameExist("LOLI"));
		
		wardb.test();

		wardb.warHasBeenFinished();
	}
		
	@SuppressWarnings("unchecked")
	public void test() {

		Calendar startDate = Calendar.getInstance();
		startDate.set(2014, 9, 20);
		Calendar endDate = Calendar.getInstance();
		endDate.set(2014, 9, 30);
		
		Timestamp sqlStart = new Timestamp(startDate.getTime().getTime());
		Timestamp sqlEnd = new Timestamp(endDate.getTime().getTime());

		System.out.println("\nsqlStart: " + sqlStart);
		System.out.println("sqlEnd: " + sqlEnd);
		
		List<Launch> launches = null;
		
		synchronized (emf) {
			EntityManager em = emf.createEntityManager();
			
			launches = em.createQuery(
					"SELECT l FROM Launch l "
						+ "WHERE l.time BETWEEN :startDate AND :endDate "
						+ "ORDER BY l.time DESC")

						.setParameter("startDate", sqlStart, TemporalType.DATE)
						.setParameter("endDate", sqlEnd, TemporalType.DATE).getResultList();
					
//			launches = em.createNamedQuery("Launch.findAll").getResultList();
			
//			launches = em.createQuery("SELECT l FROM Launch l WHERE l.time BETWEEN :startDate AND :endDate")  
//					  .setParameter("startDate", sqlStart, TemporalType.DATE)  
//					  .setParameter("endDate", sqlEnd, TemporalType.DATE)  
//					  .getResultList();
			
			em.close();
		}
		
		for (Launch l : launches)
			System.out.println("\nl launcher id " + l.getLauncherID());
		
		System.out.println("\nsize: " + launches.size());
		
		System.out.println();
		
//		System.out.println("\n\n getNumOfLaunchMissiles: " + getNumOfLaunchMissiles(startDate, endDate));
//		System.out.println("\n\n getNumOfInterceptMissiles: " + getNumOfInterceptMissiles(startDate, endDate));
//		System.out.println("\n\n getNumOfHitTargetMissiles: " + getNumOfHitTargetMissiles(startDate, endDate));
//		System.out.println("\n\n getNumOfLaunchersDestroyed: " + getNumOfLaunchersDestroyed(startDate, endDate));
//		System.out.println("\n\n getTotalDamage: " + getTotalDamage(startDate, endDate));

	}

}
