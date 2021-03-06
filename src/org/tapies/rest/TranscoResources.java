package org.tapies.rest;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.i2cat.tapies.transcoder.entities.Task;
import org.i2cat.tapies.transcoder.tasks.TaskManager;
import org.tapies.db.RestServicesDao;

import org.tapies.rest.entities.Transco;
import org.tapies.util.*;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/")
public class TranscoResources implements Observer {

	private RestServicesDao dao = new RestServicesDao();

	/**
	 * TranscoResources constructor
	 */
	public TranscoResources() {
		// We register this module to receive 
		// all updates from the TranscoManager
		TaskManager.getInstance().addObserver(this);
	}

	/**
	 * getAllTransco method, returns the list of Transcoding activities on
	 * the transcoder url: http://localhost:8080/TapiesWebServices/rest/
	 * 
	 * @return List of Transco entities
	 */

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Transco> getAllTransco() {
		List<Transco> transcos = dao.getAll();
		return transcos;
	}

	/**
	 * getStatusQueue method, returns the status of the queue on the transcoder
	 * url: http://localhost:8080/TapiesWebServices/rest/
	 * 
	 * @return List of Transco entities
	 */

	@GET
	@Path("status")
	@Produces(MediaType.TEXT_HTML)
	public String getStatusQueue() {
		List<Transco> transcos = dao.getNotProcessed();
		return "Queue size: " + transcos.size();
	}

	/**
	 * getTransco method, returns the desired Transcoding activity on the
	 * transcoder url: http://localhost:8080/TapiesWebServices/rest/{id}
	 * 
	 * @return a Transco entity
	 */

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Transco getTransco(@PathParam("id") int transcoId) {
		return dao.get(transcoId);
	}

	/**
	 * addTransco method, adds the given Transco entity
	 * 
	 * @param transco
	 *            entity
	 * @return a String with the given id
	 */

	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String addTransco(Transco transco) {
		int id = dao.getAll().size();
		
		// Check mandatory params
		if (!transco.isValid()) {
			return "Provided transco entity not valid!";
		}
		
		transco.setId(id);
		dao.put(transco);
		
		Task task = TranscoTaskAdapter.fromTransco(transco);
		TaskManager.getInstance().enqueueNewTask(task);
		return "Transco " + transco.getId() + " added to service.";
	}

	@Override
	public void update(Observable arg, Object obj) {
		System.out.println("Notified change in output queue!");
		//int id = TaskManager.getInstance().getFinnishedTask().getId();
		Task t = TaskManager.getInstance().pollFinnishedTask();
		dao.updateFinishedTransco(t);
	}
}
