/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Erros;

import java.io.*;

import org.w3c.dom.*;

import javax.xml.parsers.*;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

/**
 * Classe que permite criar um ficheiro XML de erros e adicionar novos filhos a esse ficheiro
 * @author Tiago
 */
public class WriteXML {

    private String filePath;
    private File xmlOutputFile;
    private FileOutputStream fos;
    private Document doc;
    private Element root;

    /**
     * Constructor por parâmetros da classe WriteXML
     * @param path string com a path onde ficará guardado o ficheiro XML de erros
     */
    public WriteXML(String path) {
        filePath = path;
       
        if(!filePath.toUpperCase().endsWith(".XML"))
           filePath = filePath + ".XML";
    }

    /**
     * Método que permite criar o ficheiro XML. O path para o ficheiro XML de erros 
     * é passado na variável global <filePath>.
     */
    public void createXMLFile() {
        try {
            xmlOutputFile = new File(filePath);
            fos = new FileOutputStream(xmlOutputFile);

            /////////////////////////////
            //Creating an empty XML Document

            //We need a Document
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            doc = docBuilder.newDocument();
            doc.setXmlVersion("1.0");

            //create the root element and add it to the document
            root = doc.createElement("Errors");
            doc.appendChild(root);

            //create a comment and put it in the root element
            Comment comment = doc.createComment("Lista Com todos os Erros para o Programa ALGOLXXI");
            root.appendChild(comment);
            comment = doc.createComment("Nota: sempre que for necessário adicionar uma variável à descrição de um erro usar obrigatóriamente a notação '@variable.");
            root.appendChild(comment);


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Método que permite adicionar um novo node ao ficheiro XML
     * @param code string com o id do erro
     * @param type string com o tipo de erro
     * @param description string com a descrição do erro
     * @param solution string com a solução do erro
      */
    public void addXMLNode(String code, String type, String description, String solution) {
        try {

            ////////////////////////
            //Creating the XML tree

            //create child element, add an attribute, and add to root
            Element child = doc.createElement("Error");
            child.setAttribute("Code", code);
            child.setAttribute("Type", type);

            Element Description = doc.createElement("Description");
            Text textDesc = doc.createTextNode(description);
            Description.appendChild(textDesc);


            Element Solution = doc.createElement("Solution");
            Text textSol = doc.createTextNode(solution);
            Solution.appendChild(textSol);

            child.appendChild(Description);
            child.appendChild(Solution);
            
            root.appendChild(child);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Método que permite guardar o ficheiro XML de erros. Usado no editor de erros.
     */
    public void saveXMLFile() {
        try {
            /////////////////
            //Output the XML

            //set up a transformer
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");

            //create string from xml tree
            //StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(fos);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    /**
     * Selector para a variável global <filePath>
     * @return String filePath
     */
    public String getFilePath(){
        return this.filePath;
    }
    
    /**
     * Modificador para a variável global <filePath>
     * @param _filePath String com o novo path para o ficheiro XML de erros
     */
    public void filePath( String _filePath ){
        this.filePath = _filePath;
    }
}
