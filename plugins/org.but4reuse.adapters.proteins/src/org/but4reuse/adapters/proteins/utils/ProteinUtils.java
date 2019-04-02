package org.but4reuse.adapters.proteins.utils;

import org.but4reuse.adapters.proteins.ProteinElement;
import org.but4reuse.adapters.proteins.preferences.ProteinsAdapterPreferencePage;
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
		double automaticThreshold = ProteinsAdapterPreferencePage.getAutomaticEqualThresholdProtein();

		//if the automaticTHreshold is 0, just compare their frequency
		if(automaticThreshold==0.00) {
			if(p1==p2) {
				return 1;	
			}else {
				return 0;
			}
		// else if the automaticTHreshold is bigger than 0, we compare the value of |p1-p2| the automaticTHreshold
		}else if(automaticThreshold!=0.00) {
			if((Math.sqrt(Math.pow(p1-p2,2)))<automaticThreshold){
				return 1 ;
			}else{
				return 0;
			}
		}
		return 0;
	}
}
