package com.example.msd_ca2;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.util.Log;

public class IrishParser 
{


	public Document getXML(String xml) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is);

		} catch (Exception e) {
			Log.e("Error: ", e.getMessage());
			return null;
		}

		return doc;
	}

	public String getValue(Element item, String str) 
	{
		NodeList n = item.getElementsByTagName(str);
		return this.getElementValue(n.item(0));
	}

	public final String getElementValue(Node elem) {
		Node child;
		if (elem != null) {
			if (elem.hasChildNodes()) {
				for (child = elem.getFirstChild(); child != null; child = child
						.getNextSibling()) {
					if (child.getNodeType() == Node.TEXT_NODE) {
						return child.getNodeValue();
					}
				}
			}
		}
		return "";
	}

	public IrishSite[] getXmlTagInfo(String xmlString) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try 
		{

			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlString));
			doc = db.parse(is);

		} 
		catch (Exception e) 
		{
			return null;
		}
		NodeList nl = doc.getElementsByTagName("landmark");
		IrishSite[] sites = new IrishSite[nl.getLength()];

		for (int i = 0; i < nl.getLength(); i++) {
			Element e = (Element) nl.item(i);
			sites[i] = new IrishSite(getValue(e, "title"), getValue(e, "coordinates"), getValue(e, "info"));
		}
		return sites;

	}
}
