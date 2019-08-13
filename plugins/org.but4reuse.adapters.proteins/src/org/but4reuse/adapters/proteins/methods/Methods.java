package org.but4reuse.adapters.proteins.methods;
/* Different methods of the comparison proteins can be used in different situations
 * 
 */

import java.util.ArrayList;
import java.util.List;

import org.but4reuse.adapters.IElement;

public interface Methods {
	public List<IElement> method(ArrayList<String>  strLetter);
	public List<IElement> method(ArrayList<String>  strLetter,String filename);
	public boolean activatorMethod();
}
