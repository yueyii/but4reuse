package org.but4reuse.adapters.proteins.methods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.but4reuse.adapters.IElement;
import org.but4reuse.adapters.proteins.ProteinElement;
import org.but4reuse.adapters.proteins.activator.Activator;
import org.but4reuse.adapters.proteins.preferences.ProteinsAdapterPreferencePage;

public class MethodAAC implements Methods{
	@Override
	public List<IElement> method(ArrayList<String> Strletters) {
		List<IElement> elements = new ArrayList<IElement>();
		Map<String,Integer> letters = new HashMap<String,Integer>();
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
			}
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
	}