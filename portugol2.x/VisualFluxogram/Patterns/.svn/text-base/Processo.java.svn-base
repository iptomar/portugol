/*
 * Processo.java
 *
 * Created on 5 de Maio de 2006, 21:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package VisualFluxogram.Patterns;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Vector;

/**
 *
 * @author Eduardo
 */
public class Processo extends Forma{
    
    /** Creates a new instance of Losango */
    public Processo() {
        super();
        //FormColor=new Color(255,207,1);
    }
    
    public Processo(Point TopLeft, String text, Color color) {
        super();
        this.TopLeft=new Point2D(TopLeft.x, TopLeft.y);
        this.BottonRight=new Point2D(TopLeft.x+100, TopLeft.y+35);
        CalcCenterX();
        CalcCenterY();
        CalcFormPts();
        Text=text;
        if (color!=null)
            FormColor=color;
    }
    
    public void CalcFormPts(){
        
    }
    
    public Point First(){
        return Top();
    }    
    public Point Second(){
        return Botton();
    }    
    public Point Third(){
        return Left();
    }    
    public Point Fourth(){
        return Right();
    }
    
    public void DrawShadow(Graphics g){        
        DrawShadow(g, Color.black);
    }    
    public void DrawShadow(Graphics g, Color c){        
        g.setColor(c);
        g.drawRect((int)TopLeft.x, (int)TopLeft.y, 
                (int)(BottonRight.x-TopLeft.x), (int)(BottonRight.y-TopLeft.y));
    }
    
    
    public void Draw(Graphics g) {
        Draw(g, FormColor);
    }
    
    public void Draw(Graphics g, Color c) {
        g.setColor(c);
        g.fillRect((int)TopLeft.x, (int)TopLeft.y, 
                (int)(BottonRight.x-TopLeft.x), (int)(BottonRight.y-TopLeft.y));
        DrawShadow(g);
    }

    public boolean Contains(Point p) {        
        return p.x>=TopLeft.x && p.x<=BottonRight.x && p.y>=TopLeft.y && p.y<=BottonRight.y;        
    }
    
    //---------------------------------------------------------------    
    public Forma clone() {
        Forma forma=new Processo();
        forma.TopLeft=TopLeft.clone();
        forma.BottonRight=BottonRight.clone();
        forma.Center=Center.clone();
        
        forma.Text=new String(Text);
        forma.FormColor=FormColor;
        forma.CalcFormPts();        
        return forma;
    }
    
}
