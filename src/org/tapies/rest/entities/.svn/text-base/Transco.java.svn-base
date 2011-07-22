package org.tapies.rest.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.garret.perst.Persistent;


@XmlRootElement
public class Transco extends Persistent{
	private int id;
	private boolean priority = false;
	private String src_path;
	private List <Profile> profiles;
	
	public Transco() {}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isPriority() {
		return priority;
	}
	public void setPriority(boolean priority) {
		this.priority = priority;
	}
	public String getSrc_path() {
		return src_path;
	}
	public void setSrc_path(String src_path) {
		this.src_path = src_path;
	}
	public List<Profile> getProfiles() {
		return profiles;
	}
	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}
	public boolean isValid() {
	
		if (this.src_path == null)
			return false;
		
		for (Profile p : this.profiles) {
			if (!p.isValid()) {
				return false;
			}
		}
		
		return true;
	}
	
}
