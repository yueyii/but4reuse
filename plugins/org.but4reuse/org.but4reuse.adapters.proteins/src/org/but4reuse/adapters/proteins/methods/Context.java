package org.but4reuse.adapters.proteins.methods;

import java.util.ArrayList;
import java.util.List;

import org.but4reuse.adapters.IElement;

public class Context {
	 private Methods method;
	 
	   public Context(Methods method){
	      this.method = method;
	   }
	 
	   public List<IElement> executeMethod(ArrayList<String>  strLetter){
	      return method.method(strLetter);
	   }
}
