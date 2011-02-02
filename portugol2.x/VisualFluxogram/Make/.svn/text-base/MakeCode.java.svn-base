/*
 * MakeCode.java
 *
 * Created on 3 de Junho de 2006, 17:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package VisualFluxogram.Make;



import Portugol.Language.Evaluate.CalculusElement;
import Portugol.Language.Parse.Expression;
import Portugol.Language.Parse.Symbol;
import Portugol.Language.Parse.Variable;
import Portugol.Language.Utils.IteratorExpression;
import Portugol.Language.Utils.Parentesis;
import Portugol.Language.Utils.Values;
import VisualFluxogram.*;
import VisualFluxogram.Parser.ParseStructure;
import VisualFluxogram.Parser.ParseText;
import VisualFluxogram.Parser.UnknowExpression;
import VisualFluxogram.Patterns.Conexao;
import VisualFluxogram.Patterns.Decisao;
import VisualFluxogram.Patterns.Escrita;
import VisualFluxogram.Patterns.Forma;
import VisualFluxogram.Patterns.Leitura;
import VisualFluxogram.Patterns.Processo;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.util.Vector;

/**
 *
 * @author Eduardo
 */
public class MakeCode {

    private String code;
    private Vector Stack=new Vector();
    private Vector Formas=new Vector();
    
    private Vector Ifs=new Vector();
    private Vector ConnectionCycles=new Vector();
    
    private Vector memory=new Vector();
    
    public Vector getMemory(){
        return memory;
    }
    
    /** Creates a new instance of MakeCodee */
    public MakeCode(Vector AllForms){
        Formas = AllForms;
    }
    
    public String getCode(){
        return code;
    }
    
    public String WriteCodeWithoutParse() throws Exception{
        Stack.clear();
        code="";
        Forma inicio = ParseStructure.GetInicialFigure(Formas);
        WriteLine(0,"inicio");
        CalcCodeWithoutParse(inicio.GetNext());
        WriteLine(0,"fim");
        Stack.clear();
        Stack.trimToSize();
        
        return code;
    }
    
    public Forma WriteCodeWithParse(List ErrList, Vector ErrForms) throws Exception{
        
        ParseStructure parse = new ParseStructure(Formas, ErrList, ErrForms);        
        Forma inicio = parse.ParseFlowChart(true);
        Ifs = parse.GetIfs();
        ConnectionCycles = parse.GetConnectionCycles();
        
        
        Stack.clear();        
        //code="";
        //WriteLine(0,"inicio");
        code="inicio\n";
        //escrever cariaveis
        memory = parse.GetVariables();
        WriteVariables(memory);
        CalcCodeWithParse(inicio.GetNext());
        //WriteLine(0,"fim");
        code+="fim";
        
        Stack.clear();
        Stack.trimToSize();        
        return inicio;
    }
    

    public void WriteVariables(Vector Variables){
        
        for (int i=0;i<Variables.size();i++){
            Symbol var=(Symbol)Variables.get(i);
            if (var.getType()==Symbol.DESCONHECIDO)
                var.setType(Symbol.TEXTO);
            WriteLine(0,var.getStringType().toLowerCase()+" "+var.getName());
        }        
    }
    
    
    private void WriteLine(int Indentation, String str){        
        for (int i=0;i<Indentation+1;i++)
            code+="\t";
        code+=str+"\n";
        //System.out.println(str);
    }
    
    //--------------------------------------------------------------------------
    
    //calcular codigo do fluxograma a começar na figura f
    //Stack->usado para calculo de condicoes(onde estas abre e feixam)
    private void CalcCodeWithParse(Forma f){
        
        if (f instanceof Leitura){
            WriteLine(Stack.size(), "ler "+f.Text);
            CalcCodeWithParse(f.GetNext());
            return;
        }
        
        if (f instanceof Processo){
            WriteLine(Stack.size(), f.Text);
            CalcCodeWithParse(f.GetNext());
            return;
        }
        
        if (f instanceof Escrita){
            WriteLine(Stack.size(), "escrever "+f.Text);
            CalcCodeWithParse(f.GetNext());
            return;
        }
        
        if (f instanceof Conexao){            
            
            //verificar se a condicao de if na parte de verdade chegou ao fim
            if (!Stack.isEmpty() && Stack.lastElement()==f)
                return;
            
            //verificar se a conexao é a figura de terminacao de if
            else if (!Stack.isEmpty() && Stack.lastElement() instanceof Decisao){
                
                int search_if=Ifs.indexOf(Stack.lastElement())+1;
                if (search_if!=0 && f==Ifs.get(search_if) ){                
                    //verificar se existe condicao de falso no if
                    if (((Forma)Stack.lastElement()).GetIfFalse()!=f){
                        WriteLine(Stack.size()-1, "senao");
                        //adicionar fig de terminacao do if(conexao) e remover a fig de inicio
                        Stack.add(f);
                        CalcCodeWithParse(((Forma)Stack.remove(Stack.size()-2)).GetIfFalse());
                    }
                
                    //remover figura de terminacao da condicao if
                    //->conexao se o falso tiver argumentos, ->decisao se falso nao tiver argumentos
                    Stack.removeElementAt(Stack.size()-1);
                    WriteLine(Stack.size(), "fimse");
                }
            }
            
            
            //verificar se a conexao pertence a uma condicao de repete/faz
             for (int i=0;i<ConnectionCycles.size();i++)
                //descobrir o repete da figura
                if ( ((Forma)ConnectionCycles.get(i)).GetIfFalse()==f  ){
                    //&& Intersects(f,(Forma)ConnectionCycles.get(i), !Stack.isEmpty() ? (Forma)Stack.lastElement() : null)){
                
                    WriteLine(Stack.size(), "repete");                        
                    //adicionar na stack o sitio onde deve voltar para concluir o repete
                    Stack.add( (Forma)ConnectionCycles.remove(i) );
                    CalcCodeWithParse(f.GetNext());                    
                    WriteLine(Stack.size()-1, "ate "+((Forma)Stack.lastElement()).Text);
                    
                    //seguir repete depois de concluido pelo iftrue
                    CalcCodeWithParse( ((Forma)Stack.remove(Stack.size()-1)).GetIfTrue() );
                    //sair de modo a nao recalcular o repete
                    return;
                    
                //descobrir o faz da figura
                }else if ( ((Forma)ConnectionCycles.get(i)).GetIfTrue()==f ){
                    //&& Intersects(f,(Forma)ConnectionCycles.get(i), !Stack.isEmpty() ? (Forma)Stack.lastElement() : null)){
                
                    WriteLine(Stack.size(), "faz");                        
                    //adicionar na stack o sitio onde deve voltar para concluir o faz
                    Stack.add( (Forma)ConnectionCycles.remove(i) );
                    CalcCodeWithParse(f.GetNext());
                    WriteLine(Stack.size()-1, "enquanto "+((Forma)Stack.lastElement()).Text);                    
                    
                    //seguir faz depois de concluido pelo iffalse
                    CalcCodeWithParse( ((Forma)Stack.remove(Stack.size()-1)).GetIfFalse() );
                    //sair de modo a nao recalcular o faz
                    return;
                }
            
            //continua com o codigo
            CalcCodeWithParse(f.GetNext());
            return;            
        }   
        
        if (f instanceof Decisao){
        
            //verificar se foi concluida alguma condicao
            if (!Stack.isEmpty() && Stack.lastElement()==f)
                return;
        
            //verificar se se trata de um if
            if (Ifs.contains(f)){
                WriteLine(Stack.size(), "se "+f.Text+" entao");
                Stack.add(f);
                CalcCodeWithParse(f.GetIfTrue());
                return;
            
            //se nao for um if tem de ser um enquanto
            }else{
                WriteLine(Stack.size(), "enquanto "+f.Text+" faz");
                Stack.add(f);
                CalcCodeWithParse(f.GetIfTrue());
                Stack.removeElementAt(Stack.size()-1);
                WriteLine(Stack.size(), "fimenquanto");
                CalcCodeWithParse(f.GetIfFalse());
                return;
            }
        }
        
        return;        
    }
    
    
    
    //calcular codigo do fluxograma a começar na figura f
    //Stack->usado para calculo de condicoes(onde estas abre e feixam)
    private void CalcCodeWithoutParse(Forma f){
        if (f instanceof Leitura){
            WriteLine(Stack.size(), "ler "+f.Text);            
            //verificar se a proxima figura está a completar uma condicao
            if (f.GetNext()!=null)
                CalcCodeWithoutParse(f.GetNext());
            //se tiver entao volta a parte de finalizacao da expressao
            return;
        }
        
        if (f instanceof Processo){
            WriteLine(Stack.size(), f.Text);
            if (f.GetNext()!=null)
                CalcCodeWithoutParse(f.GetNext());
            return;
        }
        
        if (f instanceof Escrita){
            WriteLine(Stack.size(), "escrever "+f.Text);        
            if (f.GetNext()!=null)
                CalcCodeWithoutParse(f.GetNext());
            return;
        }
        
        if (f instanceof Conexao){
            
            //verificar se a condicao de if na parte de verdade chegou ao fim
            if (!Stack.isEmpty() && Stack.lastElement()==f)
                return;
            
            //verificar se a conexao é a figura de terminacao de if
            //---------------------------------------------------------------
            //usada a codincao de if como figura de stop para garantir que nao sao
            //usados ciclos na procura da figura de conexao do if
            else if (!Stack.isEmpty() && Stack.lastElement() instanceof Decisao && 
                    ParseStructure.Intersects(((Forma)Stack.lastElement()).GetIfFalse(),f, (Forma)Stack.lastElement()) && 
                    ParseStructure.Intersects(((Forma)Stack.lastElement()).GetIfTrue(),f, (Forma)Stack.lastElement())){
                
                //verificar se existe condicao de falso no if
                if (((Forma)Stack.lastElement()).GetIfFalse()!=f){
                    WriteLine(Stack.size()-1, "senao");
                    
                    //adicionar fig de terminacao do if(conexao) e remover a fig de inicio
                    Stack.add(f);
                    CalcCodeWithoutParse(((Forma)Stack.remove(Stack.size()-2)).GetIfFalse());
                }
                
                WriteLine(Stack.size(), "fimse");                
                //remover figura de terminacao da condicao if
                //->conexao se o falso tiver argumentos, ->decisao se falso nao tiver argumentos
                Stack.removeElementAt(Stack.size()-1);
            }
            
            
            //verificar se a conexao pertence a uma condicao de repete
            //---------------------------------------------------------------            
            //usada a ultima codincao como figura de stop para garantir que nao sao
            //usados ciclos na procura da conexao do repete
            
            //descobrir o repete da figura
            for(int i=0; i<Formas.size(); i++)
                if (((Forma)(Formas.get(i))).GetIfFalse()==f && 
                    ParseStructure.Intersects(f,(Forma)Formas.get(i), !Stack.isEmpty() ? (Forma)Stack.lastElement() : null) ){
                
                    if (f!=null){
                        WriteLine(Stack.size(), "repete");                        
                        //adicionar na stack o sitio onde deve voltar para concluir o repete
                        Stack.add(Formas.get(i));
                        CalcCodeWithoutParse(f.GetNext());
                        Stack.removeElementAt(Stack.size()-1);
                        WriteLine(Stack.size(), "ate "+((Forma)(Formas.get(i))).Text);                        
                    }
                    //seguir repete depois de concluido pelo iftrue
                    if (((Forma)(Formas.get(i))).GetIfTrue()!=null)
                        CalcCodeWithoutParse(((Forma)(Formas.get(i))).GetIfTrue());
                    //sair de modo a nao recalcular o repete
                    return;
                }else if (((Forma)(Formas.get(i))).GetIfTrue()==f && 
                    ParseStructure.Intersects(f,(Forma)Formas.get(i), !Stack.isEmpty() ? (Forma)Stack.lastElement() : null) ){
                
                    if (f!=null){
                        WriteLine(Stack.size(), "faz");                        
                        //adicionar na stack o sitio onde deve voltar para concluir o repete
                        Stack.add(Formas.get(i));
                        CalcCodeWithoutParse(f.GetNext());
                        Stack.removeElementAt(Stack.size()-1);
                        WriteLine(Stack.size(), "enquanto "+((Forma)(Formas.get(i))).Text);                        
                    }
                    //seguir repete depois de concluido pelo iffalse
                    if (((Forma)(Formas.get(i))).GetIfFalse()!=null)
                        CalcCodeWithoutParse(((Forma)(Formas.get(i))).GetIfFalse());
                    //sair de modo a nao recalcular o repete
                    return;
                }
            
            //continua com o codigo
            if (f.GetNext()!=null)
                CalcCodeWithoutParse(f.GetNext());
            return;            
        }   
        
        if (f instanceof Decisao){
        
            //verificar se foi concluida alguma condicao-->(desnecessario)
            if (!Stack.isEmpty() && Stack.lastElement()==f)
                return;
        
            //verificar se se trata de um enquanto
            if (ParseStructure.Intersects(f.GetIfTrue(),f, !Stack.isEmpty() ? (Forma)Stack.lastElement() : null)){      
                
                if (f.GetIfTrue()!=null){                
                    WriteLine(Stack.size(), "enquanto "+f.Text+" Faz");
                    Stack.add(f);
                    CalcCodeWithoutParse(f.GetIfTrue());
                    Stack.removeElementAt(Stack.size()-1);
                    WriteLine(Stack.size(), "fimenquanto");                
                }
                if (f.GetIfFalse()!=null)
                    CalcCodeWithoutParse(f.GetIfFalse());
                return;
            
            }else{            
                if (f.GetIfTrue()!=null){
                    WriteLine(Stack.size(), "se "+f.Text+" entao");
                    Stack.add(f);
                    CalcCodeWithoutParse(f.GetIfTrue());
                    return;
                }
            }
        
        }
        
        return;    
    }
    
    
}
