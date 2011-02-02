/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor.CodeEditor;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.String;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.openide.util.Exceptions;
import org.w3c.dom.*;

/**
 *
 * @author Samuel Oliveira, André Teixeira e João Gameiro
 */
public class PortugolStyleReader {
  public static String path = "AlgolXXI/Editor/CodeEditor/StyleCOST.xml";
    /**
     * Selector para os PortugolStyle lidos dos ficheiros XML.
     * @return  - PortugolStyle PortugolStyle
     */
    public static PortugolStyle getStyle() {

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        Document doc = null;

        PortugolStyle style = new PortugolStyle();


       try {
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            doc = docBuilder.parse(PortugolStyleReader.class.getClassLoader().getResourceAsStream(path));
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }

        NodeList listOfKeys = doc.getElementsByTagName("code");
        Node KeyNode = listOfKeys.item(0);
        if (KeyNode.getNodeType() == Node.ELEMENT_NODE) {
            Element e = (Element) KeyNode;

            NodeList family = e.getElementsByTagName("family");
            NodeList size = e.getElementsByTagName("size");

            Element fe = (Element) family.item(0);
            Element se = (Element) size.item(0);

            NodeList txtF = fe.getChildNodes();
            NodeList txtS = se.getChildNodes();

            String finalF = txtF.item(0).getNodeValue().trim();
            String finalS = txtS.item(0).getNodeValue().trim();

            style.setFontFamily(finalF);
            style.setFontSize(finalS);
        }

// Vamos normalizar o documento de forma a todos os Nodes ficarem estruturados
        doc.getDocumentElement().normalize();

        // Criamos uma lista de Nodes que tenham o TagName passado por parametro        
        listOfKeys = doc.getElementsByTagName("set");

        // Definimos o nº total de Chaves existentes no Documento
        int totalKeys = listOfKeys.getLength();

        for (int i = 0; i <
                totalKeys; i++) {
            // Criamos um Node da Lista
            KeyNode = listOfKeys.item(i);
            // Se este Node for um Elemento
            if (KeyNode.getNodeType() == Node.ELEMENT_NODE) {
                // Criamos um Elemento atraves do Node
                Element KeyElement = (Element) KeyNode;

                // Criamos uma lista de Nodes que tenham o TagName passado por parametro                
                NodeList r = KeyElement.getElementsByTagName("r");
                NodeList g = KeyElement.getElementsByTagName("g");
                NodeList b = KeyElement.getElementsByTagName("b");
                NodeList bold = KeyElement.getElementsByTagName("bold");
                NodeList italic = KeyElement.getElementsByTagName("italic");
                NodeList cat = KeyElement.getElementsByTagName("category");

                // Criamos um Elemento com o Node que se encontra na posição 0 na NodeList
                Element rElement = (Element) r.item(0);
                Element gElement = (Element) g.item(0);
                Element bElement = (Element) b.item(0);
                Element boldElement = (Element) bold.item(0);
                Element italicElement = (Element) italic.item(0);
                Element catElement = (Element) cat.item(0);

                // NodeList Contendo todos os filhos deste Node
                NodeList txtR = rElement.getChildNodes();
                NodeList txtG = gElement.getChildNodes();
                NodeList txtB = bElement.getChildNodes();
                NodeList txtBold = boldElement.getChildNodes();
                NodeList txtItalic = italicElement.getChildNodes();
                NodeList txtCat = catElement.getChildNodes();

                // Vamos buscar o valor do Node e limpamos os espaços
                String finalR = txtR.item(0).getNodeValue().trim();
                String finalG = txtG.item(0).getNodeValue().trim();
                String finalB = txtB.item(0).getNodeValue().trim();
                String finalBold = txtBold.item(0).getNodeValue().trim();
                String finalItalic = txtItalic.item(0).getNodeValue().trim();
                String finalCat = txtCat.item(0).getNodeValue().trim();

                style.addStyle(new AlgolXXIStyle(finalR, finalG, finalB, finalBold, finalItalic), finalCat);
            }
        }
        NodeList listOfKeysf = doc.getElementsByTagName("flux");
        Node KeyNodef = listOfKeysf.item(0);
        if (KeyNodef.getNodeType() == Node.ELEMENT_NODE) {
            Element e = (Element) KeyNodef;

            NodeList family = e.getElementsByTagName("family");
            NodeList size = e.getElementsByTagName("size");
            NodeList bold = e.getElementsByTagName("bold");
            NodeList italic = e.getElementsByTagName("italic");

            NodeList r = e.getElementsByTagName("r");
            NodeList g = e.getElementsByTagName("g");
            NodeList b = e.getElementsByTagName("b");

            Element fe = (Element) family.item(0);
            Element se = (Element) size.item(0);
            Element bolde = (Element) bold.item(0);
            Element italice = (Element) italic.item(0);


            Element re = (Element) r.item(0);
            Element ge = (Element) g.item(0);
            Element be = (Element) b.item(0);


            NodeList txtF = fe.getChildNodes();
            NodeList txtS = se.getChildNodes();
            NodeList txtbold = bolde.getChildNodes();
            NodeList txtitalic = italice.getChildNodes();

            NodeList txtr = re.getChildNodes();
            NodeList txtg = ge.getChildNodes();
            NodeList txtb = be.getChildNodes();

            String finalF = txtF.item(0).getNodeValue().trim();
            String finalS = txtS.item(0).getNodeValue().trim();
            String finalbold = txtbold.item(0).getNodeValue().trim();
            String finalitalic = txtitalic.item(0).getNodeValue().trim();

            String finalr = txtr.item(0).getNodeValue().trim();
            String finalg = txtg.item(0).getNodeValue().trim();
            String finalb = txtb.item(0).getNodeValue().trim();

            style.setFluxfontFamily(finalF);
            style.setFluxfontSize(finalS);
            style.setFluxBold(finalbold);
            style.setFluxItalic(finalitalic);

            style.setConsColor(finalr, finalg, finalb);
        }
        NodeList listOfKeysc = doc.getElementsByTagName("cons");
        Node KeyNodec = listOfKeysc.item(0);
        if (KeyNodec.getNodeType() == Node.ELEMENT_NODE) {
            Element e = (Element) KeyNodec;

            NodeList family = e.getElementsByTagName("family");
            NodeList size = e.getElementsByTagName("size");
            NodeList bold = e.getElementsByTagName("bold");
            NodeList italic = e.getElementsByTagName("italic");

            NodeList r = e.getElementsByTagName("r");
            NodeList g = e.getElementsByTagName("g");
            NodeList b = e.getElementsByTagName("b");

            Element fe = (Element) family.item(0);
            Element se = (Element) size.item(0);
            Element bolde = (Element) bold.item(0);
            Element italice = (Element) italic.item(0);


            Element re = (Element) r.item(0);
            Element ge = (Element) g.item(0);
            Element be = (Element) b.item(0);


            NodeList txtF = fe.getChildNodes();
            NodeList txtS = se.getChildNodes();
            NodeList txtbold = bolde.getChildNodes();
            NodeList txtitalic = italice.getChildNodes();

            NodeList txtr = re.getChildNodes();
            NodeList txtg = ge.getChildNodes();
            NodeList txtb = be.getChildNodes();

            String finalF = txtF.item(0).getNodeValue().trim();
            String finalS = txtS.item(0).getNodeValue().trim();
            String finalbold = txtbold.item(0).getNodeValue().trim();
            String finalitalic = txtitalic.item(0).getNodeValue().trim();

            String finalr = txtr.item(0).getNodeValue().trim();
            String finalg = txtg.item(0).getNodeValue().trim();
            String finalb = txtb.item(0).getNodeValue().trim();

            style.setConsfontFamily(finalF);
            style.setConsfontSize(finalS);
            style.setConsBold(finalbold);
            style.setConsItalic(finalitalic);

            style.setConsColor(finalr, finalg, finalb);
        }





// Vamos normalizar o documento de forma a todos os Nodes ficarem estruturados
        doc.getDocumentElement().normalize();

        return style;
    }
}
