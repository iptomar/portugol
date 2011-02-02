/*
 * Leitura.java
 *
 * Created on 5 de Maio de 2006, 1:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package VisualFluxogram.Patterns;

import VisualFluxogram.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.RoundRectangle2D;
import java.util.Vector;

/**
 *
 * @author Eduardo
 */
public class Leitura extends Forma{
    
    private int []xPoints=new int[4];
    private int []yPoints=new int[4];
    
    /** Creates a new instance of Losango */
    public Leitura() {
        super();
        //FormColor=new Color(102,204,255);        
    }
    
    public Leitura(Point TopLeft, String text, Color color) {
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
        xPoints[2]=(int)(TopLeft.x+(BottonRight.x-TopLeft.x)*.8);
        yPoints[2]=(int)BottonRight.y;
        xPoints[3]=(int)TopLeft.x;
        yPoints[3]=(int)BottonRight.y;
        ///////////////////////////////////////////////////
        /*
        double b=(BottonRight.y-TopLeft.y);//(BottonRight.y-Center.y);
        //calcular inclinacao da recta
        double m=b/(xPoints[0]-TopLeft.x);
        //calcular valor de x pela equacao da recta
        double x=(BottonRight.y-Center.y-b)/m;
        
        Third.setLocation( TopLeft.x - x, Center.y);
        Fourth.setLocation( BottonRight.x + x, Center.y);        */
    }
    
    public Point First(){
        return Top();
    }    
    public Point Second(){
        return Botton();
    }    
    public Point Third(){
        double b=(BottonRight.y-TopLeft.y);//(BottonRight.y-Center.y);
        //calcular inclinacao da recta
        double m=b/(xPoints[0]-TopLeft.x);
        //calcular valor de x pela equacao da recta
        ///return new Point((int)((TopLeft.x+(BottonRight.x-TopLeft.x)*.2)+((BottonRight.y-Center.y-b)/m)), (int)Center.y);
        return new Point((int)(TopLeft.x-((BottonRight.y-Center.y-b)/m)), (int)Center.y);
    }    
    public Point Fourth(){
        double b=(BottonRight.y-TopLeft.y);//(BottonRight.y-Center.y);
        //calcular inclinacao da recta
        double m=b/(xPoints[0]-TopLeft.x);
        //calcular valor de x pela equacao da recta
        return new Point((int)(BottonRight.x+((BottonRight.y-Center.y-b)/m)), (int)Center.y);
    }
//------------------------------------------------------------------------------
    
    public void DrawShadow(Graphics g){        
        DrawShadow(g, Color.black);
    }
    public void DrawShadow(Graphics g, Color c){        
        g.setColor(c);
        g.drawPolygon(xPoints, yPoints, 4);
    }    
    
    public void Draw(Graphics g) {
        Draw(g, FormColor);
    }
    
    public void Draw(Graphics g, Color c) {
        g.setColor(c);
        g.fillPolygon(xPoints, yPoints, 4);
        DrawShadow(g);
    }

    public boolean Contains(Point p) {
        
        if(p.y>BottonRight.y || p.y<TopLeft.y)
            return false;
        
        //calcular posicao do ponto p em relacao ao centro e converter sempre para positivo
        //double x1=p.x-xPoints[0];
        //calcular b para a equacao da recta
        double b=BottonRight.y-TopLeft.y;//(BottonRight.y-Center.y);
        //calcular inclinacao da recta
        double m=b/(xPoints[0]-TopLeft.x);
        //calcular valor de y pela equacao da recta
        //double y=m*x1+b;
        
        //double x2=p.x-BottonRight.x;
        //double y2=m*x2+b;
        
        //calcular posicao do ponto p em relacao ao centro convertendo para positivo e verificar se se encontra abaixo da recta
        return BottonRight.y-p.y<=  m*(p.x-xPoints[0])+b && 
                BottonRight.y-p.y>= m*(p.x-BottonRight.x)+b;
        
    }
    
    //---------------------------------------------------------------    
    public Forma clone() {
        Forma forma=new Leitura();
        forma.TopLeft=TopLeft.clone();
        forma.BottonRight=BottonRight.clone();
        forma.Center=Center.clone();
        
        forma.Text=new String(Text);
        forma.FormColor=FormColor;
        forma.CalcFormPts();
        return forma;
    }
    
    public void ResizeFromText(Graphics g, Font font){
        
        Point2D AuxCenter= new Point2D(Center);
        
        g.setFont(font);
        int start_x=(int)(Center.x-g.getFontMetrics().stringWidth(Text)/2);
        
        TopLeft.x=start_x;
        BottonRight.x=start_x+ (g.getFontMetrics().stringWidth(Text)/.8  );
        
        TopLeft.x-=(int)( g.getFontMetrics().stringWidth(Text)*.2)+7;        
        BottonRight.x+=(int)( g.getFontMetrics().stringWidth(Text)*.2)+7;
        
        
        int base_y=(int)TopLeft.y-g.getFontMetrics().getDescent();
            base_y+=((BottonRight.y-TopLeft.y)-(g.getFontMetrics().getHeight()))/2;
            
            TopLeft.y=base_y-2;
            BottonRight.y=base_y+g.getFontMetrics().getHeight()+2;
        
        CalcCenterX();
        CalcCenterY();
        
        TopLeft.x+=AuxCenter.x-Center.x;
        TopLeft.y+=AuxCenter.y-Center.y;
        BottonRight.x+=-Center.x+AuxCenter.x;
        BottonRight.y+=-Center.y+AuxCenter.y;
        
        CalcCenterX();
        CalcCenterY();
        CalcFormPts();
        
    }
    
}
