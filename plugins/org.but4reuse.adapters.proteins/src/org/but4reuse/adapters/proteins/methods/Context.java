package org.but4reuse.adapters.proteins.methods;

import java.util.ArrayList;
import java.util.List;

import org.but4reuse.adapters.IElement;

public class Context {
	private Methods method;
	public String filename;
	public Context(Methods method){
		this.method = method;
	}

	public List<IElement> executeMethod(ArrayList<String>  strLetter){
		return method.method(strLetter);
	}
	public List<IElement> executeMethod(ArrayList<String>  strLetter,String filename){
		return method.method(strLetter,filename);
	}
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}


}
