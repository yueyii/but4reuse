package org.but4reuse.adapters.proteins.utils;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.but4reuse.adapters.ui.xmlgenerator.SaveDataUtils;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

public class AnalyseXML{
	public void analyse(){
		try{		

			File file= SaveDataUtils.saveDataInFile();			
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();

			Document doc = dBuilder.parse(file);
			NodeList var =doc.getElementsByTagName("content");
			
			if (doc.hasChildNodes()) {			
				printNote(var);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	  private static void printNote(NodeList nodeList) {
			
		    for (int count = 0; count < nodeList.getLength(); count++) { 

			Node tempNode = nodeList.item(count);
			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				if (tempNode.hasAttributes()) {
					// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();
					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item( i);
						System.out.println("yes"+node.getNodeValue());
					}
				}

//				if (tempNode.hasChildNodes()) {
//
//					// loop again if has child nodes
//					printNote(tempNode.getChildNodes());
//
//				}
			}
		    }

		  }

}
