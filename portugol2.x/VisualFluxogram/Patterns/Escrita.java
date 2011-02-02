/*
 * Escrita.java
 *
 * Created on 5 de Maio de 2006, 1:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package VisualFluxogram.Patterns;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

/**
 *
 * @author Eduardo
 */
public class Escrita extends Forma{
    
    private int []xPoints=new int[5];
    private int []yPoints=new int[5];
    
    /** Creates a new instance of Losango */
    public Escrita() {
        super();
        //FormColor=new Color(0,228,168);        
    }
    
    public Escrita(Point TopLeft, String text, Color color) {
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
        xPoints[0]=(int)(TopLeft.x+(BottonRight.x-TopLeft.x)*.2);
        yPoints[0]=(int)TopLeft.y;
        xPoints[1]=(int)BottonRight.x;
        yPoints[1]=(int)TopLeft.y;
        xPoints[2]=(int)BottonRight.x;
        yPoints[2]=(int)BottonRight.y;
        xPoints[3]=(int)TopLeft.x;
        yPoints[3]=(int)BottonRight.y;
        xPoints[4]=(int)TopLeft.x;
        yPoints[4]=(int)(TopLeft.y+(BottonRight.y-TopLeft.y)*.2);
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
    
//------------------------------------------------------------------------------
    
    public void DrawShadow(Graphics g){        
        DrawShadow(g, Color.black);
    }
    public void DrawShadow(Graphics g, Color c){        
        g.setColor(c);
        g.drawPolygon(xPoints, yPoints, 5);
    }
    
    
    public void Draw(Graphics g) {
        Draw(g, FormColor);
    }
    
    public void Draw(Graphics g, Color c) {
        g.setColor(c);
        g.fillPolygon(xPoints, yPoints, 5);
        DrawShadow(g);
    }

    public boolean Contains(Point p) {
        
        if (p.x<TopLeft.x || p.x>BottonRight.x || p.y<TopLeft.y || p.y>BottonRight.y)
            return false;
        
        //calcular posicao do ponto p em relacao ao centro e converter sempre para positivo
        double x=p.x-xPoints[0];
        //calcular b para a equacao da recta
        double b=yPoints[4]-TopLeft.y;//(BottonRight.y-Center.y);
        //calcular inclinacao da recta
        double m=b/(xPoints[0]-TopLeft.x);
        //calcular valor de y pela equacao da recta
        double y=m*x+b;
        
        //calcular posicao do ponto p em relacao ao centro e verificar se se encontra abaixo da recta
        return yPoints[4]-p.y<=y;        
    }
    
    //---------------------------------------------------------------    
    public Forma clone() {
        Forma forma=new Escrita();
        forma.TopLeft=TopLeft.clone();
        forma.BottonRight=BottonRight.clone();
        forma.Center=Center.clone();
        
        forma.Text=new String(Text);
        forma.FormColor=FormColor;
        forma.CalcFormPts();
        return forma;
    }
    
}
