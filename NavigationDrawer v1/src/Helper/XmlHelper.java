package Helper;

import java.io.File;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.util.Log;


public class XmlHelper {
	public String strPath;
	
	public XmlHelper() 
	{
		
	}
	public void readXML() {
		  try {
			Log.i("Parser xml", "start");
		   /*File xmlFile = new File("http://posifyrt.blob.core.windows.net/mycontainer/myblob.xml");
		   Log.i("Parser xml", "erro1");
		   DocumentBuilderFactory documentFactory = DocumentBuilderFactory
		     .newInstance();
		   Log.i("Parser xml", "erro2");
		   DocumentBuilder documentBuilder = documentFactory
		     .newDocumentBuilder();
		   Log.i("Parser xml", "erro3");
		   Document doc = documentBuilder.parse(xmlFile);
		   Log.i("Parser xml", "erro4");
		   doc.getDocumentElement().normalize();
		   Log.i("Parser xml", "erro5");
		   NodeList nodeList = doc.getElementsByTagName("Event");
		   Log.i("Parser xml", "erro4");
		   System.out.println("Root element :"
		     + doc.getDocumentElement().getNodeName());*/
			URL url = new URL("http://posifyrt.blob.core.windows.net/mycontainer/myblob.xml");

	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        Document doc = db.parse(new InputSource(url.openStream()));
	        doc.getDocumentElement().normalize();
	        NodeList nodeList = doc.getElementsByTagName("Event");
		   for (int temp = 0; temp < nodeList.getLength(); temp++) 
		   {
			    Node node = nodeList.item(temp);
			    System.out.println("\nElement type :" + node.getNodeName());
			    if (node.getNodeType() == Node.ELEMENT_NODE) 
			    {
			     Element student = (Element) node;
			     Log.i("Parser xml", student.getTextContent());
			    }
		   }
/*
		     System.out.println("Student id : "
		       + student.getAttribute("id"));
		     System.out.println("First Name : "
		       + student.getElementsByTagName("firstname").item(0)
		         .getTextContent());
		     System.out.println("Last Name : "
		       + student.getElementsByTagName("lastname").item(0)
		         .getTextContent());
		     System.out.println("Email Id : "
		       + student.getElementsByTagName("email").item(0)
		         .getTextContent());
		     System.out.println("Phone No : "
		       + student.getElementsByTagName("phone").item(0)
		         .getTextContent());*/
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		 }

}
