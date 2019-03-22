package org.but4reuse.adapters.proteins.methods;

import java.util.ArrayList;
import java.util.List;

import org.but4reuse.adapters.IElement;
import org.but4reuse.adapters.proteins.activator.Activator;
import org.but4reuse.adapters.proteins.preferences.ProteinsAdapterPreferencePage;

public class MethodEGAAC implements Methods {
	@Override
	public List<IElement> method(ArrayList<String> StrLetters) {
		ArrayList<String> str = new ArrayList<String>();
		String[] g = { "GAVLMI","FYW","KRN", "DE" ,"STCPNQ"};

		for(int i=0; i<StrLetters.size();i++){
			for(int j=0 ; j < g.length; j++){
				if(g[j].contains(StrLetters.get(i))){
					if(j==0){
						str.add("G");
					}
					if(j==1){
						str.add("F");
					}
					if(j==2){
						str.add("K");
					}
					if(j==3){
						str.add("D");
					}
					if(j==4){
						str.add("S");
					}
				}
			}
		}
		
		//Use EaacMethod
		MethodEAAC eaac=new MethodEAAC();
		return eaac.method(str);

	}

	@Override
	public boolean activatorMethod() {
		if(Activator.getDefault().getPreferenceStore().getBoolean(ProteinsAdapterPreferencePage.METHOD_EGAAC)){
			return true;
		}else {
			return false;
		}
	}

}
