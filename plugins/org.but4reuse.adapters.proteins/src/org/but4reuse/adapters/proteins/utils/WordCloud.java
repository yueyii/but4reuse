package org.but4reuse.adapters.proteins.utils;
import java.util.HashMap;

public class WordCloud {
	//static ArrayList <ArrayList<Integer>> elems= new ArrayList<>();
	static int elems[][];
	public static HashMap<Character, Integer> keyvalue;

	/* 5 group's position index
	 * G:0
	 * F:1
	 * K:2
	 * D:3
	 * S:4
	 * */
	public static <Charcter, Integer> Character getKey(HashMap<Character, Integer> map, Integer value) {
		for (Character key : map.keySet()) {
			if (value.equals(map.get(key))) {
				return key;
			}
		}
		return null;
	}

	public static void init() {
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

	public static int checkIndex(String letters, int position) {
		return keyvalue.get(letters.toCharArray()[position]);
	}

	public static int[][] frequencyTable(String letter, int frequency) {	
		WordCloud.init();	
		for(int position=0; position < 5; position++) {	
			int index = checkIndex(letter,position);
			elems[position][index]=frequency;
		}

		return elems;
	}
}
