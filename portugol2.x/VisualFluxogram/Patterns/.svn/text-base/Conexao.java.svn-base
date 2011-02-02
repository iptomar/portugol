/*
 * Conexao.java
 *
 * Created on 4 de Maio de 2006, 22:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package VisualFluxogram.Patterns;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Eduardo
 */
public class Conexao extends Forma{
    
    double intersectX;
    
    
    /** Creates a new instance of Losango */
    public Conexao() {
        super();
        //FormColor=new Color(255,255,225);
    }
    
    public Conexao(Point TopLeft, Color color) {
        super();
        this.TopLeft=new Point2D(TopLeft.x, TopLeft.y);
        this.BottonRight=new Point2D(TopLeft.x+30, TopLeft.y+30);
        CalcCenterX();
        CalcCenterY();
        CalcFormPts();
        if (color!=null)
            FormColor=color;
    }
    
    public void CalcFormPts(){
        //calcular eixo semimaior
        double a=BottonRight.x-Center.x;        
        //calcular eixo semimenor
        double b=BottonRight.y-Center.y;      
        //calcular valor de x(=y) para a interseccao de uma recta de 45º com a elipse
        intersectX=Math.sqrt((b*b*a*a)/(b*b+a*a));        
    }
//------------------------------------------------------------------------------
    
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
    
    /*public Point Fifth(){        
        return new Point((int)(Center.x-intersectX) ,(int)(Center.y-intersectX));
    }
    public Point Sixth(){
        return new Point((int)(Center.x+intersectX) ,(int)(Center.y-intersectX));
    }    
    public Point Seventh(){
        return new Point((int)(Center.x-intersectX) ,(int)(Center.y+intersectX));
    }    
    public Point Eighth(){
        return new Point((int)(Center.x+intersectX) ,(int)(Center.y+intersectX));
    }
    
    public int GetPosition(int pos, Point []ConectPts){
        if(ConectPts[pos].equals(First()))
            return 0;
        if(ConectPts[pos].equals(Second()))
            return 1;
        if(ConectPts[pos].equals(Third()))
            return 2;
        if(ConectPts[pos].equals(Fourth()))
            return 3;
        
        if (ConectPts[pos].equals(Fifth()))
            return 4;
        if (ConectPts[pos].equals(Sixth()))
            return 5;
        if (ConectPts[pos].equals(Seventh()))
            return 6;
        if (ConectPts[pos].equals(Eighth()))
            return 7;
        return -1;//1
    }
    
    public void SetInitialPts(int pos, Point []ConectPts){       
        if (pos==1){
            ConectPts[0]=First();
            ConectPts[1]=new Point(ConectPts[0]);
            ConectPts[1].translate(0, -15);
        }else if (pos==2){
            ConectPts[0]=Second();
            ConectPts[1]=new Point(ConectPts[0]);
            ConectPts[1].translate(0, 15);
        }else if (pos==3){
            ConectPts[0]=Third();
            ConectPts[1]=new Point(ConectPts[0]);
            ConectPts[1].translate(-15, 0);
        }else if (pos==4){
            ConectPts[0]=Fourth();
            ConectPts[1]=new Point(ConectPts[0]);
            ConectPts[1].translate(15, 0);
        }else if (pos==5){
            ConectPts[0]=Fifth();
            ConectPts[1]=new Point(ConectPts[0]);
            ConectPts[1].translate(-10, -10);
        }else if (pos==6){
            ConectPts[0]=Sixth();
            ConectPts[1]=new Point(ConectPts[0]);
            ConectPts[1].translate(10, -10);
        }else if (pos==7){
            ConectPts[0]=Seventh();
            ConectPts[1]=new Point(ConectPts[0]);
            ConectPts[1].translate(-10, 10);
        }else if (pos==8){
            ConectPts[0]=Eighth();
            ConectPts[1]=new Point(ConectPts[0]);
            ConectPts[1].translate(10, 10);
        }
        
        ConectPts[2]=new Point(ConectPts[1]);
    }
    
    public void SetFinalPts(int pos, Point []ConectPts){
        if (pos==1){
            ConectPts[4]=First();
            ConectPts[3]=new Point(ConectPts[4]);
            ConectPts[3].translate(0, -15);
        }else if (pos==2){
            ConectPts[4]=Second();
            ConectPts[3]=new Point(ConectPts[4]);
            ConectPts[3].translate(0, 15);
        }else if (pos==3){
            ConectPts[4]=Third();
            ConectPts[3]=new Point(ConectPts[4]);
            ConectPts[3].translate(-15, 0);
        }else if (pos==4){
            ConectPts[4]=Fourth();
            ConectPts[3]=new Point(ConectPts[4]);
            ConectPts[3].translate(15, 0);
        }else if (pos==5){
            ConectPts[4]=Fifth();
            ConectPts[3]=new Point(ConectPts[4]);
            ConectPts[3].translate(-10, -10);
        }else if (pos==6){
            ConectPts[4]=Sixth();
            ConectPts[3]=new Point(ConectPts[4]);
            ConectPts[3].translate(10, -10);
        }else if (pos==7){
            ConectPts[4]=Seventh();
            ConectPts[3]=new Point(ConectPts[4]);
            ConectPts[3].translate(-10, 10);
        }else if (pos==8){
            ConectPts[4]=Eighth();
            ConectPts[3]=new Point(ConectPts[4]);
            ConectPts[3].translate(10, 10);
        }
    }*/
    /*
    public void SetFinalClosestPts(Point p, Point []ConectPts){
        
            ConectPts[3]=new Point(p);
            ConectPts[4]=new Point();
            
            double dis=999999999;
                
                if (p.distance(First())<dis && extremes[0]){
                    dis=p.distance(First());
                    ConectPts[3].setLocation(First());
                    ConectPts[3].translate(0, -15);
                    ConectPts[4].setLocation(First());
                }
                if (p.distance(Second())<dis && extremes[1]){
                    dis=p.distance(Second());
                    ConectPts[3].setLocation(Second());
                    ConectPts[3].translate(0, 15);
                    ConectPts[4].setLocation(Second());
                }
                if (p.distance(Third())<dis && extremes[2]){
                    dis=p.distance(Third());
                    ConectPts[3].setLocation(Third());
                    ConectPts[3].translate(-15, 0);
                    ConectPts[4].setLocation(Third());
                }
            
                if (p.distance(Fifth())<dis){
                    dis=p.distance(Fifth());
                    ConectPts[3].setLocation(Fifth());
                    ConectPts[3].translate(-10, -10);
                    ConectPts[4].setLocation(Fifth());
                }
                if (p.distance(Sixth())<dis){
                    dis=p.distance(Sixth());
                    ConectPts[3].setLocation(Sixth());
                    ConectPts[3].translate(10, -10);
                    ConectPts[4].setLocation(Sixth());
                }
                if (p.distance(Seventh())<dis){
                    dis=p.distance(Seventh());
                    ConectPts[3].setLocation(Seventh());
                    ConectPts[3].translate(-10, 10);
                    ConectPts[4].setLocation(Seventh());
                }
                if (p.distance(Eighth())<dis){
                    dis=p.distance(Eighth());
                    ConectPts[3].setLocation(Eighth());
                    ConectPts[3].translate(10, 10);
                    ConectPts[4].setLocation(Eighth());
                }
            
                if (dis==999999999 || (p.distance(Fourth())<dis && extremes[3])){
                    dis=p.distance(Fourth());
                    ConectPts[3].setLocation(Fourth());
                    ConectPts[3].translate(15, 0);
                    ConectPts[4].setLocation(Fourth());
                }
           // }            
    }*/
//------------------------------------------------------------------------------
    
    public void DrawShadow(Graphics g){        
        DrawShadow(g, Color.black);
    }
    public void DrawShadow(Graphics g, Color c){        
        g.setColor(c);
        g.drawOval((int)TopLeft.x, (int)TopLeft.y, (int)(BottonRight.x-TopLeft.x), (int)(BottonRight.y-TopLeft.y));
    }    
    
    public void Draw(Graphics g) {
        Draw(g, FormColor);
    }
    
    public void Draw(Graphics g, Color c) {
        g.setColor(c);
        g.fillOval((int)TopLeft.x, (int)TopLeft.y, (int)(BottonRight.x-TopLeft.x), (int)(BottonRight.y-TopLeft.y));
        DrawShadow(g);
    }
    
    /*public void DrawExtremes(Graphics g){
        g.drawRect(First().x-5, First().y-5, 10, 10);
        g.drawRect(Second().x-5, Second().y-5, 10, 10);
        g.drawRect(Third().x-5, Third().y-5, 10, 10);
        g.drawRect(Fourth().x-5, Fourth().y-5, 10, 10);
        g.drawRect(Fifth().x-5, Fifth().y-5, 10, 10);
        g.drawRect(Sixth().x-5, Sixth().y-5, 10, 10);
        g.drawRect(Seventh().x-5, Seventh().y-5, 10, 10);
        g.drawRect(Eighth().x-5, Eighth().y-5, 10, 10);
    }*/
    
    //---------------------------------------------------------------
    
    public boolean Contains(Point p) {        
        //calcular posicao do ponto p em relacao ao centro da figura e dividir por eixo semimaior
        double x_a=(p.x-Center.x)/(BottonRight.x-Center.x);
        //calcular eixo semimenor
        double b=BottonRight.y-Center.y;
        //calcular valor de y pela equacao da elipse
        double y=Math.sqrt(b*b*(1-Math.pow(x_a, 2)));
        
        //verificar se o ponto p (passando para o lado positivo) se encontra dentro da equacao da elipse        
        return Math.abs(Center.y-p.y)<=y;// && Center.y-p.y>=-y;        
    }
    
    //---------------------------------------------------------------    
    public Forma clone() {
        Forma forma=new Conexao();
        forma.TopLeft=TopLeft.clone();
        forma.BottonRight=BottonRight.clone();
        forma.Center=Center.clone();
        
        forma.Text=new String(Text);
        forma.FormColor=FormColor;
        forma.CalcFormPts();
        return forma;
    }
    
}
