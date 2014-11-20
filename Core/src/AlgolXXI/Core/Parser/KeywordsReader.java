/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Core.Parser;

import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.openide.util.Exceptions;
import org.w3c.dom.*;

/**
 *
 * @author Saso
 */
public class KeywordsReader {

    private static final String path = "AlgolXXI/Core/Parser/DefaultLang.xml";

    public static ArrayList<AlgolXXIKeyword> getKeywords() {
        ArrayList<AlgolXXIKeyword> out = new ArrayList<AlgolXXIKeyword>();
        Document doc = null;

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            doc = docBuilder.parse(KeywordsReader.class.getClassLoader().getResourceAsStream(path));
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }

        doc.getDocumentElement().normalize();
        NodeList listOfKeys = doc.getElementsByTagName("keyword");

        int totalKeys = listOfKeys.getLength();

        for (int i = 0; i < totalKeys; i++) {
            Node KeyNode = listOfKeys.item(i);
            if (KeyNode.getNodeType() == Node.ELEMENT_NODE) {
                Element KeyElement = (Element) KeyNode;

                NodeList id = KeyElement.getElementsByTagName("id");
                NodeList w = KeyElement.getElementsByTagName("word");
                NodeList c = KeyElement.getElementsByTagName("category");

                Element idElement = (Element) id.item(0);
                Element wElement = (Element) w.item(0);
                Element cElement = (Element) c.item(0);

                NodeList txtID = idElement.getChildNodes();
                NodeList txtW = wElement.getChildNodes();
                NodeList txtC = cElement.getChildNodes();

                String finalID = txtID.item(0).getNodeValue().trim();
                String finalW = txtW.item(0).getNodeValue().trim();
                String finalC = txtC.item(0).getNodeValue().trim();

                out.add(new AlgolXXIKeyword(Integer.parseInt(finalID), finalW, finalC));
            }
        }
       
        
        
        return out;
    }
}
