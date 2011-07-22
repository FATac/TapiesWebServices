package org.tapies.rest.entities;

public class Profile {
	
	private int type = -1;
	private String dst_path;
	private int status = 0;
	private ExtraOptions extra_ops;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDst_path() {
		return dst_path;
	}
	public void setDst_path(String dst_path) {
		this.dst_path = dst_path;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public ExtraOptions getExtra_ops() {
		return extra_ops;
	}
	public void setExtra_ops(ExtraOptions extra_ops) {
		this.extra_ops = extra_ops;
	}
	public boolean isValid() {
		
		if (dst_path == null)
			return false;
		if (type == -1) 
			return false;
		if (extra_ops != null) {
			if (!extra_ops.isValid())
				return false;
		}
		return true;
	}
	

}
