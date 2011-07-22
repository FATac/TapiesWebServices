package org.tapies.db;

import org.garret.perst.FieldIndex;
import org.garret.perst.Persistent;
import org.garret.perst.Storage;
import org.tapies.rest.entities.Transco;

public class MyRootClass extends Persistent {
	
	public FieldIndex <Transco> idKeyIndex;
	public FieldIndex <Transco> notProcessedKeyIndex;
	public FieldIndex <Transco> processedKeyIndex;
	
	public MyRootClass (Storage db) {
		super(db);
		idKeyIndex = db.<Transco>createFieldIndex(Transco.class, "id", true);
		notProcessedKeyIndex = db.<Transco>createFieldIndex(Transco.class, "id" , true);
		processedKeyIndex = db.<Transco>createFieldIndex(Transco.class, "id" , true);
	}
	
	public MyRootClass() {
		
	}
	

}
