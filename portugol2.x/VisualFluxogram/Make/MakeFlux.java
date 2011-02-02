/*
 * MakeFlux.java
 *
 * Created on 24 de Junho de 2006, 21:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package VisualFluxogram.Make;


import Portugol.Language.Make.Fluxogram;
import Portugol.Language.Make.NodeInstruction;
import Portugol.Language.Parse.Keyword;
import Portugol.Language.Utils.IteratorString;
import Portugol.Language.Utils.LanguageException;
import Portugol.Language.Utils.Values;
import VisualFluxogram.Patterns.Conexao;
import VisualFluxogram.Patterns.Decisao;
import VisualFluxogram.Patterns.Escrita;
import VisualFluxogram.Patterns.Forma;
import VisualFluxogram.Patterns.Leitura;
import VisualFluxogram.Patterns.Processo;
import VisualFluxogram.Patterns.Terminador;
import java.awt.Point;
import java.util.Vector;

/**
 *
 * @author Eduardo
 */
public class MakeFlux {
    
    private Vector Formas = new Vector();
    private Vector Stack=new Vector();
    private Vector Stack2=new Vector();
    private int Horiz=200;
    private int Vert=200;
    private boolean sidetrue=false;
    private double []values=new double[255];
    private int values_length=1;
    private int FinalX=0;
    
    /** Creates a new instance of MakeFlux */
    public MakeFlux(Vector AllForms){
        Formas = AllForms;
    }
    
    public Vector getFlux(){
        return Formas;
    }
    
    public void CalcFlowChart(String program) throws LanguageException{
        Formas.clear();
        Stack.clear();
        
        Fluxogram f = new Fluxogram(program);
        System.out.println(f.toString());
        NodeInstruction tmp=f.getStart();
        Vert=25;
        Horiz=GetLeftSize(tmp)*100;
        int right=GetRightSize(tmp)*100;
        
        //System.out.println("posicao de X inicio: "+Horiz+" -->figuras: "+Horiz/100);
        //System.out.println("posicao X total: "+(Horiz+right)+" -->figuras: "+((Horiz+right)/100));
        
        FinalX=Horiz+right+80;
        //Formas.add(new Processo(new Point(Horiz+(right)+80,Vert),"test-fimX:"+(Horiz+(GetRightSize(tmp)*100)+80),Color.BLUE));
        
        
        CalcFlux(tmp);
        //System.out.println("posicao de Y fim: "+Vert);
        //Formas.add(new Processo(new Point(Horiz,Vert),"test-fimY:"+Vert,Color.BLUE));
        //System.out.println("numero total de figuras processadas: "+Formas.size());
    }
    
    public int GetHorizSize(){
        return FinalX;
    }
    public int GetVertSize(){
        return Vert;
    }
    
    private boolean isDefineDefault(NodeInstruction n){
        if (n.GetType()==Keyword.DEFINIR){
            //fazer uma atribuição com o valor da declaração
            //-----------------------------------------------
            IteratorString it = new IteratorString(n.GetText());
            String var = it.current() ;it.next() ; // Variavel
            String tipo = it.current() ;it.next() ; // Variavel
            String nome = it.current() ;it.next(); // tipo de dados
            String oper = it.current() ;it.next(); // operador
            String valor =  it.getUnprocessedString();
            //-------------------------------------------------------------
            // Se for o valor por defeito
            if(  Values.getDefault(tipo).equals(valor))
                return true;
            else
                return false;
        }
        return false;
    }
    
    private void CalcFlux(NodeInstruction n){
        
        //TERMINADOR////////////////////////////////////////////////////////////
        if (n.GetType()==Keyword.INICIO || n.GetType()==Keyword.FIM){
            
            //se for o inicio
            if (n.GetNext()!=null){
                n.SetForm(new Terminador(new Point(Horiz+10, Vert),true, null ) );
                Vert+=60;
                Formas.add(n.GetForm());
                CalcFlux( JumpNext(n.GetNext()) );
                //se for o fim
            }else{
                n.SetForm(new Terminador(new Point(Horiz+10, Vert),false, null ) );
                Vert+=60;
                Formas.add(n.GetForm());
                return;
            }
            //calcular conector do inicio depois de calcular as formas sguintes
            n.GetForm().CreateNextConector( JumpNext(n.GetNext()).GetForm(), Forma.SECOND, Forma.FIRST);
            return;
        }
        //DEFINIR  VARIAVEIS//////////////////////////////////////////////////////////////
        if (n.GetType()==Keyword.DEFINIR){
            //fazer uma atribuição com o valor da declaração
            //-----------------------------------------------
            IteratorString it = new IteratorString(n.GetText());
            String var = it.current() ;it.next() ; // Variavel
            String tipo = it.current() ;it.next() ; // Variavel
            String nome = it.current() ;it.next(); // tipo de dados
            String oper = it.current() ;it.next(); // operador
            String valor =  it.getUnprocessedString();
            //-------------------------------------------------------------
            String atr = nome + " " + oper + " " + valor ;
            n.SetForm(new Processo(new Point(Horiz+10, Vert), atr, null ) );
            Vert+=60;
            
            if ( !Stack.isEmpty() && JumpNext(n.GetNext())==Stack.lastElement() ){
                Stack.add(n);
                return;
            }
            
            CalcFlux( JumpNext(n.GetNext()) );
            n.GetForm().CreateNextConector( JumpNext(n.GetNext()).GetForm(), Forma.SECOND, Forma.FIRST);
            Formas.add(n.GetForm());
            return;
            
        }
        
        
        //ENQUANTO//////////////////////////////////////////////////////////////
        if (n.GetType()==Keyword.ENQUANTO){
            
            n.SetForm(new Decisao(new Point(Horiz, Vert), n.GetText(), null ) );
            //n.SetForm(new Decisao(new Point(Horiz, Vert), (new String (""+n.GetPositionX())).substring(0,(new String (""+n.GetPositionX())).length()>5?5:(new String (""+n.GetPositionX())).length())+", "+(int)n.GetPositionY(), null ) );
            Vert+=60;
            
            //calcular quantas formas existe no lado iftrue
            Horiz+=( 100*GetIfTrueSize(n) );
            
            //guardar figura de saida do ciclo
            Stack.add(n);
            //calcular ciclo
            CalcFlux( JumpNext(n.GetIfTrue()) );
            //atribuir conector a figura do lado iftrue
            n.GetForm().CreateIfTrueConector( JumpNext(n.GetIfTrue()).GetForm(), Forma.FOURTH, Forma.FIRST);
            //receber figura de finalizacao do ciclo
            NodeInstruction last=(NodeInstruction)Stack.lastElement();
            
            //se figura de finalizacao for de 1 repete(figura decisao)
            if (last.GetType()==Keyword.ATE){
                last.GetForm().CreateIfTrueConector( n.GetForm(), Forma.SECOND, Forma.SECOND);
            }else if (last.GetType()==Keyword.FAZENQUANTO){
                last.GetForm().CreateIfFalseConector( n.GetForm(), Forma.SECOND, Forma.SECOND);
            }else{
                last.GetForm().CreateNextConector( n.GetForm(), Forma.SECOND, Forma.SECOND);
                Formas.add(last.GetForm());
            }
            
            //apagar figura de finalizacao
            Stack.remove(Stack.size()-1);
            //recuperar posicao original antes de comecar o enquanto
            Horiz=(int)n.GetForm().TopLeft.x;
            //dar uma margem de espaco no fim do enquanto
            Vert+=40;
            
            //guardar figura criada
            Formas.add(n.GetForm());
            //remover figura de saida do ciclo
            Stack.remove(Stack.size()-1);
            //se a figura seguinte for de terminacao de condicao
            if ( !Stack.isEmpty() && JumpNext(n.GetIfFalse())==Stack.lastElement() ){
                Stack.add(n.GetIfFalse());
                return;
            }
            //calcular iffalse do enquanto
            CalcFlux( JumpNext(n.GetIfFalse()) );
            //calcular conector do iffalse
            n.GetForm().CreateIfFalseConector( JumpNext(n.GetIfFalse()).GetForm(), Forma.THIRD, Forma.FIRST);
            return;
            
        }
        
        //SE////////////////////////////////////////////////////////////////////
        if (n.GetType()==Keyword.SE){
            
            n.SetForm(new Decisao(new Point(Horiz, Vert), n.GetText(), null ) );
            //n.SetForm(new Decisao(new Point(Horiz, Vert), (new String (""+n.GetPositionX())).substring(0,(new String (""+n.GetPositionX())).length()>5?5:(new String (""+n.GetPositionX())).length())+", "+(int)n.GetPositionY(), null ) );
            Vert+=60;
            
            //calcular quantas formas existe no lado iftrue
            Horiz+=( 100*GetIfTrueSize(n) );
            
            //guardar figura de finalizacao do if
            Stack.add(n.GetNext());
            //calcular parte verdadeira
            CalcFlux( JumpNext(n.GetIfTrue()) );
            //calcular conector de iftrue
            n.GetForm().CreateIfTrueConector( JumpNext(n.GetIfTrue()).GetForm(), Forma.FOURTH, Forma.FIRST);
            //receber figura antes da finalizacao do if da parte verdadeira
            NodeInstruction last=(NodeInstruction)Stack.lastElement();
            //removela da stack
            Stack.remove(Stack.size()-1);
            
            //verificar se existe argumento na parte falsa do if
            if ( JumpNext(n.GetIfFalse())!=n.GetNext() ){
                //guardar o valor y da parte verdadeira
                int SaveVert=Vert;
                //recuperar posicao original antes de comecar o if
                Vert=(int)n.GetForm().TopLeft.y+60;
                //calcular quantas formas existe no lado iffalse
                Horiz=(int)n.GetForm().TopLeft.x-( 100*GetIfFalseSize(n) );
                //calcular parte falsa
                CalcFlux( JumpNext(n.GetIfFalse()) );
                //calcular conector de iffalse
                n.GetForm().CreateIfFalseConector( JumpNext(n.GetIfFalse()).GetForm(), Forma.THIRD, Forma.FIRST);
                //guardar maior valor que ocupa o if
                if (SaveVert>Vert)
                    Vert=SaveVert;
            }
            
            //recuperar posicao original antes de comecar o if
            Horiz=(int)n.GetForm().TopLeft.x;
            
            //criar figura de fim do if
            n.GetNext().SetForm(new Conexao(new Point(Horiz+45, Vert), null ) );
            Vert+=60;
            
            
            //calcular conectores para a parte verdadeira
            //se figura de finalizacao for de 1 repete(figura decisao)
            if (last.GetType()==Keyword.ATE){
                last.GetForm().CreateIfTrueConector( n.GetNext().GetForm(), Forma.SECOND, Forma.FOURTH);
            }else if (last.GetType()==Keyword.FAZENQUANTO){
                last.GetForm().CreateIfFalseConector( n.GetNext().GetForm(), Forma.SECOND, Forma.FOURTH);
            }else{
                last.GetForm().CreateNextConector( n.GetNext().GetForm(), Forma.SECOND, Forma.FOURTH);
                Formas.add(last.GetForm());
            }
            
            //calcular conectores para a parte falsa, se existir
            if ( JumpNext(n.GetIfFalse())!=n.GetNext()){
                //receber figura antes da finalizacao do if da parte falsa
                last=(NodeInstruction)Stack.lastElement();
                Stack.remove(Stack.size()-1);
                
                //se figura de finalizacao for de 1 repete(figura decisao)
                if (last.GetType()==Keyword.ATE){
                    last.GetForm().CreateIfTrueConector( n.GetNext().GetForm(), Forma.SECOND, Forma.THIRD);
                }else if (last.GetType()==Keyword.FAZENQUANTO){
                    last.GetForm().CreateIfFalseConector( n.GetNext().GetForm(), Forma.SECOND, Forma.THIRD);
                }else{
                    last.GetForm().CreateNextConector( n.GetNext().GetForm(), Forma.SECOND, Forma.THIRD);
                    Formas.add(last.GetForm());
                }
                
                //criar conector directo
            }else{
                n.GetForm().CreateIfFalseConector( n.GetIfFalse().GetForm(), Forma.THIRD, Forma.THIRD);
            }
            
            //guardar figura if
            Formas.add(n.GetForm());
            //remover figura de terminacao if
            Stack.remove(Stack.size()-1);
            //se a figura seguinte ao if for de terminacao de condicao
            if (!Stack.isEmpty() && JumpNext(n.GetNext().GetNext())==Stack.lastElement()){
                Stack.add(n.GetNext());
                return;
            }
            //calcular depois de fim de if
            CalcFlux( JumpNext(n.GetNext().GetNext()) );
            //calcular conector seguinte
            n.GetNext().GetForm().CreateNextConector( JumpNext(n.GetNext().GetNext()).GetForm(), Forma.SECOND, Forma.FIRST);
            //guardar terminador de if
            Formas.add(n.GetNext().GetForm());
            return;
        }
        
        //CONEXAO///////////////////////////////////////////////////////////////
        if (n.GetType()==Keyword.CONECTOR){
            
            n.SetForm(new Conexao(new Point(Horiz+45, Vert), null ) );
            Vert+=60;
            
            //se a figura seguinte for de terminacao de condicao
            if ( !Stack.isEmpty() && JumpNext(n.GetNext())==Stack.lastElement() ){
                Stack.add(n);
                return;
            }
            //calcular figura seguinte
            CalcFlux( JumpNext(n.GetNext()) );
            //calcular conector seguinte
            n.GetForm().CreateNextConector( JumpNext(n.GetNext()).GetForm(), Forma.SECOND, Forma.FIRST);
            //guardar figura
            Formas.add(n.GetForm());
            return;
        }
        
        //LEITURA///////////////////////////////////////////////////////////////
        if (n.GetType()==Keyword.LER){
            
            n.SetForm(new Leitura(new Point(Horiz+10, Vert), n.GetText().substring(4), null ) );
            //n.SetForm(new Escrita(new Point(Horiz+10, Vert), /*n.GetText().substring(9)*/(new String (""+n.GetPositionX())).substring(0,(new String (""+n.GetPositionX())).length()>5?5:(new String (""+n.GetPositionX())).length())+", "+(int)n.GetPositionY(), null ) );
            Vert+=60;
            
            if ( !Stack.isEmpty() && JumpNext(n.GetNext())==Stack.lastElement() ){
                Stack.add(n);
                return;
            }
            
            CalcFlux( JumpNext(n.GetNext()) );
            n.GetForm().CreateNextConector( JumpNext(n.GetNext()).GetForm(), Forma.SECOND, Forma.FIRST);
            Formas.add(n.GetForm());
            return;
        }
        
        //ESCRITA///////////////////////////////////////////////////////////////
        if (n.GetType()==Keyword.ESCREVER){
            
            n.SetForm(new Escrita(new Point(Horiz+10, Vert), n.GetText().substring(9), null ) );
            //n.SetForm(new Escrita(new Point(Horiz+10, Vert), /*n.GetText().substring(9)*/(new String (""+n.GetPositionX())).substring(0,(new String (""+n.GetPositionX())).length()>5?5:(new String (""+n.GetPositionX())).length())+", "+(int)n.GetPositionY(), null ) );
            Vert+=60;
            
            if ( !Stack.isEmpty() && JumpNext(n.GetNext())==Stack.lastElement() ){
                Stack.add(n);
                return;
            }
            
            CalcFlux( JumpNext(n.GetNext()) );
            n.GetForm().CreateNextConector( JumpNext(n.GetNext()).GetForm(), Forma.SECOND, Forma.FIRST);
            Formas.add(n.GetForm());
            return;
        }
        
        //PROCESSO//////////////////////////////////////////////////////////////
        if (n.GetType()==Keyword.CALCULAR){
            
            n.SetForm(new Processo(new Point(Horiz+10, Vert), n.GetText(), null ) );
            //n.SetForm(new Processo(new Point(Horiz+10, Vert), (new String (""+n.GetPositionX())).substring(0,(new String (""+n.GetPositionX())).length()>5?5:(new String (""+n.GetPositionX())).length())+", "+(int)n.GetPositionY(), null ) );
            Vert+=60;
            
            if ( !Stack.isEmpty() && JumpNext(n.GetNext())==Stack.lastElement() ){
                Stack.add(n);
                return;
            }
            
            CalcFlux( JumpNext(n.GetNext()) );
            n.GetForm().CreateNextConector( JumpNext(n.GetNext()).GetForm(), Forma.SECOND, Forma.FIRST);
            Formas.add(n.GetForm());
            return;
        }
        
        //FIM REPETE////////////////////////////////////////////////////////////
        if (n.GetType()==Keyword.ATE){
            
            n.SetForm(new Decisao(new Point((int)((NodeInstruction)Stack.lastElement()).GetForm().TopLeft.x-45, Vert), n.GetText(), null ) );
            Vert+=100;
            
            if ( !Stack.isEmpty() && n.GetIfFalse()==Stack.lastElement() ){
                Stack.add(n);
                return;
            }
        }
        
        //REPETE////////////////////////////////////////////////////////////////
        if (n.GetType()==Keyword.REPETE){
            
            n.SetForm(new Conexao(new Point(Horiz+45, Vert), null ) );
            Vert+=60;
            
            //calcular quantas formas existe no repete
            Horiz-=( 100*GetRepeteSize(n) );
            //guardar figura de inicio
            Stack.add(n);
            //comecar calculo do repete
            CalcFlux( JumpNext(n.GetNext()) );
            //calcular conector
            n.GetForm().CreateNextConector( JumpNext(n.GetNext()).GetForm(), Forma.SECOND, Forma.FIRST);
            //guardar figura
            Formas.add(n.GetForm());
            
            //recuperar posicao original antes de comecar o if
            Horiz=(int)n.GetForm().TopLeft.x-45;
            
            //receber ultima figura de finalizacao do repete
            NodeInstruction last=(NodeInstruction)Stack.lastElement();
            //removela da stack
            Stack.remove(Stack.size()-1);
            
            //unir fim de repete com a figura de conexao
            last.GetForm().CreateIfFalseConector( n.GetForm(), Forma.FOURTH, Forma.FOURTH);
            
            //guardar figura de fim de repete
            Formas.add(last.GetForm());
            //remover figura de inicio de repete
            Stack.remove(Stack.size()-1);
            //verificar se a figura seguinte é de terminacao
            if ( !Stack.isEmpty() && JumpNext(last.GetNext())==Stack.lastElement() ){
                Stack.add(last);
                return;
            }
            //calcular depois do repete
            CalcFlux( JumpNext(last.GetIfTrue()) );
            last.GetForm().CreateIfTrueConector( JumpNext(last.GetIfTrue()).GetForm(), Forma.SECOND, Forma.FIRST);
            return;
        }
        
        //FIM FAZ///////////////////////////////////////////////////////////////
        if (n.GetType()==Keyword.FAZENQUANTO){
            
            n.SetForm(new Decisao(new Point((int)((NodeInstruction)Stack.lastElement()).GetForm().TopLeft.x-45, Vert), n.GetText(), null ) );
            Vert+=100;
            
            if (!Stack.isEmpty() && n.GetIfTrue()==Stack.lastElement()){
                Stack.add(n);
                return;
            }
        }
        
        //FAZ///////////////////////////////////////////////////////////////////
        if (n.GetType()==Keyword.FAZ){
            
            n.SetForm(new Conexao(new Point(Horiz+45, Vert), null ) );
            Vert+=60;
            
            //calcular quantas formas existe no repete
            Horiz+=( 100*GetFazSize(n) );
            //guardar figura de inicio
            Stack.add(n);
            //comecar calculo do repete
            CalcFlux( JumpNext(n.GetNext()) );
            //calcular conector
            n.GetForm().CreateNextConector( JumpNext(n.GetNext()).GetForm(), Forma.SECOND, Forma.FIRST);
            //guardar figura
            Formas.add( n.GetForm() );
            
            //recuperar posicao original antes de comecar o if
            Horiz=(int)n.GetForm().TopLeft.x-45;
            
            //receber ultima figura de finalizacao do repete
            NodeInstruction last=(NodeInstruction)Stack.lastElement();
            //removela da stack
            Stack.remove(Stack.size()-1);
            
            //unir fim de repete com a figura de conexao
            last.GetForm().CreateIfTrueConector( n.GetForm(), Forma.THIRD, Forma.THIRD);
            
            //guardar figura de fim de repete
            Formas.add(last.GetForm());
            //remover figura de inicio de repete
            Stack.remove(Stack.size()-1);
            //verificar se a figura seguinte é de terminacao
            if ( !Stack.isEmpty() && JumpNext(last.GetNext())==Stack.lastElement() ){
                Stack.add(last);
                return;
            }
            //calcular depois do repete
            CalcFlux( JumpNext(last.GetIfFalse()) );
            last.GetForm().CreateIfFalseConector( JumpNext(last.GetIfFalse()).GetForm(), Forma.SECOND, Forma.FIRST);
            return;
        }
        
        return;
    }
    
    //saltar figuras como passo e defenir com valores por defeito
    private NodeInstruction JumpNext(NodeInstruction n){
        // se for o passo ou se for a definição com o valor por defeito
        while (n.GetType()==Keyword.PASSO  || ( n.GetType()==Keyword.DEFINIR && this.isDefineDefault(n)))        
            n=n.GetNext();
        
        return n;
    }
    
    /*private NodeInstruction GetRepeteEnd(NodeInstruction n){
     
        while (n.GetIfFalse()!=n){
            if (n.GetType()==Keyword.ENQUANTO)
                n=n.GetIfFalse();
            else
                n=n.GetNext();
        }
        return n;
    }*/
    
    
    /*private int GetVertSize(NodeInstruction ini){
        while (ini.GetNext()!=null){
            ini=ini.GetNext();
        }
        return (int)ini.GetPositionY();
    }*/
    
    
    /*private void GetMaxSize(NodeInstruction ini){
     
        if (!Stack2.isEmpty() && ini==Stack2.lastElement())
            return;
     
        if (Stack2.isEmpty()){
            if (sidetrue && ini.GetPositionX()<0.0)
                return;
            if (!sidetrue && ini.GetPositionX()>0.0)
                return;
        }
     
        if ( (sidetrue && ini.GetPositionX()>=0.0) || (!sidetrue && ini.GetPositionX()<=0.0)  ){
            boolean contains=false;
            for (int i=0;i<values_length;i++)
                if (values[i]==ini.GetPositionX()){
                    contains=true;
                    break;
                }
            if (!contains)
                values[values_length++]=ini.GetPositionX();
        }
     
        if (ini.GetType()==Keyword.SE){
     
            if (ini.GetIfTrue()!=null){
                Stack2.add(ini.GetNext());
                GetMaxSize(ini.GetIfTrue());
            }
            if (ini.GetIfFalse()!=null)
                GetMaxSize(ini.GetIfFalse());
            Stack2.remove(Stack2.size()-1);
            if (ini.GetNext()!=null)
                GetMaxSize(ini.GetNext());
            return;
     
        }else if (ini.GetType()==Keyword.ENQUANTO){
     
            if (ini.GetIfTrue()!=null){
                Stack2.add(ini);
                GetMaxSize(ini.GetIfTrue());
                Stack2.remove(Stack2.size()-1);
            }
            if (ini.GetIfFalse()!=null)
                GetMaxSize(ini.GetIfFalse());
            return;
     
        }else{
     
            if (!Stack2.isEmpty())
                if ( ini.GetType()==Keyword.FAZENQUANTO || ini.GetType()==Keyword.ATE )
                //if ( (ini.GetType()==Keyword.FAZENQUANTO && ini.GetIfTrue()==Stack2.lastElement()) ||
                //     (ini.GetType()==Keyword.ATE && ini.GetIfFalse()==Stack2.lastElement()) )
                    Stack2.remove(Stack2.size()-1);
            if (ini.GetType()==Keyword.FAZ || ini.GetType()==Keyword.REPETE)
                Stack2.add(ini);
     
     
            if (ini.GetNext()!=null)
                GetMaxSize(ini.GetNext());
            return;
        }
    }*/
    
    private int GetLeftSize(NodeInstruction ini){
        sidetrue=true;
        values_length=0;
        values[values_length++]=-2;
        values[values_length++]=ini.GetPositionX();
        Stack2.clear();
        GetSize(ini);
        return values_length-1;
        
        /*sidetrue=false;
        values_length=0;
        Stack2.clear();
        GetMaxSize(ini);
        return values_length;*/
    }
    private int GetRightSize(NodeInstruction ini){
        sidetrue=false;
        values_length=0;
        values[values_length++]=2;
        values[values_length++]=ini.GetPositionX();
        Stack2.clear();
        GetSize(ini);
        return values_length-2;
        
        /*sidetrue=true;
        values_length=0;
        Stack2.clear();
        GetMaxSize(ini);
        return values_length;*/
    }
    
    private int GetIfTrueSize(NodeInstruction se){
        sidetrue=true;
        values_length=0;
        values[values_length++]=se.GetPositionX();
        values[values_length++]=se.GetIfTrue().GetPositionX();
        Stack2.clear();
        GetSize(se.GetIfTrue());
        return values_length-1;
    }
    
    private int GetIfFalseSize(NodeInstruction se){
        sidetrue=false;
        values_length=0;
        values[values_length++]=se.GetPositionX();
        values[values_length++]=se.GetIfFalse().GetPositionX();
        Stack2.clear();
        GetSize(se.GetIfFalse());
        return values_length-1;
    }
    
    private int GetRepeteSize(NodeInstruction repete){
        sidetrue=false;
        values_length=0;
        values[values_length++]=repete.GetPositionX();
        if (values[0]!=repete.GetNext().GetPositionX())
            values[values_length++]=repete.GetNext().GetPositionX();
        Stack2.clear();
        GetSize(repete.GetNext());
        return values_length-1;
    }
    private int GetFazSize(NodeInstruction faz){
        sidetrue=true;
        values_length=0;
        values[values_length++]=faz.GetPositionX();
        if (values[0]!=faz.GetNext().GetPositionX())
            values[values_length++]=faz.GetNext().GetPositionX();
        Stack2.clear();
        GetSize(faz.GetNext());
        return values_length-1;
    }
    
    
    //calcular figuras entre as posicoes values[0] e values[1]
    private void GetSize(NodeInstruction ini){
        
        //verifica se se chegou ao final do ciclo
        if (!Stack2.isEmpty() && ini==Stack2.lastElement())
            return;
        
        //verificar se a posicao encontrada nao ultrapassa os limites
        //sidetrue usado para saber se se está a trabalhar com posicoes >0 uo <0
        boolean save_pos=true;
        if (sidetrue && (ini.GetPositionX()>values[1] || ini.GetPositionX()<=values[0]))
            save_pos=false;
        if (!sidetrue && (ini.GetPositionX()<values[1] || ini.GetPositionX()>=values[0]))
            save_pos=false;
        
        //guardar nova posicao
        if (save_pos){
            boolean contains=false;
            for (int i=0;i<values_length;i++)
                if (values[i]==ini.GetPositionX()){
                contains=true;
                break;
                }
            if (!contains)
                values[values_length++]=ini.GetPositionX();
        }else
            //se nao esta dentro de um repete ou faz entao sai
            if (Stack2.isEmpty())
                return;
        
        if (ini.GetType()==Keyword.SE){
            
            Stack2.add(ini.GetNext());
            if (ini.GetIfTrue()!=null)
                GetSize(ini.GetIfTrue());
            if (ini.GetIfFalse()!=null)
                GetSize(ini.GetIfFalse());
            Stack2.remove(Stack2.size()-1);
            
            if (ini.GetNext()!=null)
                GetSize(ini.GetNext());
            return;
            
        }else if (ini.GetType()==Keyword.ENQUANTO){
            
            if (ini.GetIfTrue()!=null){
                Stack2.add(ini);
                GetSize(ini.GetIfTrue());
                Stack2.remove(Stack2.size()-1);
            }
            if (ini.GetIfFalse()!=null)
                GetSize(ini.GetIfFalse());
            return;
            
        }else{
            
            if ( ini.GetType()==Keyword.FAZENQUANTO || ini.GetType()==Keyword.ATE )
                Stack2.remove(Stack2.size()-1);
            if (ini.GetType()==Keyword.FAZ || ini.GetType()==Keyword.REPETE)
                Stack2.add(ini);
            
            if (ini.GetNext()!=null)
                GetSize(ini.GetNext());
            return;
        }
        
    }
    
}
