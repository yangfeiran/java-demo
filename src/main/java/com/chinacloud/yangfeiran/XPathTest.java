package com.chinacloud.yangfeiran;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XPathTest {
	public static void read() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document doc = builder.parse("schools.xml");
			// root <university>
			Element root = doc.getDocumentElement();
			if (root == null) return;
			System.err.println(root.getAttribute("name"));
			// all college node
			NodeList collegeNodes = root.getChildNodes();
			if (collegeNodes == null) return;
			for(int i = 0; i < collegeNodes.getLength(); i++) {
				Node college = collegeNodes.item(i);
				if (college != null && college.getNodeType() == Node.ELEMENT_NODE) {
					System.err.println("\t" + college.getAttributes().getNamedItem("name").getNodeValue());
					// all class node
					NodeList classNodes = college.getChildNodes();
					if (classNodes == null) continue;
					for (int j = 0; j < classNodes.getLength(); j++) {
						Node clazz = classNodes.item(j);
						if (clazz != null && clazz.getNodeType() == Node.ELEMENT_NODE) {
							System.err.println("\t\t" + clazz.getAttributes().getNamedItem("name").getNodeValue());
							// all student node
							NodeList studentNodes = clazz.getChildNodes();
							if (studentNodes == null) continue;
							for (int k = 0; k < studentNodes.getLength(); k++) {
								Node student = studentNodes.item(k);
								if (student != null && student.getNodeType() == Node.ELEMENT_NODE) {
									System.err.print("\t\t\t" + student.getAttributes().getNamedItem("name").getNodeValue());
									System.err.print(" " + student.getAttributes().getNamedItem("sex").getNodeValue());
									System.err.println(" " + student.getAttributes().getNamedItem("age").getNodeValue());
								}
							}
						}
					}
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static void main(String[] args)  throws ParserConfigurationException, SAXException,
	          											IOException, XPathExpressionException {
read();
	    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	    domFactory.setNamespaceAware(true);
	    DocumentBuilder builder = domFactory.newDocumentBuilder();
	    System.out.println("Loading books.xml...");
	    Document doc = builder.parse("books.xml");

	    XPathFactory factory = XPathFactory.newInstance();
	    XPath xpath = factory.newXPath();
	    System.out.println("Reading list of titles...");
	    System.out.println("(using xpath = /bookstore/book[last()]/title/text()");
	    XPathExpression expr = xpath.compile("/bookstore/book[last()]/title/text()");

	    Object result = expr.evaluate(doc, XPathConstants.NODESET);
	    NodeList nodes = (NodeList) result;
	    for (int i = 0; i < nodes.getLength(); i++) {
	        System.out.println(nodes.item(i).getNodeValue());
	    }

	  }
}
