package org.but4reuse.fca.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import fr.labri.galatea.Attribute;
import fr.labri.galatea.Context;
import fr.labri.galatea.Entity;

public class ContextProtein extends Context {
	
	Map<Attribute,String> attributeProteinRelation;
	protected Map<Entity,Map<Attribute,String>> relationPercentage;
	
	
	public ContextProtein() {
		super();
		relationPercentage = new  HashMap<>();
		attributeProteinRelation=new  HashMap<>();
		
	}
	
	public void addPair(Entity e,Attribute a,String value) {
		if ( !relation.containsKey(e) )
			relation.put(e,new HashSet<Attribute>());
		
		if ( !reverseRelation.containsKey(a) )
			reverseRelation.put(a,new HashSet<Entity>());
		
		//for stocking the value of percentage in a map
		if ( !relationPercentage.containsKey(e) )
			relationPercentage.put(e,new HashMap<Attribute,String>());
		
		if(!relationPercentage.get(e).containsKey(a)) {
			relationPercentage.get(e).put(a,"vide");
		}
		relation.get(e).add(a);
		reverseRelation.get(a).add(e);
		relationPercentage.get(e).put(a,value);
		attributeProteinRelation.put(a,value);
	}

	public Map<Entity, Map<Attribute, String>> getrelationPercentage() {
		return relationPercentage;
	}
	
	public String getproteinPercentageFromAttribute(Entity entity,Attribute attribute) {
		return relationPercentage.get(entity).get(attribute);
	}
	
	public void setrelationPercentage(Map<Entity, Map<Attribute, String>> relationPercentage) {
		this.relationPercentage = relationPercentage;
	}

	public Map<Attribute, String> getAttributeProteinRelation() {
		return attributeProteinRelation;
	}
	
	public String getproteinFromAttribute(Attribute attribute) {
		return attributeProteinRelation.get(attribute);
	}

	public void setAttributeProteinRelation(Map<Attribute, String> attributeProteinRelation) {
		this.attributeProteinRelation = attributeProteinRelation;
	}

}
