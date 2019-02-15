

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ioman
 */
public class ParseXML{
    
    org.w3c.dom.Document doc;

    public ParseXML(String u) throws MalformedURLException, ProtocolException, IOException, ParserConfigurationException, SAXException{
            URL url = new URL(u);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/xml");
            InputStream xml = connection.getInputStream();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(xml);
    }
    
    public org.w3c.dom.Document getDoc(){
        return this.doc;
    }   
    
    public Item[] parseDoc(){
        //System.out.println("Root element :" + xml.getDocumentElement().getNodeName());
        NodeList n = this.doc.getElementsByTagName("file");
        String path;
        String crc;
        Item fileToCheck;
        Item itemArray[] = new Item[n.getLength()];
        
        for(int x = 0; x < n.getLength(); x++){
            Node nodo = n.item(x);
            if (nodo.getNodeType() == Node.ELEMENT_NODE){
                Element eElement = (Element) nodo;
                path = eElement.getElementsByTagName("path").item(0).getTextContent();
                crc = eElement.getElementsByTagName("crc").item(0).getTextContent();
                itemArray[x] = new Item(path, crc);
            }
        }
        return itemArray;
    }
    
}