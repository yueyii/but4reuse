package org.but4reuse.adapters.proteins.utils;

import java.util.HashMap;
import java.util.Map;

import org.but4reuse.adapters.IElement;

public class Count {
	static int times;
	public static Map<String, Integer> Mapp = new HashMap<String, Integer>();
	public static Map<String,Map<IElement, Integer>> MapFileBlock = new HashMap<String, Map <IElement, Integer>>();
	public static Map<IElement, Integer> MapBlock = new HashMap <IElement, Integer>();
	public boolean addToMapp(String name,int count) {
		if(Mapp.containsKey(name)) return false;
		Mapp.put(name, count);
		
		return true;
	}

	public boolean addToMapBlock(String filename, Map<IElement, Integer> map) {
		MapFileBlock.put(filename, map);
		return true;
	}
	
	public Map<String,Map<IElement, Integer>> getMapBlock() {
		return MapFileBlock;
	}
	
	public int getCountFromName(String name) {
		return Mapp.get(name);
	}
	
	//for protein element 
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		Count.times = times;
	}
	
	
}
