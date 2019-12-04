package org.but4reuse.adapters.proteins.utils;
import java.util.HashMap;

public class WordCloud {
	static int elems[][];
	public static HashMap<Character, Integer> keyvalue;


	//return Amino acid according to its index
	public static Character getKey(HashMap<Character, Integer> map, Integer value) {
		for (Character key : map.keySet()) {
			if (value.equals(map.get(key))) {
				return key;
			}
		}
		return null;
	}
	
	/* 5 group's position index
	 * G:0
	 * F:1
	 * K:2
	 * D:3
	 * S:4
	 * */
	public static void initGroup1() {
		elems = new int[5][5];
		for(int i =0;i< 5; i++) {
			for(int j =0;j< 5;j++) {
				elems[i][j]=-1;
			}		
		}
		keyvalue = new HashMap<Character, Integer>();
		keyvalue.put('G', 0);
		keyvalue.put('F', 1);
		keyvalue.put('K', 2);
		keyvalue.put('D', 3);
		keyvalue.put('S', 4);
	}
	
	
	/* 11 group's position index
	 * A:0
	 * R:1
	 * C:2
	 * S:3
	 * D:4
	 * N:5
	 * G:6
	 * F:7
	 * P:8
	 * W:9
	 * Y:10
	 * */
	public static void initGroup2() {
		elems = new int[5][11];
		for(int i =0;i< 5; i++) {
			for(int j =0;j< 5;j++) {
				elems[i][j]=-1;
			}		
		}
		keyvalue = new HashMap<Character, Integer>();
		keyvalue.put('A', 0);
		keyvalue.put('R', 1);
		keyvalue.put('C', 2);
		keyvalue.put('S', 3);
		keyvalue.put('D', 4);
		keyvalue.put('N', 5);
		keyvalue.put('G', 6);
		keyvalue.put('F', 7);
		keyvalue.put('P', 8);
		keyvalue.put('W', 9);
		keyvalue.put('Y', 10);
	}
	
	//for other method expect method EGAAC
	public static void initOthers() { 
		//max size of an element is 5
		//max 20 Amino acid
		elems = new int[5][20];
		for(int i =0;i< 5; i++) {
			for(int j =0;j< 20;j++) {
				elems[i][j]=-1;
			}		
		}
		keyvalue = new HashMap<Character, Integer>();
		keyvalue.put('A', 0);
		keyvalue.put('C', 1);
		keyvalue.put('D', 2);
		keyvalue.put('E', 3);
		keyvalue.put('F', 4);
		keyvalue.put('G', 5);
		keyvalue.put('H', 6);
		keyvalue.put('I', 7);
		keyvalue.put('K', 8);
		keyvalue.put('L', 9);
		keyvalue.put('M', 10);
		keyvalue.put('N', 11);
		keyvalue.put('P', 12);
		keyvalue.put('Q', 13);
		keyvalue.put('R', 14);
		keyvalue.put('S', 15);
		keyvalue.put('T', 16);
		keyvalue.put('V', 17);
		keyvalue.put('W', 18);
		keyvalue.put('Y', 19);

	}

	//check the index of the letter in table
	public static int checkIndex(String letters, int position) {
		return keyvalue.get(letters.toCharArray()[position]);
	}

	//write the frequency in table 
	public static int[][] frequencyTable(String letter, int frequency) {	
		for(int position=0; position < letter.length(); position++) {	
			int index = checkIndex(letter,position);
			elems[position][index]=frequency;
		}

		return elems;
	}
}
