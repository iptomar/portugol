/*
 * ParseFlowChatText.java
 *
 * Created on 1 de Julho de 2006, 18:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package VisualFluxogram.Parser;

import Portugol.Language.Evaluate.CalculusElement;
import Portugol.Language.Parse.Symbol;
import Portugol.Language.Parse.Variable;
import Portugol.Language.Utils.IteratorExpression;
import Portugol.Language.Utils.Parentesis;
import Portugol.Language.Utils.Values;

import VisualFluxogram.Patterns.Forma;
import java.awt.List;
import java.util.Vector;

/**
 *
 * @author Eduardo
 */
public class ParseText {
    
    private List ErrList;
    private Vector ErrForms;
    
    private boolean WithUnIniVars=false;
    private Vector UnInitializedVars=new Vector();
    private Vector InitializedVars=new Vector();
    
    private Vector Variables=new Vector();
    
    /** Creates a new instance of ParseText */
    public ParseText(List list, Vector Errors){
        ErrList = list;
        ErrForms = Errors;
    }
    
    private void AddError(Forma f, String err){
        ErrList.add(err);
        ErrForms.add(f);
    }
    
    public Vector GetVariables(){
        return Variables;
    }
    
////////////////////////////////////////////////////////////////////////////////
    
    public boolean VerifyOpenCondicion(){
        
        if (WithUnIniVars)
            //ja se tinha entrado dentro de uma condicao
            return true;
        //Avisar que todas a variaveis descobertas apartir deste momento
        //so podem ser usadas dentro do ciclo
        WithUnIniVars=true;
        //vai se entrar numa condicao
        return false;
    }
    
    public void CloseOpenedCondition(boolean isInside, int size_ini){
        if (!isInside)
            WithUnIniVars=false;
        
        //alterar variaveis para nao poderem ser utilizadas depois do enquanto
        for (int i=size_ini;i<UnInitializedVars.size();i++){
            
            boolean found = false;
            for (int j=0;j<size_ini;j++)
                if ( ( (String)UnInitializedVars.get(j) ).equals( UnInitializedVars.get(i) ) )
                    found = true;
            
            if (!found)
                ((Symbol)Variables.get( VariablePos((String)UnInitializedVars.get(i))) ).setLevel(1);
        }
        
        //remover todas a variaveis criadas dentro do enqunato
        UnInitializedVars.setSize(size_ini);
    }
    
    public int getUnInitializedVarsSize(){
        return UnInitializedVars.size();
    }
    
    public void AddInitializedVarsFromTrueSide(int size_ini){
        //adicionar vars inicializadas como por inicializar na parte falsa
        if (!InitializedVars.isEmpty()){
            Vector iniVars=(Vector)InitializedVars.lastElement();
            
            int aux_ini=size_ini;
            for (int i=0;i<iniVars.size();i++)
                UnInitializedVars.add(aux_ini++, iniVars.get(i));
            InitializedVars.remove(iniVars);
        }
        
        //alterar variaveis para nao poderem ser utilizadas dentro
        //do outro lado da condicao if
        for (int i=size_ini;i<UnInitializedVars.size();i++){
            
            boolean found = false;
            for (int j=0;j<size_ini;j++)
                if ( ( (String)UnInitializedVars.get(j) ).equals( UnInitializedVars.get(i) ) )
                    found = true;
            
            if (!found)
                ((Symbol)Variables.get( VariablePos((String)UnInitializedVars.get(i))) ).setLevel(1);
        }
        
    }
    
    public void AddInitializedVarsInPrevious(boolean isInside, int IfPos, int size_ini, int size_true){
        //adicionar vars inicializadas como por inicializar na condicao onde se estava antes
        if (!InitializedVars.isEmpty()){
            Vector iniVars=(Vector)InitializedVars.lastElement();
            for (int i=0;i<iniVars.size();i++)
                UnInitializedVars.add(iniVars.get(i));
            InitializedVars.remove(iniVars);
        }
        
        ///////////
        if (!isInside){
            //saber que nao é necessario procurar + vars para inicializar
            WithUnIniVars=false;
            
            for (int i=size_true;i<UnInitializedVars.size();i++){
                //alterar variaveis para nao poderem ser utilizadas depois do if
                ((Symbol)Variables.get( VariablePos((String)UnInitializedVars.get(i))) ).setLevel(1);
                for (int j=size_ini;j<size_true;j++)
                    //se existir 2 variaveis iguas por inicializar em ambos os lados
                    //entao a variavel passa a ser considerada como inicializada
                    if ( ((String)UnInitializedVars.get(i)).toString().equals( ((String)UnInitializedVars.get(j)).toString() ) )
                        ((Symbol)Variables.get( VariablePos((String)UnInitializedVars.get(i))) ).setLevel(0);
            }
            
            InitializedVars.clear();
            
        }else{
            
            //vector auxiliar com as variaveis inicializadas encontradas
            Vector InitializedVarsAux;
            if (IfPos==0 && InitializedVars.isEmpty())
                InitializedVarsAux=new Vector();
            else if (InitializedVars.size()==IfPos)
                //se esta dentro do mesmo if apaga o anterior para guardar
                //um novo vector com o resto das alteracoes
                InitializedVarsAux=(Vector)InitializedVars.remove( InitializedVars.size()-1 );
            else
                InitializedVarsAux=new Vector();
            
            for (int i=size_true;i<UnInitializedVars.size();i++){
                //alterar variaveis para nao poderem ser utilizadas depois do if
                ((Symbol)Variables.get( VariablePos((String)UnInitializedVars.get(i))) ).setLevel(1);
                for (int j=size_ini;j<size_true;j++)
                    //se existir 2 variaveis iguas por inicializar em ambos os lados
                    //entao a variavel passa a ser considerada como inicializada
                    if ( ((String)UnInitializedVars.get(i)).equals( (String)UnInitializedVars.get(j) ) ){
                    ((Symbol)Variables.get( VariablePos((String)UnInitializedVars.get(i))) ).setLevel(0);
                    //guardar todas as variaveis inicializadas em ambos os lados do if
                    if (!InitializedVarsAux.contains(UnInitializedVars.get(i)))
                        InitializedVarsAux.add(UnInitializedVars.get(i));
                    }
            }
            
            //guardar vector com todas as variaveis inicializadas
            InitializedVars.add(InitializedVarsAux);
            
        }
        
        //deixar todas as variaveis por inicializar dentro da condicao em causa
        UnInitializedVars.setSize(size_ini);
    }
    
////////////////////////////////////////////////////////////////////////////////
    
    public boolean VarIsCorrect(String str, Forma f){
        
        String err= Variable.getErrorName(str);
        if ( !err.equals("SEM ERRO NO NOME") ) {
            AddError(f, "Erro: "+err);
            return false;
        }
        return true;
        
    }
    
    public int VariablePos(String var_name){
        for (int i=0;i<Variables.size();i++)
            if ( ((Symbol)Variables.get(i)).getName().equalsIgnoreCase(var_name.trim()) )
                return i;
        return -1;
    }
    
    public boolean ExpressionIsCorrect(String expression, Forma f, boolean shownexpr){
        
        if (expression.trim().length()==0 || expression==null){
            AddError(f, "Erro: figura com falta de alguma expressao");
            return false;
        }
        
        String elem;
        IteratorExpression it=new IteratorExpression(expression);
        CalculusElement calc = new CalculusElement();
        String err="";
        int var_index;
        while (it.hasMoreElements()){
            elem = it.current();
            it.next();
            var_index=VariablePos(elem);
            
            if ( (var_index==-1 || ((Symbol)Variables.get(var_index)).getLevel()==1 ) &&
                    !calc.IsCalculus(elem) && !Values.IsValue(elem) && !Parentesis.IsParentesis(elem) ){
                if (!err.equals(""))
                    err+=" ,";
                err+= elem;
                if (var_index!=-1 && ((Symbol)Variables.get(var_index)).getLevel()==1 )
                    err+= "(variavel por inicializar)";
            }
            
        }
        
        if (!err.equals("")){
            if (shownexpr)
                AddError(f, "Erro: a expresao [ "+expression+" ] contem erros nos seguintes simbolos/variaveis: "+err);
            else
                AddError(f, "Erro: expresao com erro nos seguintes simbolos/variaveis: "+err);
            return false;
        }
        return true;
    }
    
    
    public void VerifyProcessText(Forma f){
        
        if (f.Text.contains("<-") ){
            
            String var_name=f.Text.substring(0, f.Text.indexOf("<-"));
            if (!VarIsCorrect(var_name, f))
                return;
            
            String expression=f.Text.substring(f.Text.indexOf("<-")+2);
            /************************************************/
            /************************************************/
            /************************************************/
            /************************************************/
            /************************************************/
            NodeInstruction tmp = new NodeInstruction(expression,0,0);
            ExpandFluxogram.VerifyCalculate(tmp,Variables);
            expression = tmp.GetText();
            /************************************************/
            /************************************************/
            /************************************************/
            /************************************************/
            /************************************************/
            /************************************************/
            
            if (!ExpressionIsCorrect(expression, f, false))
                return;
            
            int var_type = Symbol.DESCONHECIDO;
            try {
                var_type = UnknowExpression.FortuneType_NumberPreference(expression, Variables);
            } catch (Exception ex) {
                AddError(f, "Erro:" + ex.getMessage().toLowerCase() );
                return;
            }
            if (var_type==Symbol.DESCONHECIDO){
                AddError(f, "Erro: expressao contem tipos de variaveis incompativeis  ou funcoes com argumentos invalidos");
                return;
            }
            
            int var_index=VariablePos(var_name);
            if (var_index==-1){
                Variables.add(new Symbol(var_type, var_name.toLowerCase()));
                if (WithUnIniVars)
                    UnInitializedVars.add(var_name.toLowerCase());
            }else{
                Symbol var=(Symbol)Variables.get(var_index);
                if (var.getType()==Symbol.DESCONHECIDO)
                    var.setType(var_type);
                if (var_type==Symbol.REAL && var.getType()==Symbol.INTEIRO)
                    var.setType(var_type);
                if (var_type==Symbol.TEXTO && var.getType()==Symbol.CARACTER)
                    var.setType(var_type);
                
                if (!WithUnIniVars)
                    var.setLevel(0);
                if (UnInitializedVars.contains(var_name.toLowerCase()))
                    UnInitializedVars.add(var_name.toLowerCase());
                
                if ( !var.IsCompatible(var_type) ){
                    AddError(f, "Erro: a variavel [ "+var_name+" ] ja foi definida como "+var.getStringType()+
                            ", nao pode atribuir uma expressao do tipo "+Symbol.getStringType(var_type));
                    return;
                }
            }
            
        }else
            AddError(f, "Erro: expresao sem simbolo de atribuicao(<-)");
        
    }
    
    public void VerifyWriteText(Forma f){
        
        int parents=0;
        int begin=0;
        
        for (int pos=0; pos<f.Text.length(); pos++)
            if (f.Text.charAt(pos)=='(')
                parents++;
            else if (f.Text.charAt(pos)==')')
                parents--;
            else if ( (f.Text.charAt(pos)==',' || pos==f.Text.length()-1) && parents==0 ){
            String expression;
            if (f.Text.charAt(pos)==',')
                expression=f.Text.substring(begin,pos);
            else
                expression=f.Text.substring(begin,pos+1);
            
            if (!ExpressionIsCorrect(expression, f, true))
                return;
            try {
                if (UnknowExpression.FortuneType_TextPreference(expression, Variables)==Symbol.DESCONHECIDO){
                    AddError(f, "Erro: a expresao [ "+expression+" ] contem tipos de variaveis incompativeis ou funcoes com argumentos invalidos");
                    return;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            begin=pos+1;
            }
        
        if ( f.Text.trim().endsWith(",")  ) {
            AddError(f, "Erro: tem de colocar uma nova expressao depois de cada virgula");
            return;
        }
        
        if (parents!=0){
            AddError(f, "Erro: a expressao contem erro no numero de parentesis");
        }
        
    }
    
    public void VerifyReadText(Forma f){
        String var_name=f.Text.toLowerCase();
        if (!VarIsCorrect(f.Text, f))
            return;
        else if (VariablePos(f.Text)==-1){
            Variables.add(new Symbol(Symbol.DESCONHECIDO, var_name));
            if (WithUnIniVars)
                UnInitializedVars.add(var_name);
        }else if (!WithUnIniVars)
            ((Symbol)Variables.get(VariablePos(f.Text))).setLevel(0);
        if (UnInitializedVars.contains(var_name))
            UnInitializedVars.add(var_name);
    }
    
    public void VerifyConditionText(Forma f){
        
        if (!ExpressionIsCorrect(f.Text, f, false))
            return;
        
        int type = Symbol.DESCONHECIDO;
        try {
            type = UnknowExpression.FortuneType_LogicPreference(f.Text, Variables);
        } catch (Exception ex) {
            AddError(f, "Erro:" + ex.getMessage().toLowerCase() );
            return;
        }
        if (type != Symbol.LOGICO){
            AddError(f, "Erro: tipo incompativel [ "+Symbol.getStringType(type)+" ], tem de usar um expressao do tipo LOGICO");
            return;
        }
    }
    
}
