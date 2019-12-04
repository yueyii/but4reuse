package org.but4reuse.adapters.proteins.methods;

import java.util.ArrayList;
import java.util.List;

import org.but4reuse.adapters.IElement;
import org.but4reuse.adapters.proteins.activator.Activator;
import org.but4reuse.adapters.proteins.preferences.ProteinsAdapterPreferencePage;

public class MethodEGAAC2 implements Methods {
	@Override
	public List<IElement> method(ArrayList<String> StrLetters) {
		ArrayList<String> str = new ArrayList<String>();
		String[] g = { "AILV","RHK","CM", "ST" ,"DE","NQ","G","F","P","W","Y"};
		
		for(int i=0; i<StrLetters.size();i++){
			for(int j=0 ; j < g.length; j++){
				if(g[j].contains(StrLetters.get(i))){
					if(j==0){
						str.add("A");
					}
					if(j==1){
						str.add("R");
					}
					if(j==2){
						str.add("C");
					}
					if(j==3){
						str.add("S");
					}
					if(j==4){
						str.add("D");
					}
					if(j==5){
						str.add("N");
					}
					if(j==6){
						str.add("G");
					}
					if(j==7){
						str.add("F");
					}
					if(j==8){
						str.add("P");
					}
					if(j==9){
						str.add("W");
					}
					if(j==10){
						str.add("Y");
					}
				}
			}
		}
		
		//Use EaacMethod
		MethodEAAC eaac=new MethodEAAC();
		return eaac.method(str);

	}
	
	@Override
	public List<IElement> method(ArrayList<String> StrLetters,String filename) {
		ArrayList<String> str = new ArrayList<String>();
		 String[] g = { "AILV","RHK","CM", "ST" ,"DE","NQ","G","F","P","W","Y"};
		
		for(int i=0; i<StrLetters.size();i++){
			for(int j=0 ; j < g.length; j++){
				if(g[j].contains(StrLetters.get(i))){
					if(j==0){
						str.add("A");
					}
					if(j==1){
						str.add("R");
					}
					if(j==2){
						str.add("C");
					}
					if(j==3){
						str.add("S");
					}
					if(j==4){
						str.add("D");
					}
					if(j==5){
						str.add("N");
					}
					if(j==6){
						str.add("G");
					}
					if(j==7){
						str.add("F");
					}
					if(j==8){
						str.add("P");
					}
					if(j==9){
						str.add("W");
					}
					if(j==10){
						str.add("Y");
					}
				}
			}
		}
		
		//Use EaacMethod
		MethodEAAC eaac=new MethodEAAC();
		return eaac.method(str,filename);

	}
	@Override
	public boolean activatorMethod() {
		if(Activator.getDefault().getPreferenceStore().getBoolean(ProteinsAdapterPreferencePage.METHOD_EGAAC2)){
			return true;
		}else {
			return false;
		}
	}

}
