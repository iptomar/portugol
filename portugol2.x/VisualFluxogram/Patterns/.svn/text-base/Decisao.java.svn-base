/*
 * Decisao.java
 *
 * Created on 4 de Abril de 2006, 10:56
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
import java.awt.Polygon;
import java.util.Vector;

/**
 *
 * @author Eduardo
 */
public class Decisao extends Forma{
    
    private int []xPoints=new int[4];
    private int []yPoints=new int[4];
    
    /** Creates a new instance of Losango */
    public Decisao() {
        super();
        //FormColor=new Color(255,153,225);
    }
    
    public Decisao(Point TopLeft, String text, Color color) {
        super();
        this.TopLeft=new Point2D(TopLeft.x, TopLeft.y);
        this.BottonRight=new Point2D(TopLeft.x+120, TopLeft.y+60);
        CalcCenterX();
        CalcCenterY();
        CalcFormPts();
        Text=text;
        if (color!=null)
            FormColor=color;
    }
    
    public void CalcFormPts(){        
        xPoints[0]=(int)Center.x;
        yPoints[0]=(int)TopLeft.y;
        xPoints[1]=(int)BottonRight.x;
        yPoints[1]=(int)Center.y;
        xPoints[2]=(int)Center.x;
        yPoints[2]=(int)BottonRight.y;
        xPoints[3]=(int)TopLeft.x;
        yPoints[3]=(int)Center.y;
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
    
    //---------------------------------------------------------------

    public boolean Contains(Point p) {
        //calcular posicao do ponto p em relacao ao centro e converter sempre para positivo
        double x=Math.abs(p.x-Center.x);
        //calcular b para a equacao da recta
        double b=(Center.y-BottonRight.y);//(BottonRight.y-Center.y);
        //calcular inclinacao da recta
        double m=b/(BottonRight.x-Center.x);
        //calcular valor de y pela equacao da recta
        double y=m*x-b;
        
        //calcular posicao do ponto p em relacao ao centro convertendo para positivo e verificar se se encontra abaixo da recta
        return Math.abs(Center.y-p.y)<=y;
    }
    
//---------------------------------------------------------------
    public boolean equals(Object obj) { 
        if ((obj != null) & (obj instanceof Decisao)){
            return this.TopLeft.equals(((Decisao)obj).TopLeft) && 
                    this.TopLeft.equals(((Decisao)obj).TopLeft);
        }
        return false;
    }
    
    public Forma clone() {
        Forma forma=new Decisao();
        forma.TopLeft=TopLeft.clone();
        forma.BottonRight=BottonRight.clone();
        forma.Center=Center.clone();
        
        forma.Text=new String(Text);
        forma.FormColor=FormColor;
        forma.CalcFormPts();
        return forma;
    }
    
//---------------------------------------------------------------
    /*
    public int MaxLeftPos(int Vert){
        
        Line h=new Line(new Point(0, Vert), new Point(5, Vert));        
        if (Vert<=Left().y)
            return (int)h.Intersection(new Line(Left(), Top())).x;
        else
            return (int)h.Intersection(new Line(Botton(), Left())).x;
    }
    
    public int MaxRightPos(int Vert){
        
        Line h=new Line(new Point(0, Vert), new Point(5, Vert));        
        if (Vert<=Left().y)
            return (int)h.Intersection(new Line(Top(), Right())).x;
        else
            return (int)h.Intersection(new Line(Right(), Botton())).x;
    }
        
    public void DrawText(Graphics g){
        
        Font font = new Font("Serif", Font.PLAIN, 20);
        g.setFont(font);
        //g.setFont(g.getFont().deriveFont(g.getFont().getStyle(), 40));        
        
        
        Vector v= new Vector();
        int base_y,top_y,left_x,right_x;
        int text_index=0,index=Text.length(),nlines=1;
        
        String line=Text.intern();
        String aux="";
        
        //linha em k começa por tentar
        int line_n=1;
        while(true){
            //base onde o texto vai ser escrito dependendo do n de linhas
            base_y=Top().y-g.getFontMetrics().getDescent();
            base_y+=((Botton().y-Top().y)-(nlines*g.getFontMetrics().getHeight()))/2;
            base_y+=line_n*g.getFontMetrics().getHeight();
            
            //altura maxima k o texto atinge
            //top_y=base_y+g.getFontMetrics().getDescent()-g.getFontMetrics().getAscent();
            top_y=base_y-g.getFontMetrics().getHeight();

            //calcular espaço maximo para escrita
            if (MaxLeftPos(base_y) > MaxLeftPos(top_y))
                left_x=MaxLeftPos(base_y);
            else
                left_x=MaxLeftPos(top_y);
            
            if (MaxRightPos(base_y) < MaxRightPos(top_y))
                right_x=MaxRightPos(base_y);
            else
                right_x=MaxRightPos(top_y);      

            
            //verifica se o texto cabe no espaço calculado em cima
            if (g.getFontMetrics().stringWidth(line+" ")<right_x-left_x || index==0){                
                aux+=line;
                
                //verifica se o texto cabe todo dentro da figura, se nao couber adiciona "...""
                if (base_y+g.getFontMetrics().getHeight()>Botton().y){
                    v.add("...");
                    break;
                }                
                
                //verifica se a palavra min cabe na figura
                if (index==0){
                    //v.add("...");
                    //-->
                    v.add(line.substring(1,line.length()));
                    index=line.length();
                    
                }else                  
                    v.add(line.substring(1,line.length()));
                
                //introducao do texto completa
                if (aux.equals(Text))
                    break;
                
                //calcula posicao na string original
                text_index+=index;
                line=Text.substring(text_index,Text.length());
                //passa a tentar escrever a proxima linha
                line_n++;
                
            }else if (line_n==nlines){// || index==0){ //significa k o texto nao cabe no n de linhas definido-> tentar com + linhas
                
                //if (index==0)
                //    g.setFont(g.getFont().deriveFont(g.getFont().getStyle(), (right_x-left_x)/12*27));
                
                line=Text.intern();
                text_index=0;
                index=Text.length();
                nlines++;
                aux="";
                line_n=1;
                v.clear(); 
            }else{//significa k o texto nao cabe e vai ter de ser subdividido por espaços pra caber
                index=line.lastIndexOf(' ');
                if (index!=0)
                    line=line.substring(0,index);
            }
            
        }       
                
//-----------------------------------------

           
        //escrever o texto na figura
        for (int i=0;i<v.size();i++){
               
            base_y=Top().y-g.getFontMetrics().getDescent();
            base_y+=((Botton().y-Top().y)-(v.size()*g.getFontMetrics().getHeight()))/2;
            base_y+=(i+1)*g.getFontMetrics().getHeight();
            
            //top_y=base_y+g.getFontMetrics().getDescent()-g.getFontMetrics().getAscent();
            top_y=base_y-g.getFontMetrics().getHeight();
            
            if (MaxLeftPos(base_y) > MaxLeftPos(top_y))
                left_x=MaxLeftPos(base_y);
            else
                left_x=MaxLeftPos(top_y);
            
            if (MaxRightPos(base_y) < MaxRightPos(top_y))
                right_x=MaxRightPos(base_y);
            else
                right_x=MaxRightPos(top_y);
            


            
            
            aux=(String)v.get(i);
            g.drawString(aux,(right_x+left_x-g.getFontMetrics().stringWidth(aux))/2,base_y);
            //g.drawString(aux,(right_x-left_x-g.getFontMetrics().stringWidth(aux))/2+left_x,base_y);
            
            //g.drawLine(left_x,top_y,right_x,top_y);
            //g.drawLine(left_x,base_y,right_x,base_y);
                
        }            
            
    }

    */

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
        BottonRight.x=start_x+g.getFontMetrics().stringWidth(Text);
                
        TopLeft.x-=(int)( g.getFontMetrics().stringWidth(Text)*.8);     
        BottonRight.x+=(int)( g.getFontMetrics().stringWidth(Text)*.8);
        */
        
        int base_y=(int)TopLeft.y-g.getFontMetrics().getDescent();
            base_y+=((BottonRight.y-TopLeft.y)-(g.getFontMetrics().getHeight()))/2;
            
            TopLeft.y=base_y;
            TopLeft.y-=(int)( (BottonRight.y-TopLeft.y)*.25);
            
            BottonRight.y=base_y+g.getFontMetrics().getHeight();
            BottonRight.y+=(int)( (BottonRight.y-TopLeft.y)*.25);
        
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