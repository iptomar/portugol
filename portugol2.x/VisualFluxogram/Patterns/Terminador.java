/*
 * Terminador.java
 *
 * Created on 4 de Maio de 2006, 19:20
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
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.lang.Float;
import java.util.Vector;

/**
 *
 * @author Eduardo
 */
public class Terminador extends Forma{
    
    /** Creates a new instance of Losango */
    public Terminador() {
        super();
        //FormColor=new Color(255,255,225);
        Text="Inicio";
    }
    
    public Terminador(Point TopLeft, boolean inicio, Color color) {
        super();
        this.TopLeft=new Point2D(TopLeft.x, TopLeft.y);
        this.BottonRight=new Point2D(TopLeft.x+100, TopLeft.y+35);
        CalcCenterX();
        CalcCenterY();
        CalcFormPts();
        if (inicio)
            Text="Inicio";
        else
            Text="Fim";
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
        g.drawRoundRect(TopLeft().x, TopLeft().y, BottonRight().x-TopLeft().x, BottonRight().y-TopLeft().y,
                (int)((BottonRight().x-TopLeft().x)/4), BottonRight().y-TopLeft().y);
    }    
    
    public void Draw(Graphics g) {
        Draw(g, FormColor);
    }
    
    public void Draw(Graphics g, Color c) {
        g.setColor(c);
        g.fillRoundRect(TopLeft().x, TopLeft().y, BottonRight().x-TopLeft().x, BottonRight().y-TopLeft().y,
                (int)((BottonRight().x-TopLeft().x)/4), BottonRight().y-TopLeft().y);
        DrawShadow(g);
    }

    public boolean Contains(Point p) {
        
        //calcular posicao do ponto p em relacao ao centro da elipse e dividir por eixo semimenor
        double y_b=(p.y-Center.y)/(BottonRight.y-Center.y);
        //calcular eixo semimaior
        double a=(BottonRight.x-Center.x)/4;
        //calcular valor de x pela equacao da elipse
        double x=Math.sqrt(a*a*(1-Math.pow(y_b, 2)));
        
        //verificar se o ponto p se encontra dentro de ambas as equacao das elipses k sao movidas para as suas posicoes
        return p.x>=-x+TopLeft.x+a && p.x<=x+BottonRight.x-a;
    }
    
    //---------------------------------------------------------------    
    public Forma clone() {
        Forma forma=new Terminador();
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
        BottonRight.x=start_x+ (g.getFontMetrics().stringWidth(Text)/.6  );
        
        TopLeft.x-=(int)( g.getFontMetrics().stringWidth(Text)*.2);        
        BottonRight.x+=(int)( g.getFontMetrics().stringWidth(Text)*.2);
        /*
        g.setFont(font);
        int start_x=(int)(Center.x-g.getFontMetrics().stringWidth(Text)/2);
        
        TopLeft.x=start_x;
        TopLeft.x-=(int)( (BottonRight.x-TopLeft.x)*.3);
        
        BottonRight.x=start_x+g.getFontMetrics().stringWidth(Text);
        BottonRight.x+=(int)( (BottonRight.x-TopLeft.x)*.3);
        */
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
