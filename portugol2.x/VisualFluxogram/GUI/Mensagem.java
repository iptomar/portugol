/*****************************************************************************/
/****     Copyright (C) 2005                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politécnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/*  This library is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published
 *  by the Free Software Foundation; either version 2.1 of the License, or
 *  (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
/*
 * Mensagem.java
 *
 * Created on 22 de Outubro de 2005, 17:35
 *
 */

package VisualFluxogram.GUI;

import Portugol.Language.Utils.LanguageException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;



/**
 *
 * @author António Manuel Rodrigues MAnso
 */
public class Mensagem {
    private static final String title =  FluxogramaPTG.TITLE + " Mensagem";
    private static ImageIcon icoBug = new javax.swing.ImageIcon("./VisualFluxogram/Icons/bug32.png");    
    
    public static  void Error(String msg){
       JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE); 
    }
    
    public static void Information(String msg){
       JOptionPane.showMessageDialog(null, msg, title,JOptionPane.INFORMATION_MESSAGE); 
    }
    
    public static void Warning(String msg){
       JOptionPane.showMessageDialog(null, msg, title,JOptionPane.WARNING_MESSAGE); 
    }
    
    public static int Confirm(String msg){
        return JOptionPane.showConfirmDialog(null,msg, title,JOptionPane.YES_NO_CANCEL_OPTION);
    }
    
    public static  void CompileError(LanguageException e){
        String 
              str = "INSTRUÇÃO:\n" + e.codeLine  + "\n";
              str +="ERRO:\n" + e.error  + "\n";
              str +="SOLUÇÃO:\n" + e.solution +"\n";
       
       JOptionPane.showMessageDialog(null, str, title, JOptionPane.ERROR_MESSAGE); 
    }
    public static  void ExecutionError(String msg,LanguageException e){
        String 
              str = msg + "\n\n";
              str += "INSTRUÇÃO:\n" + e.codeLine  + "\n";
              str +="ERRO:\n" + e.error  + "\n";
              str +="SOLUÇÃO:\n" + e.solution +"\n";
       
       JOptionPane.showMessageDialog(null, str, title, JOptionPane.ERROR_MESSAGE); 
    }
    
}
