/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor.CodeEditor;

import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.openide.util.Exceptions;
import org.w3c.dom.*;

/**
 * Classe que permite ler do ficheiro XML os CodeStyle.
 * @author Saso
 */
public class CodeStyleReader {

    /**
     * Selector para os CodeStyle lidos dos ficheiros XML.
     * @return  - CodeStyle codeStyle
     */
    public static CodeStyle getStyle(String path) {

        DocumentBuilderFactory docBuilderFactory = null;
        DocumentBuilder docBuilder = null;
        Document doc = null;
        FileInputStream input = null;
        
        CodeStyle codeStyle = new CodeStyle();

        try {
            docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docBuilderFactory.newDocumentBuilder();

            input = new FileInputStream(path);
            doc = docBuilder.parse(input);
        } catch (Exception ex) {
            try {
                String[] aux = path.split("/");
                input = new FileInputStream(aux[aux.length - 1]);
                doc = docBuilder.parse(input);
            } catch (Exception ex1) {
                Exceptions.printStackTrace(ex1);
            }
        }

        NodeList listOfKeys = doc.getElementsByTagName("font");
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

            codeStyle.setFontFamily(finalF);
            codeStyle.setFontSize(finalS);
        }

// Vamos normalizar o documento de forma a todos os Nodes ficarem estruturados
        doc.getDocumentElement().normalize();

        // Criamos uma lista de Nodes que tenham o TagName passado por parametro        
        listOfKeys =
                doc.getElementsByTagName("set");

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

                codeStyle.addStyle(new AlgolXXIStyle(finalR, finalG, finalB, finalBold, finalItalic), finalCat);
            }

        }
        try {
            input.close();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return codeStyle;
    }
}
