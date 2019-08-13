package org.but4reuse.adapters.proteins.methods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.but4reuse.adapters.IElement;
import org.but4reuse.adapters.proteins.activator.Activator;
import org.but4reuse.adapters.proteins.adapter.ProteinElement;
import org.but4reuse.adapters.proteins.preferences.ProteinsAdapterPreferencePage;
import org.but4reuse.adapters.proteins.utils.Count;

public class MethodEAAC implements Methods{
		
	@Override
	public List<IElement> method(ArrayList<String> StrLetters) {
		List<IElement> elements = new ArrayList<IElement>();
		Map<String,Integer> letters = new HashMap<String,Integer>();
		int count=0;
	
		String letter="";
		Boolean nobreak=true;
		for(int index=0;index < StrLetters.size();index++){
			//check if index is out of size
			for(int j=1 ; j < 5 ;j++){
				if(index + j >=StrLetters.size()){
					nobreak=false;
					break;
				} 
			}
			if(nobreak==true) { 
				letter = StrLetters.get(index)+ 
						StrLetters.get(index+1)+
						StrLetters.get(index+2)+
						StrLetters.get(index+3)+
						StrLetters.get(index+4);
			}
			if(!letters.containsKey(letter)){
				letters.put(letter, 1);
			}else{
				letters.put(letter, letters.get(letter)+1);
			}
		}

		//calculate the number of element in one protein
		for(String letter1 : letters.keySet()){
			ProteinElement proteinElement = new ProteinElement(letter1,letters.get(letter1));
			elements.add(proteinElement);
			int times=letters.get(letter1);
			count+=times;
		}
		Count c = new Count();
		
		c.setTimes(count);
		return elements;
	}

	@Override
	public boolean activatorMethod() {
		if(Activator.getDefault().getPreferenceStore().getBoolean(ProteinsAdapterPreferencePage.METHOD_EAAC)){
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<IElement> method(ArrayList<String> StrLetters, String filename) {
		List<IElement> elements = new ArrayList<IElement>();
		Map<String,Integer> letters = new HashMap<String,Integer>();
		Map<IElement,Integer> Block = new HashMap<IElement,Integer>();
		
		int count=0;
		
		String letter="";
		Boolean nobreak=true;
		for(int index=0;index < StrLetters.size();index++){
			//check if index is out of size
			for(int j=1 ; j < 5 ;j++){
				if(index + j >=StrLetters.size()){
					nobreak=false;
					break;
				} 
			}
			if(nobreak==true) { 
				letter = StrLetters.get(index)+ 
						StrLetters.get(index+1)+
						StrLetters.get(index+2)+
						StrLetters.get(index+3)+
						StrLetters.get(index+4);
			}
			if(!letters.containsKey(letter)){
				letters.put(letter, 1);
			}else{
				letters.put(letter, letters.get(letter)+1);
			}
		}

		//calculate the number of element in one protein
		for(String letter1 : letters.keySet()){
			ProteinElement proteinElement = new ProteinElement(letter1,letters.get(letter1));
			elements.add(proteinElement);
			
			Block.put(proteinElement, letters.get(letter1));
			int times=letters.get(letter1);
			count+=times;
			
		}
		Count c = new Count();
		
		c.addToMapp(filename, count);
		c.setTimes(count);
		c.addToMapBlock(filename,Block);
		return elements;
	}

}
