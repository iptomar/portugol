/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Erros;

import java.io.*;
import java.util.Vector;
import javax.xml.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;
import org.w3c.dom.Document;

/**
 * Classe que permite ler o conteúdo de um ficheiro XML.
 * Este ficheiro contém toda a informação relativa aos erros.
 * @author Tiago
 */
public class ReadXML {

    /**
     * path para o ficheiro XML de erros
     * Por defeito o ficheiro encontra-se na package de erros
     */
    private String filePath = "AlgolXXI/Erros/ERROS_XML.xml";

    /**
     * Constructor por defeito para a classe ReadXML
     */
    public ReadXML() {
    }

    /**
     * Contructor por parâmetros para a classe ReadXML
     * @param path String com o path do ficheiro xml de erros
     */
    public ReadXML(String path) {
        filePath = path;
    }

    /**
     * Método que permite retornar todo o conteúdo do ficheiro xml de erros
     * código do erro
     * descrição do erro
     * solução do erro
     * todos os itens estão entre ""
     * Exemplo "\"CODEI\"\"TYPEI\"\"DESCRIPTIONI\"\"SOLUTIONI\"
     * @return String errors com todo o contéudo do ficheiro
     */
    public String ReadXMLfile() {
        String errors = "";
        try {
            Document doc = this.getXmlDoc();
            NodeList error = doc.getElementsByTagName("Error");
            int totalERROR = error.getLength();
            System.out.println(totalERROR);

            for (int i = 0; i < totalERROR; i++) {
                Node firstERROR = error.item(i);
                if (firstERROR.getNodeType() == Node.ELEMENT_NODE) {
                    System.out.println("O " + i + "º filho é: " + firstERROR.getNodeName() + " ---" + " e o nome: " + firstERROR.getAttributes().item(0).getNodeValue());

                    //--------------------Codigo e tipo--------------------------------
                    //errors += " " + firstERROR.getNodeName() + "\"" + firstERROR.getAttributes().item(0).getNodeValue()+ "\"";
                    errors += "\"" + firstERROR.getAttributes().item(0).getNodeValue() + "\"";
                    errors += "\"" + firstERROR.getAttributes().item(1).getNodeValue() + "\"";
                    Element elementERROR = (Element) firstERROR;
                    //--------------------Descrição--------------------------------
                    NodeList description = elementERROR.getElementsByTagName("Description");
                    Element descriptionItem = (Element) description.item(0);
                    NodeList descriptionItemList = descriptionItem.getChildNodes();
                    System.out.println("Descrição: " + descriptionItemList.item(0).getNodeValue());
                    errors += "\"" + descriptionItemList.item(0).getNodeValue() + "\"";
                    //--------------------Solução--------------------------------
                    NodeList solution = elementERROR.getElementsByTagName("Solution");
                    Element solutionItem = (Element) solution.item(0);
                    NodeList solutionItemList = solutionItem.getChildNodes();
                    System.out.println("Solução: " + solutionItemList.item(0).getNodeValue());
                    errors += "\"" + solutionItemList.item(0).getNodeValue() + "\"";
                }
            }
        } catch (Exception exception) {
            System.out.println("Excepção: " + exception);
        }
        System.out.println(errors);
        return errors;
    }

    /**
     * Método que retorna um documento virtual com todo o conteúdo do documento XML 
     * @return Document
     */
    public Document getXmlDoc() {
        try {
            //colocar todo o conteúdo do documento XML num documento virtual
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(getClass().getClassLoader().getResourceAsStream(this.filePath));
            //normalizar documento 
            doc.getDocumentElement().normalize();
            System.out.println("Método getXmlDoc - O elemento root é: " + doc.getDocumentElement().getNodeName());
            return doc;
        //errors = doc.getDocumentElement().getNodeName();

        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Método que permite pesquisar no documento XML pela descrição de um dado erro.
     * @param code código do erro
     * @return String com a descrição do erro 
     */
    public String getDescription(String code) {
        String strDescription = "";
        Document doc = this.getXmlDoc();
        NodeList error = doc.getElementsByTagName("Error");
        int totalERROR = error.getLength();
        for (int i = 0; i < totalERROR; i++) {
            Node firstERROR = error.item(i);
            if (firstERROR.getNodeType() == Node.ELEMENT_NODE &&
                    firstERROR.getAttributes().item(0).getNodeValue().equals(code)) {
                Element elementERROR = (Element) firstERROR;
                //--------------------Descrição--------------------------------
                NodeList description = elementERROR.getElementsByTagName("Description");
                Element descriptionItem = (Element) description.item(0);
                NodeList descriptionItemList = descriptionItem.getChildNodes();
                System.out.println("Descrição: " + descriptionItemList.item(0).getNodeValue());
                strDescription = descriptionItemList.item(0).getNodeValue();
                break;
            }
        }
        return strDescription;
    }

    /**
     * Método que permite pesquisar no documento XML pela solução de um dado erro. 
     * @param code código do erro
     * @return String com a solução do erro 
     */
    public String getSolution(String code) {
        String strSolution = "";
        Document doc = this.getXmlDoc();
        NodeList error = doc.getElementsByTagName("Error");
        int totalERROR = error.getLength();
        for (int i = 0; i < totalERROR; i++) {
            Node firstERROR = error.item(i);
            if (firstERROR.getNodeType() == Node.ELEMENT_NODE &&
                    firstERROR.getAttributes().item(0).getNodeValue().equals(code)) {
                Element elementERROR = (Element) firstERROR;
                //--------------------Solução--------------------------------
                NodeList description = elementERROR.getElementsByTagName("Solution");
                Element descriptionItem = (Element) description.item(0);
                NodeList descriptionItemList = descriptionItem.getChildNodes();
                System.out.println("Solução: " + descriptionItemList.item(0).getNodeValue());
                strSolution = descriptionItemList.item(0).getNodeValue();
                break;
            }
        }
        return strSolution;
    }

    /**
     * Método que retorna um vector com todo o conteudo do ficheiro XMl de erros
     * @return Vector errors
     */
    public Vector getXMLfile() {
        Vector<Vector> errors = null;
        try {
            Document doc = this.getXmlDoc();
            NodeList error = doc.getElementsByTagName("Error");
            int totalERROR = error.getLength();

            errors = new Vector<Vector>(totalERROR);

            for (int i = 0; i < totalERROR; i++) {
                Vector<String> tmp = new Vector<String>(4);
                Node firstERROR = error.item(i);
                if (firstERROR.getNodeType() == Node.ELEMENT_NODE) {

                    //----Código e Tipo do Erro------------------------------------
                    tmp.addElement(firstERROR.getAttributes().item(0).getNodeValue());
                    tmp.addElement(firstERROR.getAttributes().item(1).getNodeValue());
                    Element elementERROR = (Element) firstERROR;

                    //--------------------Descrição--------------------------------
                    NodeList description = elementERROR.getElementsByTagName("Description");
                    Element descriptionItem = (Element) description.item(0);
                    NodeList descriptionItemList = descriptionItem.getChildNodes();

                    tmp.addElement(descriptionItemList.item(0).getNodeValue());
                    //--------------------Solução--------------------------------
                    NodeList solution = elementERROR.getElementsByTagName("Solution");
                    Element solutionItem = (Element) solution.item(0);
                    NodeList solutionItemList = solutionItem.getChildNodes();

                    tmp.addElement(solutionItemList.item(0).getNodeValue());

                    errors.addElement(tmp);
                }

            }
        } catch (Exception exception) {
            System.out.println("Excepção: " + exception);
        }
        return errors;
    }

    /**
     * Selector para a variável global <filePath>
     * @return String filePath
     */
    public String getFilePath() {
        return this.filePath;
    }

    /**
     * Modificador para a variável global <filePath>
     * @param _filePath String com o novo path para o ficheiro XML de erros
     */
    public void setFilePath(String _filePath) {
        this.filePath = _filePath;
    }
    
    /***
     * Método que retorna um vector com tamanho três. Primeira posição corresponde à linha onde ocorre o erro
     * segunda posição à descrição do tipo de erro e terceira posição à solução para o erro.
     * @param line - linha onde ocorre o erro
     * @param ERROR_ID código do erro
     * @return vector
     */
    public Vector<String> LanguageException(int line, int ERROR_ID){
        Vector<String> languageException = new Vector<String>();
        languageException.add(Integer.toString(line));
        languageException.add(this.getDescription(Integer.toString(ERROR_ID)));
        languageException.add(this.getSolution(Integer.toString(ERROR_ID)));        
        return languageException;
    }
    
    /***
     * Método que retorna um vector com tamanho três e que permite definir as variáveis na descrição do erro.
     * Primeira posição corresponde à linha onde ocorre o erro segunda posição à descrição 
     * do tipo de erro e terceira posição à solução para o erro.
     * @param line - linha onde ocorre o erro
     * @param ERROR_ID código do erro
     * @param exception - vector com todas as variáveis a alterar na descrição do erro
     * @return vector
     */
    public Vector<String> LanguageException(int line, int ERROR_ID, Vector<String> exception) {
        Vector<String> languageException = new Vector<String>();
        String strDescription = "";
        String descriptionAux = this.getDescription(Integer.toString(ERROR_ID));
        String[] aux = descriptionAux.split("'");
        int index = 0;
        for (int i = 0; i < aux.length; i++) {
            if (aux[i].charAt(0) == '@' && index < exception.size()) {
                aux[i] = exception.get(index) + " ";
                index++;
            }
            strDescription += aux[i];
        }
        strDescription = strDescription.trim();
        languageException.add(Integer.toString(line));
        languageException.add(strDescription);
        languageException.add(this.getSolution(Integer.toString(ERROR_ID)));
        return languageException;
    }
    
    /***
     * Método que retorna um vector com tamanho três e que permite definir a descrição e solução do erro.
     * Primeira posição corresponde à linha onde ocorre o erro segunda posição à descrição 
     * do tipo de erro e terceira posição à solução para o erro.
     * @param line - linha onde ocorre o erro
     * @param description - descrição do erro
     * @param solution - solução do erro
     * @return vector
     */
    public Vector<String> LanguageException(int line, String description, String solution){
        Vector<String> languageException = new Vector<String>();
        languageException.add(Integer.toString(line));
        languageException.add(description);
        languageException.add(solution);        
        return languageException;
    }
}
