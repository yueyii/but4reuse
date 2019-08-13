package org.but4reuse.adapters.proteins.adapter;

import java.util.ArrayList;
import org.but4reuse.adapters.IElement;
import org.but4reuse.adapters.proteins.adapter.ProteinElement;
import org.but4reuse.adapters.proteins.methods.MethodAAC;
import org.but4reuse.adapters.proteins.methods.MethodCKSAAGP;
import org.but4reuse.adapters.proteins.methods.MethodCKSAAP;
import org.but4reuse.adapters.proteins.methods.MethodEAAC;
import org.but4reuse.adapters.proteins.methods.MethodEGAAC;
import org.but4reuse.adapters.impl.AbstractElement;
import org.but4reuse.adapters.proteins.utils.ProteinUtils;
import org.but4reuse.adapters.proteins.utils.WordCloud;

public class ProteinElement extends AbstractElement {


	public String letter;
	public int frequency;
	private MethodAAC methodAAC = new MethodAAC();
	private MethodCKSAAP methodCKSAAP = new MethodCKSAAP();
	private MethodCKSAAGP methodCKSAAGP = new MethodCKSAAGP();
	private MethodEAAC methodEAAC = new MethodEAAC();
	private MethodEGAAC methodEGAAC = new MethodEGAAC();

	//construtor
	public ProteinElement(String letter,int frequency) {
		this.letter = letter;
		this.frequency=frequency;
	}

	@Override
	public String getText() {
		String text = "ProteinElement:"+ letter + " "+ 
				"Frequency:"+frequency;
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
			//if we choose methodAAC/methodCKSAAP/methodEAAC
			if(methodAAC.activatorMethod()||methodCKSAAP.activatorMethod()
					||methodCKSAAGP.activatorMethod()
					||methodEAAC.activatorMethod()||methodEGAAC.activatorMethod()){
				//compare the frequency of two elements in the different proteins
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
		int[][] elems;
		int position_size;
		int index_size;
		//if we chose EGAAC
		if(methodEGAAC.activatorMethod()||methodCKSAAGP.activatorMethod()){
			elems= new int[5][5]; 
			WordCloud.initGroup();
			position_size=5;
			index_size=5;
			}
		//else we choose other method
		else {
			elems= new int[5][20];
			WordCloud.initOthers();
			position_size=5;
			index_size=20;	
		}
			//take the table witch stock the frequency
			elems = WordCloud.frequencyTable(letter,frequency);
			for(int position =0;position< position_size; position++) {
				for(int index =0;index< index_size;index++) {
					//check if the element has existed
					if(elems[position][index]!=-1) {
						int fre = elems[position][index];
						
						for(int k=0 ; k < fre ;k++) {
							String positionStr=position+"";	
							//write the amino acid and its position in the words to display
							words.add(WordCloud.getKey(WordCloud.keyvalue,index)+positionStr);
						}
					}
				}		
			}
//			String name = letter.toString();
//			String fre="" + frequency;
//			name=name +":"+fre+" ";
//			if (!name.equals("Erreur"))
//				words.add(name);
		return words;
	}
}
