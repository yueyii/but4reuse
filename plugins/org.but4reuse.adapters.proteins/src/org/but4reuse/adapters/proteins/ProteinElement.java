package org.but4reuse.adapters.proteins;

import java.util.ArrayList;
import org.but4reuse.adapters.IElement;
import org.but4reuse.adapters.proteins.methods.MethodAAC;
import org.but4reuse.adapters.proteins.methods.MethodCKSAAP;
import org.but4reuse.adapters.proteins.methods.MethodEAAC;
import org.but4reuse.adapters.proteins.methods.MethodEGAAC;
import org.but4reuse.adapters.impl.AbstractElement;
import org.but4reuse.adapters.proteins.ProteinElement;
import org.but4reuse.adapters.proteins.utils.ProteinUtils;
import org.but4reuse.adapters.proteins.utils.WordCloud;

public class ProteinElement extends AbstractElement {

	public String letter;
	public int frequency;


	//construtor
	public ProteinElement(String letter,int frequency) {
		this.letter = letter;
		this.frequency=frequency;
	}

	@Override
	public String getText() {
		String text = "ProteinElement :"+ letter + ""+ 
				"Frequency :"+frequency;
		return text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((letter == null) ? 0 : letter.hashCode());
		return result;
	}

	@Override
	public double similarity(IElement anotherElement) {
		if (anotherElement instanceof ProteinElement) {
			ProteinElement protein = (ProteinElement) anotherElement;
			MethodAAC methodAAC = new MethodAAC();
			MethodCKSAAP methodCKSAAP = new MethodCKSAAP();
			MethodEAAC methodEAAC = new MethodEAAC();
			MethodEGAAC methodEGAAC = new MethodEGAAC();
			//if we choose methodAAC/methodCKSAAP/methodEAAC
			if(methodAAC.activatorMethod()||methodCKSAAP.activatorMethod()
					||methodEAAC.activatorMethod()||methodEGAAC.activatorMethod()){
				//compare the frequency of two elements in the diffrents proteins
				if(this.letter.equals(protein.letter)){
					return ProteinUtils.getProteinSimilarity(this,protein);
				}
			}
		}
		return 0;
	}

	@Override
	public ArrayList<String> getWords() { 
		ArrayList<String> words= new ArrayList<String>();
		int[][] elems= new int[5][5]; 
		elems = WordCloud.frequencyTable(letter,frequency);
		for(int position =0;position< 5; position++) {
			for(int index =0;index< 5;index++) {
				//check if the element exist 
				if(elems[position][index]!=-1) {
					int fre = elems[position][index];
					for(int k=0 ; k < fre ;k++) {
						String positionStr=position+"";
						words.add(WordCloud.getKey(WordCloud.keyvalue,index)+positionStr);
					}
				}
			}		
		}
//		String name = letter.toString();
//		String fre="" + frequency;
//		name=name +":"+fre+" ";
//		if (!name.equals("Erreur"))
//			words.add(name);
		return words;
	}
}
