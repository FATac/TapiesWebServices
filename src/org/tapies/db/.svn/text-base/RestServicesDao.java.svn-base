package org.tapies.db;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.garret.perst.Key;
import org.garret.perst.Storage;
import org.garret.perst.StorageFactory;
import org.i2cat.tapies.transcoder.entities.Task;
import org.tapies.rest.entities.Transco;
import org.tapies.util.TranscoTaskAdapter;

public class RestServicesDao implements ServletContextListener {

	private static Storage db;
	private static long pagePoolSize = 524288;
	private static MyRootClass root;
	
	public RestServicesDao() {
		System.out.println("Initializing RestServicesDao");
		if (db == null) {
			// First RestServicesDao instance create db
			db = StorageFactory.getInstance().createStorage();
			System.out.println("Created db instance");
		}
		if (!db.isOpened()) {
			// open the database
			db.open("test.dbs", pagePoolSize );
			System.out.println("Opened db file");
		}

		root = (MyRootClass)db.getRoot();
		
		if (root == null ) {
			root = new MyRootClass(db);
			db.setRoot(root);
		}

	}
	
	public void put(Transco transco) {
		System.out.println("Putting transco " + transco.getId());
		root.idKeyIndex.put(transco);
		root.notProcessedKeyIndex.put(transco);
	}
	
	public void updateFinishedTransco(Task task) {
		
		// Get the transco reference
		Transco t = root.idKeyIndex.get(new Key(task.getId()));
		// Before updating the entity remove from indexes
		root.idKeyIndex.remove(t);
		root.notProcessedKeyIndex.remove(t);
		// Update de entity
		TranscoTaskAdapter.updateTranscoFromTask(t, task);
		
		
		// Store the object
		db.store(t);
		// Set on again on indexes
		root.idKeyIndex.put(t);
		root.processedKeyIndex.put(t);
	}
	
	
	public Transco get(int id) {
		Transco t = root.idKeyIndex.get(new Key(id));
		System.out.println("Getting transco " + t.getId());
		return t;
	}
	
	public List<Transco> getAll() {
		List <Transco> tList = new ArrayList<Transco>();
		for (Transco t : root.idKeyIndex)
		{
			tList.add(t);
		}
		return tList;
	}
	
	public List<Transco> getNotProcessed() {
		List <Transco> tList = new ArrayList<Transco>();
		for (Transco t : root.notProcessedKeyIndex)
		{
			tList.add(t);
		}
		return tList;
	}
	
	public void remove(int id) {
		root.idKeyIndex.remove(id);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("contextDestroyed Event!");
		db.close();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("contextInitialized Event!");
	}
}
