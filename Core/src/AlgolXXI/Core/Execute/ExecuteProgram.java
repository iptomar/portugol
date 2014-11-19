/*****************************************************************************/
/****     Copyright (C) 2008                                              ****/
/****     Antonio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politecnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/**
 * Created : 1/Jun/2008 - 17:19:14
 * @author Antonio M@nso
 */
package AlgolXXI.Core.Execute;

import AlgolXXI.Core.Evaluate.Expression;
import AlgolXXI.Core.Function;
import AlgolXXI.Core.Make.MakeProgram;
import AlgolXXI.Core.Make.MakeSymbol;
import AlgolXXI.Core.Memory.DataObject;
import AlgolXXI.Core.Memory.Memory;
import AlgolXXI.Core.Memory.SymbolData;

import AlgolXXI.Core.NodeFluxo;
import AlgolXXI.Core.Parser.Keyword;
import AlgolXXI.Core.Parser.Token;
import AlgolXXI.Core.Utils.IteratorLineTokens;
import AlgolXXI.Core.Utils.LanguageException;
import AlgolXXI.Core.Utils.Values;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import org.openide.util.Exceptions;

public class ExecuteProgram implements Runnable {

    /**
     * Vector com os blocos do programa
     */
    private ArrayList<Function> blocks = new ArrayList<Function>();
    /**
     * apontador para o inicio do bloco principal (programa)
     */
    private NodeFluxo start;
    /**
     * Nodo do fluxograma que sera o próximo a ser executado
     * Util para fazer a execução passo a passo
     */
    NodeFluxo inExecution;
    private ConsoleIO console = null;
    private boolean thread_running = false;
    MakeProgram program;

    /**
     * Execucao do programa
     */
    public void run() {
        while (inExecution != null && thread_running) {
            try {
                inExecution = ExecuteNode(inExecution, console);
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    public ExecuteProgram(MakeProgram prog) throws LanguageException {
        //funcção principal
        Function main = prog.getMainFunction();
        //clone da main
        Function cloneMain = new Function(main, new ArrayList<SymbolData>(), null);
        blocks.add(cloneMain);
        start = cloneMain.getBegin();
        inExecution = start;
        program = prog;
    }

    /**
     * Executa o programa
     * @param console - consola para IO
     * @throws AlgolXXI.Core.Utils.LanguageException
     */
    public void ExecuteTest(ConsoleIO console) throws LanguageException, InterruptedException, ExecutionException, Exception {
        inExecution = start.getNext();
        while (inExecution != null) {
            inExecution = ExecuteNode(inExecution, console);
        }
    }

    /**
     * Executa o programa
     * @param console - consola para IO
     * @throws AlgolXXI.Core.Utils.LanguageException
     */
    public void Execute(ConsoleIO consolef) throws LanguageException, InterruptedException, ExecutionException {
        if (!thread_running) {
            this.console = consolef;
            thread_running = true;
            new Thread(this).start();
        }
    }

    /**
     * Para a execucao do programa
     */
    public void stopExecution() {
        if (thread_running) {
            thread_running = false;
            if (console != null) {
                console.StopReading();
            }
        }
    }

    public NodeFluxo getStart() {
        return start;
    }

    /**
     * Executa um nodo e retorna o proximo nodo a ser executado
     * @param node - Nodo a executar
     * @param console - Consola para IO
     * @return - proximo nodo a ser executado
     * @throws AlgolXXI.Core.Utils.LanguageException
     */
    public NodeFluxo ExecuteNode(NodeFluxo node, ConsoleIO console) throws LanguageException, Exception {

        switch (node.getType()) {

            case Keyword.PROGRAMA: //iniciio dos fluxogramas
            case Keyword.INICIO:
                return node.getNext();
            case Keyword.FIM:
                removeFunction(node.getBlockID());
                return node.getNext();

            // NOTA: esta keyword deve ser alterada
            case Keyword.FUNCAODEFINIDA:
                //fazer um bloco com ligacao ao proximo
                //modelo do bloco
                Function model = program.getFunction(node);
                //parametros
                Function blk = new Function(model, getParameters(node), node.getNext());
                System.out.println("\n NEW BLOCK \n" + blk.toString());
                //adicionalo ao vector
                blocks.add(blk);
                //Retornar o proximo no a seguir a inicio               
                return blk.getBegin();

            case Keyword.ESCREVER:
                executeWrite(node, console);
                return node.getNext();

            case Keyword.DEFINIR:
                executeDefine(node);
                return node.getNext();

            case Keyword.CALCULAR:
                executeCalculate(node);
                return node.getNext();

//            case Keyword.LER:
//                executeRead(node, console);
//                return node.GetNext();
            default:
                return null;

        }
    }
    // extrai os parametros da linha de chamada da funcao
    public ArrayList<SymbolData> getParameters(NodeFluxo node)
            throws LanguageException, Exception {
        ArrayList<SymbolData> param = new ArrayList<SymbolData>();
        IteratorLineTokens it = new IteratorLineTokens(node.getInstruction());
        //nome da funçao
        Token nameFunc = it.next();
        //parentesis
        it.next();
        //memoria da função
        Memory mem = getMemory(node.getBlockID());
        //contruir os parametros (referencias ou calculos)
        while (it.hasNext()) {
            Expression expPar = MakeSymbol.getExpression(mem, it);
            //variavel na memoria
            SymbolData d = mem.getSymbol(expPar.toString());
            //não existe a variavel e e uma expressao
            if (d == null) {
                expPar = MakeSymbol.replaceVarsToValues(mem, expPar);
                Token value = expPar.evaluate(mem);
                d = MakeSymbol.makeNewDataSymbol(Keyword.GetTextKey(Keyword.VARIAVEL),
                        Keyword.GetTextKey(value.ttype), "_tmpVar",
                        value.getValue(), node.getLevel());
            }
            param.add(d);
            //virgula ou parentesis
            it.next();
        }
        System.out.println("PARAMS:" + param.toString());
        return param;
    }

    private void executeCalculate(NodeFluxo node) throws Exception {
        IteratorLineTokens it = new IteratorLineTokens(node.getInstruction());
        Token tvar = it.next();
        //retirar a atribuição
        it.next();
        Memory mem = getMemory(node.getBlockID());
        Token value = MakeSymbol.getValue(mem, it);
        SymbolData var = mem.getSymbol(tvar.getValue());
        var.setValue(value.getValue());

    }

    /**
     * Executa a a escrita de dados na consola
     * @param node - Nodo a escrever
     * @param console Local de escrita
     */
    private void executeWrite(NodeFluxo node, ConsoleIO console) throws Exception {
        IteratorLineTokens it = new IteratorLineTokens(node.getInstruction());
        //retirar o escrever
        it.next();
        Memory mem = getMemory(node.getBlockID());
        while (it.hasNext()) {
            Token value = MakeSymbol.getValue(mem, it);
            String txt = Values.removeStringComas(value.getValue());
            txt = DataObject.getEscapedString(txt);
            console.write(txt);
            //passar a virgula
            if (it.hasNext()) {
                it.next();
            }
        }


    }

    /**
     * Retorna o bloco com a identificação ID
     * @param id - Identificação do bloco
     * @return Bloco de programa
     */
    public Function getBlock(int id) {
        for (Function b : blocks) {
            if (b.getID() == id) {
                return b;
            }
        }
        return null;
    }

    /**
     * Devolve a memoria do bloco ID
     * @param ID - Identificação do Bloco
     * @return - Memoria do bloco
     */
    public Memory getMemory(int ID) {
        Function blk = getBlock(ID);
        return blk.getMemory();
    }

    /**
     * Define variaveis
     * @param node - nodo de definição
     * @throws AlgolXXI.Core.Utils.LanguageException
     */
    private void executeDefine(NodeFluxo node) throws Exception {
        Function blk = getBlock(node.getBlockID());
        MakeSymbol.Make(node.getInstruction(), blk.getMemory(), node.getLevel());
    }

    public void removeFunction(int ID) {
        for (Function b : blocks) {
            if (b.getID() == ID) {
                blocks.remove(b);
                break;
            }
        }
    }

    public ArrayList<Function> getBlocks() {
        return blocks;
    }
}
