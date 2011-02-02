/*
 * Forma.java
 *
 * Created on 4 de Abril de 2006, 10:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package VisualFluxogram.Patterns;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

/**
 *
 * @author Eduardo
 */
public abstract class Forma implements Serializable {
    
    public static final int FIRST      =  0;
    public static final int SECOND     =  1;
    public static final int THIRD      =  2;
    public static final int FOURTH     =  3;
    public static final int UNKNOW     = -1;
    
    public static int MIN_CONECTOR_DIS =  12;
    public static final int DEFAULT_MIN_CONECTOR_DIS =  12;    
    
    
    public Point2D TopLeft;
    public Point2D BottonRight;
    public Point2D Center;
    
    public String Text="";
    public Color FormColor;
    
    protected Conector Next = null;
    protected Conector IfTrue = null;
    protected Conector IfFalse = null;
    
    protected boolean []extremes={true,true,true,true};
    
    /** Creates a new instance of Formas */
    public Forma() {
        TopLeft=new Point2D(100,100);
        BottonRight=new Point2D(300,300);
        Center=new Point2D(200,200);
        FormColor=new Color(187,224,227);
    }
    
    public Forma(Point TopLeft, String text, Color color) {
        this.TopLeft=new Point2D(TopLeft.x, TopLeft.y);
        this.BottonRight=new Point2D(TopLeft.x+120, TopLeft.y+60);
        this.Center=new Point2D();        
        CalcCenterX();
        CalcCenterY();
        CalcFormPts();
        Text=text;
        if (color!=null)
            FormColor=color;
        else
            FormColor=new Color(187,224,227);
    }
    
    public Conector NextConector(){
        return Next;
    }
    public Forma GetNext(){
        if (Next!=null)        
            return Next.Pointer();
        return null;
    }
    public void SetNext(Forma next){
        if (Next==null)
            Next = new Conector(this);
        Next.SetPointer(next);
    }
    
    public Conector IfTrueConector(){
        return IfTrue;
    }
    public Forma GetIfTrue(){
        if (IfTrue!=null)
            return IfTrue.Pointer();
        return null;
    }
    public void SetIfTrue(Forma iftrue){
        if (IfTrue==null)
            IfTrue = new Conector(this);
        IfTrue.SetPointer(iftrue);
    }
    
    public Conector IfFalseConector(){
        return IfFalse;
    }
    public Forma GetIfFalse(){
        if (IfFalse!=null)
            return IfFalse.Pointer();
        return null;
    }
    public void SetIfFalse(Forma iffalse){
        if (IfFalse==null)
            IfFalse = new Conector(this);
        IfFalse.SetPointer(iffalse);
    }
    
    public void DeleteNextConector(){
        Next.UnBlockConector();
        Next = null;
    }
    public void DeleteIfTrueConector(){
        if ( IfTrue != null){
            IfTrue.UnBlockConector();
            IfTrue = null;
        }
    }
    public void DeleteIfFalseConector(){
        IfFalse.UnBlockConector();
        IfFalse = null;
    }
    
    public void CreateNextConector(Forma next, int InitialOutPos, int FinalInPos){
        Next = new Conector(this);
        Next.SetPointer(next);
        Next.CalcConector(InitialOutPos, FinalInPos);
    }
    
    public void CreateIfTrueConector(Forma iftrue, int InitialOutPos, int FinalInPos){
        IfTrue =  new Conector(this);
        IfTrue.SetPointer(iftrue);
        IfTrue.CalcConector(InitialOutPos, FinalInPos);
    }
    public void CreateIfFalseConector(Forma iffalse, int InitialOutPos, int FinalInPos){
        IfFalse =  new Conector(this);
        IfFalse.SetPointer(iffalse);
        IfFalse.CalcConector(InitialOutPos, FinalInPos);
    }
    
    
    
    public Rectangle GetBounds(){
        return new Rectangle((int)TopLeft.x, (int)TopLeft.y,
                (int)(BottonRight.x-TopLeft.x), (int)(BottonRight.y-TopLeft.y));
    }
    
    public Boolean ValidExtremes(){
        return extremes[FIRST] || extremes[SECOND] ||
                extremes[THIRD] || extremes[FOURTH];
    }
    
    public Point TopLeft(){
        return TopLeft.toPoint();
    }
    public Point TopRight(){
        return new Point((int)BottonRight.x, (int)TopLeft.y);
    }        
    public Point BottonLeft(){
        return new Point((int)TopLeft.x, (int)BottonRight.y);
    }  
    public Point BottonRight(){
        return BottonRight.toPoint();
    }    
       
    public Point Top(){
        return new Point((int)Center.x, (int)TopLeft.y);
    }    
    public Point Left(){
        return new Point((int)TopLeft.x, (int)Center.y);
    }    
    public Point Right(){
        return new Point((int)BottonRight.x, (int)Center.y);
    }    
    public Point Botton(){
        return new Point((int)Center.x, (int)BottonRight.y);
    }
    
//------------------------------------------------------
    public void ExtendTopLeft(int dx, int dy){
        TopLeft.x+=dx;
        TopLeft.y+=dy;
        CalcCenterX();
        CalcCenterY();
        CalcFormPts();
    }
    public void ExtendTopRight(int dx, int dy){
        BottonRight.x+=dx;        
        TopLeft.y+=dy;
        CalcCenterX();
        CalcCenterY();
        CalcFormPts();
    }
    public void ExtendBottonLeft(int dx, int dy){
        TopLeft.x+=dx;        
        BottonRight.y+=dy;
        CalcCenterX();
        CalcCenterY();
        CalcFormPts();
    }
    public void ExtendBottonRight(int dx, int dy){
        BottonRight.x+=dx;
        BottonRight.y+=dy;
        CalcCenterX();
        CalcCenterY();
        CalcFormPts();
    }
    
    public void ExtendLeft(int dx){
        TopLeft.x+=dx;
        CalcCenterX();
        CalcFormPts();
    }
    public void ExtendRight(int dx){
        BottonRight.x+=dx;
        CalcCenterX();
        CalcFormPts();
    }
    public void ExtendTop(int dy){
        TopLeft.y+=dy;
        CalcCenterY();
        CalcFormPts();
    }
    public void ExtendBotton(int dy){
        BottonRight.y+=dy;
        CalcCenterY();
        CalcFormPts();
    }
//______________________________________________________
    public void CalcRightPosition(){
        if (TopLeft.x>BottonRight.x){
            double aux=TopLeft.x;
            TopLeft.SetX(BottonRight.x);
            BottonRight.SetX(aux);
        }
        if (TopLeft.y>BottonRight.y){
            double aux=TopLeft.y;
            TopLeft.SetY(BottonRight.y);
            BottonRight.SetY(aux);
        }            
    }
    
    protected void CalcCenterX(){
        Center.x=(BottonRight.x+TopLeft.x)/2;
    }    
    protected void CalcCenterY(){
        Center.y=(BottonRight.y+TopLeft.y)/2;       
    }
    
    public abstract void CalcFormPts();
//------------------------------------------------------
    public abstract Point First();
    public abstract Point Second();
    public abstract Point Third();
    public abstract Point Fourth();
    
    public Point GetPositionPt(int pos) throws Exception{
        if(pos==FIRST)
            return First();
        if(pos==SECOND)
            return Second();
        if(pos==THIRD)
            return Third();
        if(pos==FOURTH)
            return Fourth();
        throw new Exception("Erro: nao existe o posicao especificada");
    }
    
    public Point GetSidePositionPt(int pos) throws Exception{
        Point p;
        if(pos==FIRST){
            p=First();
            p.translate(0, -MIN_CONECTOR_DIS);
            return p;
        }
        if(pos==SECOND){
            p=Second();
            p.translate(0, MIN_CONECTOR_DIS);
            return p;
        }
        if(pos==THIRD){
            p=Third();
            p.translate(-MIN_CONECTOR_DIS, 0);
            return p;
        }
        if(pos==FOURTH){
            p=Fourth();
            p.translate(MIN_CONECTOR_DIS, 0);
            return p;
        }
        throw new Exception("Erro: nao existe o posicao especificada");
    }
    
    protected int getClosestExtreme(Point p){
        
        double dis = 999999999;
        int pos = UNKNOW;
            
        if ( p.distance( First() ) < dis && extremes[FIRST] ){
            dis = p.distance( First() );
            pos = FIRST;
        }        
        if ( p.distance( Third() ) < dis && extremes[THIRD] ){
            dis = p.distance( Third() );
            pos = THIRD;
        }
        if ( p.distance( Fourth() ) < dis && extremes[FOURTH] ){
            dis = p.distance( Fourth() );
            pos = FOURTH;
        }
        if ( p.distance( Second() ) < dis && extremes[SECOND] ){
            dis = p.distance( Second() );
            pos = SECOND;
        }
            
        return pos;
    }
        
//------------------------------------------------------
    public abstract void DrawShadow(Graphics g);
    public abstract void DrawShadow(Graphics g, Color c);
    public abstract void Draw(Graphics g);
    public abstract void Draw(Graphics g, Color c);
    
    public void DrawExtremes(Graphics g){
        g.drawRect(First().x-5, First().y-5, 10, 10);
        g.drawRect(Second().x-5, Second().y-5, 10, 10);
        g.drawRect(Third().x-5, Third().y-5, 10, 10);
        g.drawRect(Fourth().x-5, Fourth().y-5, 10, 10);
    }
    
    public void DrawComponents(Graphics gbi){
        
        gbi.setColor(Color.white);        
        gbi.fillOval(TopLeft().x-4, TopLeft().y-4, 8, 8);
        gbi.fillOval(TopRight().x-4, TopRight().y-4, 8, 8);
        gbi.fillOval(BottonLeft().x-4, BottonLeft().y-4, 8, 8);
        gbi.fillOval(BottonRight().x-4, BottonRight().y-4, 8, 8);
        
        if (Left().distance(Right())>24){
            gbi.fillOval(Top().x-4, Top().y-4, 8, 8);
            gbi.fillOval(Botton().x-4, Botton().y-4, 8, 8);
            gbi.setColor(Color.black);
            gbi.drawOval(Top().x-4, Top().y-4, 8, 8);
            gbi.drawOval(Botton().x-4, Botton().y-4, 8, 8);
        }
        if (Top().distance(Botton())>24){
            gbi.setColor(Color.white);
            gbi.fillOval(Right().x-4, Right().y-4, 8, 8);
            gbi.fillOval(Left().x-4, Left().y-4, 8, 8);
            gbi.setColor(Color.black);
            gbi.drawOval(Left().x-4, Left().y-4, 8, 8);
            gbi.drawOval(Right().x-4, Right().y-4, 8, 8);
        }                        
        
        gbi.setColor(Color.black);        
        gbi.drawOval(TopLeft().x-4, TopLeft().y-4, 8, 8);
        gbi.drawOval(TopRight().x-4, TopRight().y-4, 8, 8);
        gbi.drawOval(BottonLeft().x-4, BottonLeft().y-4, 8, 8);
        gbi.drawOval(BottonRight().x-4, BottonRight().y-4, 8, 8);
        
    }    
    
    public void DrawConectors(Graphics g){
        try {
            if ( Next != null )
                Next.DrawConector(g, true);
            if (IfTrue!=null)
                IfTrue.DrawConector(g, true);
            if (IfFalse!=null)
                IfFalse.DrawConector(g, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }        
    }
    
        
    public abstract boolean Contains(Point p);
    
    public boolean ContainsExtremes(Point p){
        
        return (p.x>First().x-10 && p.x<First().x+10 && p.y>First().y-10 && p.y<First().y+10) ||
               (p.x>Second().x-10 && p.x<Second().x+10 && p.y>Second().y-10 && p.y<Second().y+10) ||
               (p.x>Third().x-10 && p.x<Third().x+10 && p.y>Third().y-10 && p.y<Third().y+10) ||
               (p.x>Fourth().x-10 && p.x<Fourth().x+10 && p.y>Fourth().y-10 && p.y<Fourth().y+10);
        
    }   
    
    public void DrawSimpleText(Graphics g){
        
         //  Font font = new Font("Courrier New", Font.BOLD, 12);
         //  g.setFont(font);
        
            int base_y=(int)TopLeft.y-g.getFontMetrics().getDescent();
            base_y+=((BottonRight.y-TopLeft.y)-(g.getFontMetrics().getHeight()))/2;
            base_y+=g.getFontMetrics().getHeight();
            
            int start_x=(int)(Center.x-g.getFontMetrics().stringWidth(Text)/2);
            
            g.drawString(Text,start_x,base_y);                   
    }
    
    public void ResizeFromText(Graphics g, Font font){
        
        Point2D AuxCenter= new Point2D(Center);        
        
        g.setFont(font);
        int start_x=(int)(Center.x-g.getFontMetrics().stringWidth(Text)/2)-20;
        
        TopLeft.x=start_x;
        BottonRight.x=start_x+g.getFontMetrics().stringWidth(Text)+20;
        
        
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
    
    //------------------------------------------------------    
    public void Scale(double sx, double sy){
        TopLeft.Scale(sx, sy);
        BottonRight.Scale(sx, sy);        
        
        CalcCenterX();
        CalcCenterY();
        CalcFormPts();
    }
    
    public static void ScaleDefaultDis(double sd){
        MIN_CONECTOR_DIS = (int) (DEFAULT_MIN_CONECTOR_DIS * sd);
        Conector.ARROW_SIZE = (int) (Conector.DEFAULT_ARROW_SIZE * sd);
    }
    
    public void Translate(int dx, int dy){
        TopLeft.Translate((double)dx, (double)dy);
        BottonRight.Translate((double)dx, (double)dy);
        CalcCenterX();
        CalcCenterY();
        CalcFormPts();
    }
    
    public void TranslateConectors(int dx, int dy){
        
        if ( Next != null )
            Next.translate(dx, dy);
        if ( IfTrue != null )
            IfTrue.translate(dx, dy);
        if ( IfFalse != null )
            IfFalse.translate(dx, dy);        
    }
    
//------------------------------------------------------
    public String toString(){
        return new String("hashCode: "+hashCode()+"TopLeft: "+this.TopLeft+"  BottonRight: "+this.BottonRight);
    }
//---------------------------------------------------------------
    public boolean equals(Object obj) { 
        return this==obj;
    }
//---------------------------------------------------------------    
    public abstract Forma clone();


    
}