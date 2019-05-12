package org.but4reuse.adapters.proteins.utils;

import org.but4reuse.adapters.proteins.adapter.ProteinElement;
import org.but4reuse.adapters.proteins.preferences.ProteinsAdapterPreferencePage;
public class ProteinUtils {
	
	public static final String[] PROTEIN_EXTENSIONS = { "*.txt", "*.fasta", "*.train","*.test" };

	/**
	 * Check if a file is an text or in fomal fasta based on the extension
	 * 
	 * @param fileName
	 * @return true if it is an proteinFile
	 */
	public static boolean isProteinFile(String fileName) {
		int dot = fileName.lastIndexOf(".");
		if (dot == -1) {
			return false;
		} else {
			String fileExt = fileName.substring(dot, fileName.length());
			for (String extension : PROTEIN_EXTENSIONS) {
				// remove *
				extension = extension.substring(1);
				if (fileExt.equalsIgnoreCase(extension)) {
					return true;
				}
			}
		}
		return false;
	}
	
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
		if((Math.sqrt(Math.pow(p1-p2,2)))<=automaticThreshold){
			return 1 ;
		}else{
			return 0;
		}
	}
}
