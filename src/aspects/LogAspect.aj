package aspects;

import utils.WarLogger;
import model.launchers.*;
import model.missiles.*;


public aspect LogAspect {

	private WarLogger warLogger;

	
	// model.War
	pointcut warConstructor() : execution (model.War.new());
	
	after() : warConstructor() {
		warLogger = new WarLogger();
		WarLogger.addWarLoggerHandler("War");
	}

	pointcut closeHandlers() : execution (void model.War.fireWarHasBeenFinished());

	after() : closeHandlers() {

		WarLogger.closeAllHandlers();		
		WarLogger.closeWarHandler();
		warLogger.warHasBeenFinished();
	}
	
	pointcut warStarted() : execution (void model.War.fireWarHasBeenStarted());

	after() : warStarted() {

		warLogger.warHasBeenStarted();
	}
	
	pointcut warMissileNotExist(String defenseLauncherId, String enemyId) : execution (void model.War.fireMissileNotExistEvent(*,*)) && args(defenseLauncherId, enemyId);

	after(String defenseLauncherId, String enemyId) : warMissileNotExist(defenseLauncherId, enemyId) {

		warLogger.missileNotExist(defenseLauncherId, enemyId);
	}
	
	
	// model.launchers.EnemyLauncher
	pointcut enemyLauncherConstructor(String id) : execution (model.launchers.EnemyLauncher.new(*,*,*)) && args(id,*,*);
	
	after(String id) : enemyLauncherConstructor(id) {
		WarLogger.addLoggerHandler("Launcher", id);
	}

	pointcut enemyLauncherRun() : execution (void model.launchers.EnemyLauncher.run());
	
	after() : enemyLauncherRun() {
		String id = ((EnemyLauncher)thisJoinPoint.getTarget()).getLauncherId();
		WarLogger.closeMyHandler(id);
	}
	
	pointcut enemyLauncherVisible(boolean visible) : execution (void model.launchers.EnemyLauncher.fireEnemyLauncherIsVisibleEvent(*)) && args(visible);
	
	after(boolean visible) : enemyLauncherVisible(visible) {
		String id = ((EnemyLauncher)thisJoinPoint.getTarget()).getLauncherId();
		warLogger.enemyLauncherIsVisible(id, visible);
	}
	
	pointcut enemyLauncherLaunch(String missileId) : execution (void model.launchers.EnemyLauncher.fireLaunchMissileEvent(*)) && args(missileId);
	
	after(String missileId) : enemyLauncherLaunch(missileId) {
		EnemyLauncher l = (EnemyLauncher)thisJoinPoint.getTarget();
		warLogger.enemyLaunchMissile(l.getLauncherId(), missileId, l.getDestination(), l.getDamage());
	}
	
	
	// model.launchers.IronDome
	pointcut ironDomeConstructor(String id) : execution (model.launchers.IronDome.new(*,*)) && args(id,*);
	
	after(String id) : ironDomeConstructor(id) {
		WarLogger.addLoggerHandler("IronDome", id);
	}
	
	pointcut ironDomeRun() : execution (void model.launchers.IronDome.run());
	
	after() : ironDomeRun() {
		String id = ((IronDome)thisJoinPoint.getTarget()).getIronDomeId();
		WarLogger.closeMyHandler(id);
	}
	
	pointcut ironDomeMissileNotExist(String missileId) : execution (void model.launchers.IronDome.fireMissileNotExist(*)) && args(missileId);

	after(String missileId) : ironDomeMissileNotExist(missileId) {
		String id = ((IronDome)thisJoinPoint.getTarget()).getIronDomeId();
		warLogger.missileNotExist(id, missileId);
	}
	
	pointcut ironDomeIntercept(String missileId) : execution (void model.launchers.IronDome.fireLaunchMissileEvent(*)) && args(missileId);
	
	after(String missileId) : ironDomeIntercept(missileId) {
		IronDome id = (IronDome)thisJoinPoint.getTarget();		
		warLogger.defenseLaunchMissile(id.getIronDomeId(), missileId, id.getToDestroy().getMissileId());
	}
	
	
	// model.launchers.LauncherDestructor
	pointcut launcherDestructorConstructor(String type, String id) : execution (model.launchers.LauncherDestructor.new(*,*,*)) && args(type,id,*);
	
	after(String type, String id) : launcherDestructorConstructor(type, id) {
		WarLogger.addLoggerHandler(type, id);
	}
	
	pointcut launcherDestructorRun() : execution (void model.launchers.LauncherDestructor.run());
	
	after() : launcherDestructorRun() {
		String id = ((LauncherDestructor)thisJoinPoint.getTarget()).getDestructorId();
		WarLogger.closeMyHandler(id);
	}
	
	pointcut destructorDestroy(String missileId) : execution (void model.launchers.LauncherDestructor.fireLaunchMissileEvent(*)) && args(missileId);
	
	after(String missileId) : destructorDestroy(missileId) {
		LauncherDestructor ld = (LauncherDestructor)thisJoinPoint.getTarget();
		warLogger.defenseLaunchMissile(ld.getDestructorId(), ld.getDestructorType(), missileId, ld.getToDestroy().getLauncherId());
	}

	pointcut launcherHidden(String launcherId) : execution (void model.launchers.LauncherDestructor.fireLauncherIsHiddenEvent(*)) && args(launcherId);
	
	after(String launcherId) : launcherHidden(launcherId) {
		LauncherDestructor ld = (LauncherDestructor)thisJoinPoint.getTarget();
		warLogger.defenseMissInterceptionHiddenLauncher(ld.getDestructorId(), ld.getDestructorType(), launcherId);
	}
	
	pointcut launcherNotExist(String launcherId) : execution (void model.launchers.LauncherDestructor.fireLauncherNotExist(*)) && args(launcherId);
	
	after(String launcherId) : launcherNotExist(launcherId) {
		String id = ((LauncherDestructor)thisJoinPoint.getTarget()).getDestructorId();
		warLogger.enemyLauncherNotExist(id, launcherId);
	}
	
	
	// model.missiles.DefenseDestructorMissile
	pointcut destructorHit() : execution (void model.missiles.DefenseDestructorMissile.fireHitEvent());
	
	after() : destructorHit() {
		DefenseDestructorMissile dm = (DefenseDestructorMissile)thisJoinPoint.getTarget();
		warLogger.defenseHitInterceptionLauncher(dm.getWhoLaunchedMeId(), dm.getWhoLaunchedMeType(), dm.getMissileId(), dm.getLauncherToDestroy().getLauncherId());
	}
	
	pointcut destructorMiss() : execution (void model.missiles.DefenseDestructorMissile.fireMissEvent());
	
	after() : destructorMiss() {
		DefenseDestructorMissile dm = (DefenseDestructorMissile)thisJoinPoint.getTarget();
		warLogger.defenseMissInterceptionLauncher(dm.getWhoLaunchedMeId(), dm.getWhoLaunchedMeType(), dm.getMissileId(), dm.getLauncherToDestroy().getLauncherId());
	}
	
	
	// model.missiles.DefenseMissile
	pointcut ironDomeHit() : execution (void model.missiles.DefenseMissile.fireHitEvent());
	
	after() : ironDomeHit() {
		DefenseMissile dm = (DefenseMissile)thisJoinPoint.getTarget();
		warLogger.defenseHitInterceptionMissile(dm.getWhoLaunchedMeId(), dm.getMissileId(), dm.getMissileToDestroy().getMissileId());
	}
	
	pointcut ironDomeMiss() : execution (void model.missiles.DefenseMissile.fireMissEvent());
	
	after() : ironDomeMiss() {
		DefenseMissile dm = (DefenseMissile)thisJoinPoint.getTarget();
		warLogger.defenseMissInterceptionMissile(dm.getWhoLaunchedMeId(), dm.getMissileId(), dm.getMissileToDestroy().getMissileId(), dm.getMissileToDestroy().getDamage());
	}
	
	
	// model.missiles.EnemyMissile
	pointcut enemyMissileHit() : execution (void model.missiles.EnemyMissile.fireHitEvent());
	
	after() : enemyMissileHit() {
		EnemyMissile em = (EnemyMissile)thisJoinPoint.getTarget();
		warLogger.enemyHitDestination(em.getWhoLaunchedMeId(), em.getMissileId(), em.getDestination(), em.getDamage(), em.getLaunchTime());
	}
	
	pointcut enemyMissileMiss() : execution (void model.missiles.EnemyMissile.fireMissEvent());
	
	after() : enemyMissileMiss() {
		EnemyMissile em = (EnemyMissile)thisJoinPoint.getTarget();
		warLogger.enemyMissDestination(em.getWhoLaunchedMeId(), em.getMissileId(), em.getDestination(), em.getLaunchTime());
	}
	
}

