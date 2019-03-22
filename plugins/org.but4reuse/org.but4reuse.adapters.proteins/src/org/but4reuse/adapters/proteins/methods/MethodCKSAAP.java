package org.but4reuse.adapters.proteins.methods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.but4reuse.adapters.IElement;
import org.but4reuse.adapters.proteins.ProteinElement;
import org.but4reuse.adapters.proteins.activator.Activator;
import org.but4reuse.adapters.proteins.preferences.ProteinsAdapterPreferencePage;

public class MethodCKSAAP implements Methods{
	@Override
	public List<IElement> method(ArrayList<String> Strletters) {
		List<IElement> elements = new ArrayList<IElement>();
		Map<String,Integer> letters = new HashMap<String,Integer>();
		String first=null;
		String second = null;
		for(int index=0;index<Strletters.size();index++){
			first=Strletters.get(index);
			if(index+1>=Strletters.size()){
				break;
			}else { second=Strletters.get(index+1);
			}
			//Every protein element is composed by 2 letters
			String letter = first+second;
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
		if(Activator.getDefault().getPreferenceStore().getBoolean(ProteinsAdapterPreferencePage.METHOD_CKSAAP)){
			return true;
		}else {
			return false;
		}
	}

}
