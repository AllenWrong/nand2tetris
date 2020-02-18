package compiler;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlUtils {

	/** This class is not allowed to instance.*/
	private XmlUtils() {}
	
	/**
	 * Get document object.
	 * @return
	 */
	public static Document getDocument() {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = null;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		Document document =documentBuilder.newDocument();
		return document;
	}
	
	/**
	 * Save the document object to file.
	 * @param xmlFile target file
	 * @param root root element
	 */
	public static void docSave(File xmlFile, Element root) {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT,"yes");
			DOMSource domSource = new DOMSource(root);
			StreamResult streamResult = new StreamResult(new FileOutputStream(xmlFile));
			transformer.transform(domSource, streamResult);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
