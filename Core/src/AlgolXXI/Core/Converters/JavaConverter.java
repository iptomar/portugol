/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Core.Converters;

import AlgolXXI.Core.Execute.ExecuteProgram;
import AlgolXXI.Core.Make.MakeProgram;
import AlgolXXI.Core.Memory.SymbolData;
import AlgolXXI.Core.Memory.SymbolStructure;
import AlgolXXI.Core.NodeFluxo;
import AlgolXXI.Core.Parser.Keyword;
import AlgolXXI.Core.Utils.IteratorLine;
import AlgolXXI.Core.Utils.LanguageException;
import java.util.Vector;

/**
 *
 * @author Apocas
 */
public class JavaConverter extends Converter {

    private Vector<SymbolStructure> estruturas = null;
    private int indent = 1;
    private String tab = "\t";

    public JavaConverter(String codigof) throws Exception {
        programa = new MakeProgram(codigof);
        executor = new ExecuteProgram(programa);
        estruturas = programa.getMainMemory().getStructures();
        inicio = "\nimport javax.swing.JOptionPane;\n\nclass programa {\n";
        convertStructs();
        fim = "}\n";
    }

    public void convert(NodeFluxo node) throws LanguageException {
        IteratorLine it = null;
        switch (node.getType()) {
            case Keyword.INICIO:
                if (node.getText().contains("inicio programa")) {
                    codigo = codigo + "\n" + getIdent() + "public static void main(String args[]) {\n" +
                            getIdent() + tab + "new programa();\n" +
                            getIdent() + "}\n";
                    codigo = codigo + "\n" + getIdent() + "public programa(){\n";
                } else {
                    it = new IteratorLine(node.getText(), " ");
                    String retorno = it.next();
                    String nome = it.next();
                    String parametros = it.next();
                    parametros = parametros.replace("(", "");
                    parametros = parametros.replace(")", "");
                    parametros = parametros.trim();
                    codigo = codigo + "\n" + getIdent() + "public static " + getConvertedType(retorno) + " " + nome + it.next() + "(";
                    IteratorLine it_parametros = new IteratorLine(parametros, ",");
                    while (it_parametros.hasNext()) {
                        IteratorLine it2 = new IteratorLine(it_parametros.next(), " ");
                        String var_tipo = it2.next();
                        String var_nome = it2.next();
                        codigo = codigo + getConvertedType(var_tipo) + " " + var_nome;
                        if (it_parametros.hasNext()) {
                            codigo = codigo + ", ";
                        }
                    }
                    codigo = codigo + ") {\n";
                }
                indent++;
                break;
            case Keyword.FUNCAODEFINIDA:
                codigo = codigo + getIdent() + node.getText().replace(" ", "") + ";\n";
                break;
            case Keyword.FIM:
                indent--;
                codigo = codigo + getIdent() + "}\n";
                break;
            case Keyword.ESCREVER:
                String escreve = Keyword.GetTextKey(Keyword.ESCREVER);
                String line = node.getText().substring(escreve.length()).trim();
                int ultima_aspa = line.lastIndexOf('"');
                String vars = line.substring(ultima_aspa + 1, line.length());
                vars = vars.replace(",", " + ");
                codigo = codigo + getIdent() + "System.out.print(\"" + line.substring(1, ultima_aspa) + "\"" + vars + ");\n";
                break;
            case Keyword.DEFINIR:
                addVariable(node.getText());
                break;
            case Keyword.LER:
                it = new IteratorLine(node.getText(), " ,");
                String ler = it.next();
                while (it.hasNext()) {
                    codigo = codigo + getIdent() + it.next() + " = JOptionPane.showInputDialog(null, \"Input do Utilizador:\");\n";
                }
                break;
            default:
                break;
        }

        NodeFluxo next = node.getNext();

        if (next != null) {
            convert(next);
        }
    }

    public String getConvertedType(String retorno) {
        if (retorno.equals("vazio")) {
            return "void";
        } else if (retorno.toLowerCase().equals("inteiro")) {
            return "int";
        } else if (retorno.toLowerCase().equals("logico")) {
            return "boolean";
        } else if (retorno.toLowerCase().equals("texto")) {
            return "String";
        } else if (retorno.toLowerCase().equals("caracter")) {
            return "char";
        } else if (retorno.toLowerCase().equals("real")) {
            return "double";
        }
        return null;
    }

    private void addVariable(String nodef) {
        IteratorLine it = new IteratorLine(nodef, " ,");
        String tipo = it.next();
        if (tipo.contains("variavel")) {
            tipo = it.next();
        }
        if (getConvertedType(tipo) != null) {
            codigo = codigo + getIdent() + getConvertedType(tipo) + " ";
            while (it.hasNext()) {
                codigo = codigo + it.next();
                if (getConvertedType(tipo).contains("int") || getConvertedType(tipo).contains("float")) {
                    codigo = codigo + " = 0";
                } else if (getConvertedType(tipo).contains("String")) {
                    codigo = codigo + " = \"\"";
                }
                codigo = codigo + it.next();
                if (it.hasNext()) {
                    codigo = codigo + ", ";
                }
            }
            codigo = codigo + ";\n";
        } else {
            it = new IteratorLine(nodef, " ");
            String tipo1 = it.next();
            IteratorLine it2 = new IteratorLine(nodef.replace(tipo1, "").trim(), ",");
            while (it2.hasNext()) {
                IteratorLine it3 = new IteratorLine(it2.next(), "[]");
                String nome1 = it3.next();
                String tipo2 = it3.next();
                if (tipo2 != null) {
                    codigo = codigo + getIdent() + tipo1 + " " + nome1 + "[] " + "= new " + tipo1 + "[" + tipo2 + "]" + ";\n";
                    codigo = codigo + getIdent() + "for(int i=0;i<" + tipo2 + ";i++){\n" +
                            getIdent() + tab + nome1 + "[i] = new " + tipo1 + "();\n" +
                            getIdent() + "}\n";
                } else {
                    codigo = codigo + getIdent() + tipo1 + " " + nome1 + "= new " + tipo1 + "();\n";
                }
            }
        }
    }

    private void convertStructs() {
        for (SymbolStructure struct : estruturas) {
            codigo = codigo + "\n" + getIdent() + "public class " + struct.getName() + " {\n";
            for (SymbolData symbol : struct.getData()) {
                String tipo = getConvertedType(symbol.getTypeName());
                if (tipo == null && symbol instanceof SymbolStructure) {
                    tipo = ((SymbolStructure) symbol).getTypeName();
                }
                codigo = codigo + getIdent() + tab + "public " + tipo + " " + symbol.getName() + ";\n";
            }
            codigo = codigo + getIdent() + tab + "public " + struct.getName() + "(){}\n";
            codigo = codigo + getIdent() + "}\n";
        }
    }

    private String getIdent() {
        String tablu = "";
        for (int i = 0; i < indent; i++) {
            tablu = tablu + tab;
        }
        return tablu;
    }
}
