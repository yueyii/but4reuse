package org.but4reuse.adapters.proteins.utils;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.but4reuse.adapters.ui.xmlgenerator.SaveDataUtils;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class AnalyseXML{
	public void analyse(){
		try{
			File file= SaveDataUtils.saveDataInFile();	
			System.out.println("yessssssss");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = (Document) dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			
			NodeList nList = doc.getElementsByTagName("levelofreuse");
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);					
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String e =eElement.getElementsByTagName("element").item(0).getTextContent();
					System.out.println("yessssssss"+e);
								
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
