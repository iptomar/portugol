/*
 * ParseStructure.java
 *
 * Created on 1 de Julho de 2006, 18:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package VisualFluxogram.Parser;

import Portugol.Language.Parse.Symbol;
import VisualFluxogram.*;
import VisualFluxogram.Patterns.Conexao;
import VisualFluxogram.Patterns.Decisao;
import VisualFluxogram.Patterns.Escrita;
import VisualFluxogram.Patterns.Forma;
import VisualFluxogram.Patterns.Leitura;
import VisualFluxogram.Patterns.Processo;
import VisualFluxogram.Patterns.Terminador;
import java.awt.Color;
import java.awt.List;
import java.util.Vector;

/**
 *
 * @author Eduardo
 */
public class ParseStructure {
    
    private Vector Formas=new Vector();
    private List ErrList;
    private Vector ErrForms;
    
    private boolean VerifyText;    
    
    private Vector ParseForms=new Vector();
    private Vector VForms=new Vector();
            
    private Vector Stack=new Vector();
    
    private Vector Ifs=new Vector();
    private Vector ConnectionCycles=new Vector();    
    
    public static Vector IntersectedForms=new Vector();
    public static Forma dest;
    public static Forma stop;
    
    private int IfPos=0;    
    
    private ParseText parsetext;
    
    /** Creates a new instance of ParseStructure */
    public ParseStructure(Vector AllForms, List list, Vector Errors){
        Formas = AllForms;
        ErrList = list;
        ErrForms = Errors;
        
        parsetext = new ParseText(list, Errors);
    }
    
    public Forma ParseFlowChart(boolean VerifyText) throws Exception{
        
        ResetVarsForParse();
        
        Forma inicio = GetInicialFigure(Formas);
        Forma fim = ParseStructure.GetFinalFigure(Formas);
        
        this.VerifyText = VerifyText;      
        ComplexParse(inicio);
        
        if ( !Stack.isEmpty() || !ErrForms.isEmpty() )
            throw new Exception("O FLUXOGRAMA NAO ESTA BEM CONTRUIDO");
        
        
        GetNoExecutableForms(inicio, fim);        
        
        return inicio;
    }
    
    public Forma ParseForExecute(boolean VerifyText) throws Exception{
        
        Forma inicio = GetInicialFigure(Formas);
        /*Forma fim = GetFinalFigure(Formas);
        
        
        this.VerifyText = VerifyText;
        
        ResetVarsForParse();
        SimpleParse(inicio);
        
        if ( !Stack.isEmpty() || !ErrForms.isEmpty() )
            throw new Exception("IMPOSSIVEL CRIAR CODIGO, FLUXOGRAMA MAL CONTRUIDO");
        */
        
        //ResetVarsForParse();
        //VerifyText = false;
        //ComplexParse(inicio);
        //if ( !Stack.isEmpty() || !ErrForms.isEmpty() )
        //    return false;
        return inicio;
    }
    
    public Vector GetVariables(){
        return parsetext.GetVariables();
    }
    
    public Vector GetIfs(){
        return Ifs;
    }
    
    public Vector GetConnectionCycles(){
        return ConnectionCycles;
    }    
    
    private void ResetVarsForParse(){
        IfPos=0;        
        ConnectionCycles.clear();
        Ifs.clear();
        
        Stack.clear();
        ParseForms.clear();
        //limpar erros encontrados anteriormente
        ErrList.removeAll();
        ErrForms.clear();
    }
    
    private void AddError(Forma f, String err){
        ErrList.add(err);
        ErrForms.add(f);
    }
    
    //orig-->figura onde começa a procura
    //dest-->figura igual para sair do ciclo
    //stop-->se é usada a figura extform para parar a pesquisa
    //funcao usada para inicializar todas as variaveis usadas na pesquisa de interseccoes
    public static boolean Intersects(Forma orig, Forma dest2, Forma stop2){
        IntersectedForms.clear();
        dest=dest2;
        stop=stop2;
        return VerifyIntersection(orig);
    }
    
    //verificar interseccao de f com frm e tentando evitar figura stopfrm
    //ConectedForms-->usado para garantir que nao ocorrem ciclos infinitos
    public static boolean VerifyIntersection(Forma f){
        
        if (f==stop)
            return false;               
        if (f==dest)
            return true;        
        
        if (!(f instanceof Decisao)){
            
            if (IntersectedForms.contains(f))
                return false;                
            if (f instanceof Conexao)
                IntersectedForms.add(f);
            
            if (f.GetNext()!=null)
                return VerifyIntersection(f.GetNext());
        }else{
            
            if (IntersectedForms.contains(f))
                return false;           
            IntersectedForms.add(f);
            
            
            if (f.GetIfTrue()!=null && f.GetIfFalse()!=null)
                return VerifyIntersection(f.GetIfTrue()) || VerifyIntersection(f.GetIfFalse());
            else if(f.GetIfTrue()!=null)
                return VerifyIntersection(f.GetIfTrue());
            else if (f.GetIfFalse()!=null)
                return VerifyIntersection(f.GetIfFalse());
        }
        return false;
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    //verificar a figura onde os conectores de falso e verdadeiro se intersectam
    //se a condicao for um if estes intersectam-se numa conexao
    
    //a condicao if a comparar encontra-se guardada na ultima pos de ErrForms
    //VForms-> guardar figuras processadas para evitar ciclos
    private Forma IfIntersection(Forma f){
        
        //verificar se a interseccao nao está as figuras processadas no parsing
        if (f!=ParseForms.lastElement() && ParseForms.contains(f) )
            return null;
        //saltar este tipo de figuras onde é imposivel existir interseccao
        if (!(f instanceof Decisao) && !(f instanceof Conexao))
            if (f.GetNext()!=null)
                return IfIntersection(f.GetNext());
            else
                return null;
        //verificar se foi encontrada a figura de interseccao
        //usada a codincao de if como figura de stop para garantir que nao sao
        //usados ciclos na procura da figura de conexao do if
        else if (Intersects(((Forma)ParseForms.lastElement()).GetIfFalse(),f, (Forma)ParseForms.lastElement())
             && Intersects(((Forma)ParseForms.lastElement()).GetIfTrue(),f, (Forma)ParseForms.lastElement()) )
                return f;
        
        else if (f instanceof Conexao){
            
            if (VForms.contains(f))
                return null;
            VForms.add(f);
            
            if (f.GetNext()!=null)
                return IfIntersection(f.GetNext());
            else
                return null;
            
        }else if (f instanceof Decisao){
            
            if (VForms.contains(f))
                return null;
            VForms.add(f);
            
            //segue pelo verdadeiro se for um repete
            if ( (f.GetIfFalse() instanceof Conexao) && VForms.contains(f.GetIfFalse()) )//Intersects(f.GetIfFalse(),f,null) )
                if (f.GetIfTrue()!=null)
                    return IfIntersection(f.GetIfTrue());
                else
                    return null;
            //senao segue sempre pela parte falsa
            else
                if (f.GetIfFalse()!=null)
                    return IfIntersection(f.GetIfFalse());
                else
                    return null;  
        }
        
        return null;
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    //ParseForms-> usado para garantir que nao ocorrem ciclos infinitos
    //Stack->usado para calculo de condicoes
    private void ComplexParse(Forma f){
        
        //fexar a condicao aberta
        //if (!Stack.isEmpty() && Stack.lastElement()==f)
        if (Stack.contains(f))
            return;
        
        //f.DrawShadow(Fluxogramas.DrawArea.getGraphics(),Color.green);
        //for (int i=0;i<49999999;i++){}
        
        //se o fluxograma nao terminar testa proxima figura
        if ( !(f instanceof Decisao) ){
            
            if (f instanceof Conexao){
                ParseForms.add(f);
                for(int i=0; i<Formas.size(); i++)
                    if ( ( ((Forma)(Formas.get(i))).GetIfFalse()==f || ((Forma)(Formas.get(i))).GetIfTrue()==f) && 
                        !ParseForms.contains(Formas.get(i)) )
                        Stack.add(f);
                
                
            }else if (f instanceof Leitura){
                if (VerifyText)
                    parsetext.VerifyReadText(f);
            }else if (f instanceof Escrita){
                if (VerifyText)
                    parsetext.VerifyWriteText(f);
            }else if (f instanceof Processo){
                if (VerifyText)
                    parsetext.VerifyProcessText(f);
            }
            
            
            if (f.GetNext()!=null){
                if (!ParseForms.contains(f.GetNext()) || f instanceof Conexao || 
                    (!Stack.isEmpty() && f.GetNext()==Stack.lastElement()) )
                    ComplexParse(f.GetNext());
                else{
                    AddError(f, "Erro: existem ciclos infinitos criados");
                    return;
                }
            }else if ( !(f instanceof Terminador) || !f.Text.equalsIgnoreCase("Fim") ){
                AddError(f, "Erro: teminacao de fluxograma inesperada");
                return;
            }else
                return;        
            
        }else if (f instanceof Decisao){
            if (VerifyText)
                parsetext.VerifyConditionText(f);
            ParseForms.add(f);
            
            //verificar se a figura tem todos os conectores
            if (f.GetIfFalse()==null || f.GetIfTrue()==null){
                AddError(f, "Erro: teminacao de fluxograma inesperada");
                return;
            }         
            
            
                //inicializar variaveis a usar em IfIntersection
                VForms.clear();
                //calcular figura de interseccao da condicao
                Forma intersect_form=IfIntersection(f.GetIfFalse());
            
            //se a condicao fexar com outra figura de decisao
            if ( intersect_form instanceof Decisao && !ParseForms.contains(intersect_form)){
                AddError(f, "Erro: a condicao SE nao está a fechar com uma figura de conexao");
                //continua o parser como se fosse uma if a fechar numa figura de conexao
                
                //guardar figura de finalizacao do if
                Stack.add(intersect_form);
                ComplexParse(f.GetIfTrue());
                //a parte verdadeira do if nao esta a fechar com a figura correcta
                if (Stack.lastElement()!=intersect_form){
                    AddError((Forma)Stack.lastElement(), "Erro: figura contem um REPETE/FAZ a bloquear a condicao SE");
                    return;
                }
                ComplexParse(f.GetIfFalse());
                //a parte falsa do if nao esta a fechar com a figura correcta
                if (Stack.lastElement()!=intersect_form){
                    AddError((Forma)Stack.lastElement(), "Erro: figura contem um REPETE/FAZ a bloquear a condicao SE");
                    return;
                }
                Stack.removeElementAt(Stack.size()-1);
                ComplexParse(intersect_form);
                return;
            
            //se a condicao de if terminar de forma adquada
            }else if (intersect_form instanceof Conexao){
                    
                //verifica se existe algum ciclo dentro do if
                if ( Intersects(f.GetIfTrue(),f, intersect_form) || Intersects(f.GetIfFalse(),f, intersect_form) ){
                    AddError(f, "Erro: a condicao SE/ENQUANTO está a ser usada de forma incorrecta");
                    return;
                //verificar se o if nao fecha logo directamente com a conexao
                }else if (f.GetIfTrue()==intersect_form)
                    AddError(f, "Erro: a condicao SE nao tem argumentos");
                
                //verifica se nao existe mais nenhum if para a mesma figura de interseccao
                if (Ifs.contains(intersect_form))
                    AddError(intersect_form, "Erro: figura de conexao com mais de um SE");
                else{
                    Ifs.add(f);
                    Ifs.add(intersect_form);
                }                
                //se for uma condicao if correcta
                
                //guardar figura de finalizacao do if
                Stack.add(intersect_form);
                
                int size_ini = 0;
                boolean isInside = false;
                if (VerifyText){
                    //saber que foi aberto mais um if
                    IfPos++;
                    //verificar se ja nao tinha sido aberto um ciclo
                    isInside = parsetext.VerifyOpenCondicion();
                    //guardar posicao onde vai ser comecada a adicao de variveis encontradas dentro do if
                    size_ini = parsetext.getUnInitializedVarsSize();
                }
                    
                ComplexParse(f.GetIfTrue());
                //a parte verdadeira do if nao esta a fechar com a figura correcta                
                if (Stack.lastElement()!=intersect_form){
                    AddError((Forma)Stack.lastElement(), "Erro: figura contem um REPETE/FAZ a bloquear a condicao SE");
                    return;
                }
                
                int size_true = 0;
                if (VerifyText){
                    //adicionar variaveis que foram inicializadas
                    parsetext.AddInitializedVarsFromTrueSide(size_ini);
                    //sitio onde acabam as variaveis da parte verdadeira
                    //e onde comecam da parte falsa
                    size_true = parsetext.getUnInitializedVarsSize();                    
                }
                    
                ComplexParse(f.GetIfFalse());
                //a parte falsa do if nao esta a fechar com a figura correcta
                if (Stack.lastElement()!=intersect_form){
                    AddError((Forma)Stack.lastElement(), "Erro: figura contem um REPETE/FAZ a bloquear a condicao SE");
                    return;
                }
                Stack.removeElementAt(Stack.size()-1);
                
                if (VerifyText){
                    //saber que foi fexado mais um if
                    IfPos--;                
                    //adicionar variaveis que foram inicializadas
                    parsetext.AddInitializedVarsInPrevious(isInside, IfPos, size_ini, size_true);
                    if (!isInside)
                        IfPos=0;
                }
                
                ///////////
                ComplexParse(intersect_form);
                return;
                
            //confirmacao de que a figura nao é um if(ou é um enquanto ou um repete ou faz)
            }else{
                
                
                //se nao for um if um dos iftrue e iffalse tem de criar um ciclo
                if ( !Intersects(f.GetIfTrue(),f, null) && !Intersects(f.GetIfFalse(),f, null) ){
                    AddError(f, "Erro: a condicao nao esta ligada de forma correcta");
                    return;
                    
                
                }else if ( Intersects(f.GetIfTrue(),f, ParseForms.size()>1 ? (Forma)ParseForms.get(ParseForms.size()-2) : null)){
                    
                    //verificar se nao existe um repete ao mesmo tempo que um enquanto
                    if ( (f.GetIfFalse() instanceof Conexao) && ParseForms.contains(f.GetIfFalse())){
                        AddError(f, "Erro: existe mais de um tipo de ciclo nesta condicao");
                        return;
                    }
                    
                    //se for uma condicao enquanto correcta
                    Stack.add(f);
                    
                    boolean isInside = false;
                    int size_ini = 0;
                    if (VerifyText){                    
                        //verificar se ja nao tinha sido aberto um ciclo
                        isInside = parsetext.VerifyOpenCondicion();
                        //guardar posicao onde vai ser comecada a adicao de variveis encontradas dentro do if
                        size_ini = parsetext.getUnInitializedVarsSize();
                    }
                    
                    ComplexParse(f.GetIfTrue());
                    if (Stack.lastElement()!=f){
                        AddError((Forma)Stack.lastElement(), "Erro: figura contem um REPETE/FAZ a bloquear a condicao ENQUANTO");
                        return;
                    }
                    Stack.removeElementAt(Stack.size()-1);
                    
                    if (VerifyText){
                        //nao deixar usar todas as variaveis inicializadas dentro do enquanto
                        parsetext.CloseOpenedCondition(isInside, size_ini);
                    }
                    
                    ComplexParse(f.GetIfFalse());
                    return;
                   
                
                //verificar se o ciclo com o verdadeiro só pode pertence a um faz
                }else if (Intersects(f.GetIfTrue(), ParseForms.size()>1 ? (Forma)ParseForms.get(ParseForms.size()-2) : null, f.GetIfFalse())){
                    
                    //verificar se ligacao do verdadeiro está directamente ligada à conexao
                    if ( (f.GetIfTrue() instanceof Conexao) && ParseForms.contains(f.GetIfTrue())){
                        
                        //a parte de falso nao pode intersectar a figura de decisao
                        if (Intersects(f.GetIfFalse(),f, f.GetIfTrue())){
                            AddError(f, "Erro: condicao FAZ mal construida/bloqueada");
                            return;
                        //conexao ligada directamente a figura de decisao
                        }else if (f.GetIfTrue().GetNext()==f)
                            AddError(f, "Erro: a condicao FAZ nao tem argumentos");
                        //condicao faz correcta
                        else{
                            
                            //verificar se nao existem mais repetes para a mesma fig de conexao
                            boolean found_repete=false;
                            for(int i=0;i<ConnectionCycles.size();i++)
                                if ( ((Forma)ConnectionCycles.get(i)).GetIfTrue()==f.GetIfTrue() || 
                                     ((Forma)ConnectionCycles.get(i)).GetIfFalse()==f.GetIfTrue())
                                    found_repete=true;
                                
                            if (found_repete)
                                AddError(f.GetIfTrue(), "Erro: conector com mais de um REPETE/FAZ");
                            else{
                                //evitar ligacao de 2 ciclos com a mesma condicao
                                if (!ConnectionCycles.contains(f))
                                    ConnectionCycles.add(f);
                                    /*if (Stack.lastElement()!=f.GetIfFalse()){
                                        System.out.println("erro3");
                                        return;
                                    }*/
                                    if (!Stack.isEmpty())
                                        Stack.removeElementAt(Stack.size()-1);
                                }
                        }
                        //ligacao do verdadeiro nao liga directamente à conexao
                        }else{
                            AddError(f, "Erro: ligacao da condicao FAZ nao pode ter argumentos");
                            return;
                        }
                        
                        //continuar com o parser na parte falsa
                        ComplexParse(f.GetIfFalse());
                        return;
                
                //verificar repete e se existem ciclo com condicoes de falso
                }else if ( Intersects(f.GetIfFalse(),f, null) ){
                    
                    //verificar se o ciclo com o falso só pode pertence a um repete
                    if (Intersects(f.GetIfFalse(), ParseForms.size()>1 ? (Forma)ParseForms.get(ParseForms.size()-2) : null, f.GetIfTrue())){
                    
                        //verificar se ligacao do falso está directamente ligada à conexao
                        if ( (f.GetIfFalse() instanceof Conexao) && ParseForms.contains(f.GetIfFalse())){
                        
                            //a parte de verdade nao pode intersectar a figura de decisao
                            if (Intersects(f.GetIfTrue(),f, f.GetIfFalse())){
                                AddError(f, "Erro: condicao REPETE mal construida/bloqueada");
                                return;
                            //conexao ligada directamente a figura de decisao
                            }else if (f.GetIfFalse().GetNext()==f)
                                AddError(f, "Erro: a condicao REPETE nao tem argumentos");
                            //condicao repete correcta
                            else{                            
                                
                                //verificar se nao existem mais repetes para a mesma fig de conexao
                                boolean found_repete=false;
                                for(int i=0;i<ConnectionCycles.size();i++)
                                    if ( ((Forma)ConnectionCycles.get(i)).GetIfFalse()==f.GetIfFalse() || 
                                        ((Forma)ConnectionCycles.get(i)).GetIfTrue()==f.GetIfFalse())
                                        found_repete=true;                            
                                if (found_repete)
                                    AddError(f.GetIfFalse(), "Erro: conector com mais de um REPETE/FAZ");
                                else{
                                    //evitar ligacao de 2 ciclos com a mesma condicao
                                    if (!ConnectionCycles.contains(f))
                                        ConnectionCycles.add(f);
                                    /*if (Stack.lastElement()!=f.GetIfFalse()){
                                        System.out.println("erro3");
                                        return;
                                    }*/
                                    if (!Stack.isEmpty())
                                        Stack.removeElementAt(Stack.size()-1);
                                }
                        }
                        //ligacao do falso nao liga directamente à conexao
                        }else{
                            AddError(f, "Erro: ligacao da condicao REPETE nao pode ter argumentos");
                            return;
                        }
                    //se existir algum ciclo com a parte de falso
                    }else
                        AddError(f, "Erro: nao podem existir ciclos com as saidas de falso");
                    
                    //continuar com o parser na parte verdadeira
                    ComplexParse(f.GetIfTrue());
                    return;
                    
                //??????????????????????????????
                }else
                    AddError(f, "Erro: figura usada de forma errada");
            
                
            }
            //ParseForms.add(f);
        }
        
        return;
    }
    
    
    
    private void SimpleParse(Forma f){
        
        if ( Stack.contains(f) || ParseForms.contains(f) )
            return;
        
//        f.DrawShadow(Fluxogramas.DrawArea.getGraphics(),Color.green);
  //      for (int i=0;i<49999999;i++){}
        
        //se o fluxograma nao terminar testa proxima figura
        if ( !(f instanceof Decisao) ){
            
            if (f instanceof Conexao)
                ParseForms.add(f);                
            else if (f instanceof Leitura)
                parsetext.VerifyReadText(f);
            else if (f instanceof Escrita)
                parsetext.VerifyWriteText(f);
            else if (f instanceof Processo)
                parsetext.VerifyProcessText(f);
            
            
            if (f.GetNext()!=null)
                if (!ParseForms.contains(f.GetNext()) || f instanceof Conexao || 
                    (!Stack.isEmpty() && f.GetNext()==Stack.lastElement()) )
                    SimpleParse(f.GetNext());
                else{
                    AddError(f, "Erro: existem ciclos infinitos criados");
                    return;
                }
            else if ( !(f instanceof Terminador) ){
                AddError(f, "Erro: teminacao de fluxograma inesperada");
                return;
            }else
                return;        
            
        }else{
            
            parsetext.VerifyConditionText(f);
            ParseForms.add(f);
            
            //verificar se a figura tem todos os conectores
            if (f.GetIfFalse()==null || f.GetIfTrue()==null){
                AddError(f, "Erro: teminacao de fluxograma inesperada");
                return;
            }
            
            //if ( !Intersects(f.GetIfTrue(), frm_fin, f) && !Intersects(f.GetIfTrue(), frm_fin, f)){
            /*if ( ParseForms.contains(f.GetIfTrue()) && ParseForms.contains(f.GetIfFalse()) ){
                ErrList.add("Erro: existem ciclos infinitos criados");
                ErrForms.add(f);
                return;
            }*/
            
            
            
            
            //inicializar variaveis a usar em IfIntersection
            VForms.clear();
            //calcular figura de interseccao da figura de condicao
            Forma intersect_form=IfIntersection(f);
               
            if (intersect_form!=null){
                    
                //se for uma condicao if correcta
                Stack.add(intersect_form);
                
                    //saber que foi aberto mais um if
                    IfPos++;
                    //verificar se ja nao tinha sido aberto um ciclo
                    boolean isInside= parsetext.VerifyOpenCondicion();
                    //guardar posicao onde vai ser comecada a adicao de variveis encontradas dentro do if
                    int size_ini=parsetext.getUnInitializedVarsSize();
                
                SimpleParse(f.GetIfTrue());                
                
                    //adicionar variaveis que foram inicializadas
                    parsetext.AddInitializedVarsFromTrueSide(size_ini);
                    //sitio onde acabam as variaveis da parte verdadeira
                    //e onde comecam da parte falsa
                    int size_true = parsetext.getUnInitializedVarsSize(); 
                
                SimpleParse(f.GetIfFalse());
                Stack.removeElementAt(Stack.size()-1);
                
                    //saber que foi fexado mais um if
                    IfPos--;                
                    //adicionar variaveis que foram inicializadas
                    parsetext.AddInitializedVarsInPrevious(isInside, IfPos, size_ini, size_true);
                    if (!isInside)
                        IfPos=0;  
                
                SimpleParse(intersect_form);
                return;
                
                
            }else{
                   
                for (int i=0;i<ParseForms.size();i++)
                    if ( Intersects(f.GetIfTrue(), (Forma)ParseForms.get(i), null) ){
                    
                            //verificar se ja nao tinha sido aberto um ciclo
                            boolean isInside = parsetext.VerifyOpenCondicion();
                            //guardar posicao onde vai ser comecada a adicao de variveis encontradas dentro do if
                            int size_ini = parsetext.getUnInitializedVarsSize();
                    
                        Stack.add(f);
                        SimpleParse(f.GetIfTrue());
                        Stack.removeElementAt(Stack.size()-1);
                        
                            //nao deixar usar todas as variaveis inicializadas dentro do enquanto
                            parsetext.CloseOpenedCondition(isInside, size_ini);
                            
                        SimpleParse(f.GetIfFalse());
                        return;
                            
                    }else if ( Intersects(f.GetIfFalse(), (Forma)ParseForms.get(i), null) ){
                    
                            //verificar se ja nao tinha sido aberto um ciclo
                            boolean isInside = parsetext.VerifyOpenCondicion();
                            //guardar posicao onde vai ser comecada a adicao de variveis encontradas dentro do if
                            int size_ini = parsetext.getUnInitializedVarsSize();
                            
                        Stack.add(f);
                        SimpleParse(f.GetIfFalse());
                        Stack.removeElementAt(Stack.size()-1);
                        
                            //nao deixar usar todas as variaveis inicializadas dentro do enquanto
                            parsetext.CloseOpenedCondition(isInside, size_ini);
                            
                        SimpleParse(f.GetIfTrue());
                        return;
                    }
                    SimpleParse(f.GetIfTrue());
                    SimpleParse(f.GetIfFalse());
            }
        }
    }  
    
    ////////////////////////////////////////////////////////////////////////////    
    
    public static Forma GetInicialFigure(Vector Formas) throws Exception{
        
        Forma inicio=null;
        //verificar quais as figuras de inicio que existem
            boolean found=false;
            for (int i=0;i<Formas.size();i++)
                    if(Formas.get(i) instanceof Terminador && ((Forma)Formas.get(i)).Text.equals("Inicio"))
                            if (found)
                                throw new Exception("FOI ENCONTRADO MAIS DE UMA FIGURA DE INICIO");
                            else{
                                inicio=(Forma)Formas.get(i);
                                found=true;
                            }            
            //verifica se foi encontrada a figura de inicio
            if (!found)
                throw new Exception("NAO FOI ENCONTRADA A FIGURA DE INICIO");
                
            return inicio;
    }
    
    public static Forma GetFinalFigure(Vector Formas) throws Exception{
        
        Forma fim=null;
        
            //verificar quais as figuras de fim que existem
        boolean found=false;
            for (int i=0;i<Formas.size();i++)
                    if(Formas.get(i) instanceof Terminador && ((Forma)Formas.get(i)).Text.equals("Fim"))
                            if (found)
                                throw new Exception("FOI ENCONTRADO MAIS DE UMA FIGURA DE FIM");
                            else{
                                fim=(Forma)Formas.get(i);
                                found=true;            
                            }
            //verifica se foi encontrada a figura de fim
            if (!found)
                throw new Exception("NAO FOI ENCONTRADA A FIGURA DE FIM");
                
            return fim;
    }
    
    public void GetNoExecutableForms(Forma ini, Forma fin){
        //tenta encontrar algo que nunca chega a ser executado
            for(int i=0; i<Formas.size(); i++)
                if ( !Intersects(ini ,(Forma)Formas.get(i), null) )
                    if ( Intersects((Forma)Formas.get(i), fin, null) )
                        AddError((Forma)Formas.get(i), "Aviso: figura que nunca chega a ser executada");
                    else
                        AddError((Forma)Formas.get(i), "Aviso: figura que nao está ligada ao fluxograma");
    }
    
}
