package org.but4reuse.adapters.proteins.utils;
import java.util.ArrayList;

import org.but4reuse.adapters.preferences.PreferencesHelper;
import org.but4reuse.adapters.proteins.ProteinElement;
public class ProteinUtils {
	/**
	 * Protein similarity 
	 * 
	 * @param proteineElement1
	 * @param proteineElement2
	 * @return a range from 0 to 1: 0 for proteins's frequency are diffrents, 1 for completely equal
	 */
	public static double getProteinSimilarity(ProteinElement proteinElement,ProteinElement proteinElement2){
		Count c = new Count();
		int times = c.getTimes();
		double p1 = (double)(proteinElement.frequency)/times;
		double p2 = (double)(proteinElement2.frequency)/times;
		double automaticThreshold = PreferencesHelper.getAutomaticEqualThreshold();
		
		//if p1 bigger than p2, and if p1%percentage<=p2<=p1%(2-percentage), they are equal
		if(p1>p2){
			if((p1*automaticThreshold<=p2)&&(p2<=p1*(2-automaticThreshold)))
			{
				return 1 ;
			}
			else{
				return 0;
			}
		}
		//if p2 bigger than p1, and if p2%percentage<=p1<=p2%(2-percentage), they are equal
		else{
			if((p2*automaticThreshold<=p1)&&(p1<=p2*(2-automaticThreshold)))
			{
				return 1 ;
			}
			else{
				return 0;
			}
		}
	}
	
	public static String getWordsCloud(ProteinElement proteinElement) {
		String letters = proteinElement.letter;
		
		return null;
	}
	
}
