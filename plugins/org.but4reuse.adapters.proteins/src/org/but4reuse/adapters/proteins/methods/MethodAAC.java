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

public class MethodAAC implements Methods{
	@Override
	public List<IElement> method(ArrayList<String> Strletters) {
		List<IElement> elements = new ArrayList<IElement>();
		Map<String,Integer> letters = new HashMap<String,Integer>();
		int count = 0;
		
		for (String letter : Strletters) {
				if(!letters.containsKey(letter)){
					letters.put(letter, 1);
				}else{
					letters.put(letter, letters.get(letter)+1);
				
			}
		}
			for(String letter : letters.keySet()){
				ProteinElement proteinElement = new ProteinElement(letter,letters.get(letter));
				elements.add(proteinElement);
				
				//calculate the number of element in one protein
				int times=letters.get(letter);
				if(times==1) {
					count++;
				}else {
					count+=times;		
				}
			}
			
			Count c = new Count();
			c.setTimes(count);
			return elements;
		}

		@Override
		public boolean activatorMethod() {
			if(Activator.getDefault().getPreferenceStore().getBoolean(ProteinsAdapterPreferencePage.METHOD_AAC)){
				return true;
			}else {
				return false;
			}
		}

		@Override
		public List<IElement> method(ArrayList<String> strLetter, String filename) {
			// TODO Auto-generated method stub
			return null;
		}
	}