package kr.peopleware.util.web;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLCaller {
	public Document requestGET(String uri){
//		String uri = "http://openapi.naver.com/search?key=c9413c87172e87dbdc7d05a4346e8eca&query=nexearch&target=rank";
		DocumentBuilder builder;
		DocumentBuilderFactory factory;
		Document document = null;
		
		factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(uri);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		document.getDocumentElement().normalize();
//		NodeList nodeList = document.getElementsByTagName("K");
//		for(int i=0;i<nodeList.getLength();i++){
//			System.out.println(nodeList.item(i).getChildNodes().item(0).getNodeValue());
//		}
		return document;
	}
}
