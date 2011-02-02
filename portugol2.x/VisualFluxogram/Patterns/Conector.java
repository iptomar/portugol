/*
 * Conector.java
 *
 * Created on 4 de Julho de 2006, 23:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package VisualFluxogram.Patterns;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author Eduardo
 */
public class Conector implements Serializable {
    
    public static int ARROW_SIZE       =  7;
    public static final int DEFAULT_ARROW_SIZE       =  7;
    
    public Forma Orig = null;
    public Forma Dest = null;
    public int OrigPos = 1;
    public int DestPos = 0;
    public Point MiddlePt;
    
    /** Creates a new instance of Conector */
    public Conector(Forma Initial) {
        Orig = Initial;
    }
    
    public Forma Pointer(){
        return Dest;
    }
    
    public int InitialPos(){
        return OrigPos;
    }
    
    public int FinalPos(){
        return DestPos;
    }
    
    public void SetPointer(Forma dest){
        Dest = dest;
    }
    
    public void translate(int dx, int dy){
        MiddlePt.translate(dx, dy);
    }
    
    public boolean Contains(Point p){
        
        Point InitialPt = new Point();
        Point AInitialPt = new Point();
        Point BFinalPt = new Point();
        Point FinalPt = new Point();
        try {
            
            InitialPt = Orig.GetPositionPt(OrigPos);
            AInitialPt = Orig.GetSidePositionPt(OrigPos);
            BFinalPt = Dest.GetSidePositionPt(DestPos);
            FinalPt = Dest.GetPositionPt(DestPos);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        if ( 
                (Math.abs(p.x-InitialPt.x)<6 && p.y>Math.min(AInitialPt.y, InitialPt.y) && p.y<Math.max(AInitialPt.y, InitialPt.y)) ||
                (Math.abs(p.y-InitialPt.y)<6 && p.x>Math.min(AInitialPt.x, InitialPt.x) && p.x<Math.max(AInitialPt.x, InitialPt.x)) ||
                
                (Math.abs(p.x-MiddlePt.x)<6 && p.y>Math.min(AInitialPt.y, MiddlePt.y) && p.y<Math.max(AInitialPt.y, MiddlePt.y)) ||
                (Math.abs(p.y-MiddlePt.y)<6 && p.x>Math.min(AInitialPt.x, MiddlePt.x) && p.x<Math.max(AInitialPt.x, MiddlePt.x)) ||
                (Math.abs(p.x-MiddlePt.x)<6 && p.y>Math.min(MiddlePt.y, BFinalPt.y) && p.y<Math.max(MiddlePt.y, BFinalPt.y)) ||
                (Math.abs(p.y-MiddlePt.y)<6 && p.x>Math.min(MiddlePt.x, BFinalPt.x) && p.x<Math.max(MiddlePt.x, BFinalPt.x)) ||
                (p.x>FinalPt.x-10 && p.x<FinalPt.x+10 && p.y>FinalPt.y-10 && p.y<FinalPt.y+10)   ){
            
            
            return true;
        }
        return false;
        
    }
    
    ////////////////////////////////////////////////////////////////////////////
    public void SetInitialClosestExtreme(Point p){
        OrigPos = Orig.getClosestExtreme(p);
    }    
    
    public void SetFinalClosestExtreme(Point p){
        DestPos = Dest.getClosestExtreme(p);
    }
    
    public void SetClosestExtremes(){
        
        SetInitialClosestExtreme( Dest.Center.toPoint() );
        try {
            SetFinalClosestExtreme( Orig.GetPositionPt(OrigPos) );
            MiddlePt=CalcMiddlePt();
        } catch (Exception ex) {
            ex.printStackTrace();
        }        
    } 
    
    public void AutoSetInitialExtreme(){
        if ( Orig.extremes[Forma.SECOND] )
            OrigPos = Forma.SECOND;
        else
            SetInitialClosestExtreme( Orig.Center.toPoint() );
    }
    
    public void AutoSetFinalExtreme(){
        if ( this == Orig.Next )
            AutoSetNext_FinalExtreme();
        if ( this == Orig.IfTrue )
            AutoSetDecision_FinalExtreme(Forma.FOURTH, Forma.THIRD);
        if ( this == Orig.IfFalse )
            AutoSetDecision_FinalExtreme(Forma.THIRD, Forma.FOURTH);
    }
    
    //
    
    private void AutoSetNext_FinalExtreme(){        
        if ( Dest instanceof Decisao && !Dest.extremes[Forma.FIRST] && Dest.extremes[Forma.SECOND])
            DestPos=Forma.SECOND;
        else if (Dest instanceof Conexao){
            
            if ( Orig.Center.x - Dest.Center.x > Forma.MIN_CONECTOR_DIS && Dest.extremes[Forma.FOURTH] )
                DestPos = Forma.FOURTH;
            else if ( Orig.Center.x - Dest.Center.x < -Forma.MIN_CONECTOR_DIS && Dest.extremes[Forma.THIRD] )
                DestPos = Forma.THIRD;
            else if (Dest.extremes[Forma.FIRST])
                DestPos = Forma.FIRST;
            else
                SetFinalClosestExtreme( Dest.Center.toPoint() );
            
        }else if (Dest.extremes[Forma.FIRST])
            DestPos=Forma.FIRST;
        else
            SetFinalClosestExtreme( Dest.Center.toPoint() );
    }
    
    private void AutoSetDecision_FinalExtreme(int Correct, int Opposite){
        
        if (Orig.extremes[Correct])
            OrigPos=Correct;
        
        if (Dest instanceof Conexao && VisualFluxogram.Parser.ParseStructure.Intersects(Dest, Orig, null) ){
            
            if (Orig.extremes[Opposite])
                OrigPos=Opposite;
            
            if (Dest.extremes[Opposite])
                DestPos=Opposite;
            else
                SetFinalClosestExtreme( Dest.Center.toPoint() );
            
        }else if (Dest instanceof Conexao){
            
            if(!Orig.extremes[Forma.SECOND] && Dest.extremes[Forma.FIRST])
                DestPos=Forma.FIRST;
            else if (Dest.extremes[Correct])
                DestPos=Correct;
            else
                SetFinalClosestExtreme( Dest.Center.toPoint() );
            
        }else if (Dest.extremes[Forma.FIRST])
            DestPos=Forma.FIRST;
        else
            SetFinalClosestExtreme( Dest.Center.toPoint() );
    }
    
    
    public void BlockConector(){        
        if ( OrigPos != Forma.UNKNOW)
            Orig.extremes[OrigPos] = false;
        if ( DestPos != Forma.UNKNOW && !(Dest instanceof Conexao) )
            Dest.extremes[DestPos]=false;
    }
    
    public void UnBlockConector(){
        if ( OrigPos != Forma.UNKNOW)
            Orig.extremes[OrigPos]=true;
        if ( DestPos != Forma.UNKNOW && !(Dest instanceof Conexao) )
            Dest.extremes[DestPos]=true;
    }
    
//------------------------------------------------------
    public Point CalcMiddlePt() throws Exception{
        
        Point IniSidePt=Orig.GetSidePositionPt(OrigPos);
        Point FinSidePt=Dest.GetSidePositionPt(DestPos);
        
        Point MiddlePt=new Point(IniSidePt.x, FinSidePt.y);
                
        if ( OrigPos == Forma.FOURTH ){
            
            if( FinSidePt.x < IniSidePt.x ||
              (DestPos == Forma.FIRST && FinSidePt.y < IniSidePt.y) || (DestPos == Forma.SECOND && FinSidePt.y > IniSidePt.y) )
                MiddlePt.setLocation(IniSidePt.x, FinSidePt.y);
            else
                MiddlePt.setLocation(FinSidePt.x, IniSidePt.y);
                
        }else if ( OrigPos == Forma.THIRD ){            
            
            if( FinSidePt.x > IniSidePt.x ||
              (DestPos == Forma.FIRST && FinSidePt.y < IniSidePt.y) || (DestPos == Forma.SECOND && FinSidePt.y > IniSidePt.y) )
                MiddlePt.setLocation(IniSidePt.x, FinSidePt.y);
            else
                MiddlePt.setLocation(FinSidePt.x, IniSidePt.y);
                
        }else if ( OrigPos == Forma.FIRST ){
            
            if( FinSidePt.y > IniSidePt.y ||
              (DestPos == Forma.THIRD && FinSidePt.x < IniSidePt.x) || (DestPos == Forma.FOURTH && FinSidePt.x > IniSidePt.x) )
                MiddlePt.setLocation(FinSidePt.x, IniSidePt.y);
            else
                MiddlePt.setLocation(IniSidePt.x, FinSidePt.y);
                
        }else if ( OrigPos == Forma.SECOND ){
            
            if( FinSidePt.y < IniSidePt.y ||
              (DestPos == Forma.THIRD && FinSidePt.x < IniSidePt.x) || (DestPos == Forma.FOURTH && FinSidePt.x > IniSidePt.x) )
                MiddlePt.setLocation(FinSidePt.x, IniSidePt.y);
            else
                MiddlePt.setLocation(IniSidePt.x, FinSidePt.y);
        
        }        
        
        return MiddlePt;
    }   
    
    public void CalcConector(int InitialOutPos, int FinalInPos){
        
        try {
            OrigPos=InitialOutPos;
            DestPos=FinalInPos;
            MiddlePt = CalcMiddlePt();            
            Orig.extremes[OrigPos]=false;
            if (!(Dest instanceof Conexao))
                Dest.extremes[DestPos]=false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }            
    }
    
    public void CalcDrawConector(Graphics g){        
        CalcConector();
        try {
            DrawConector(g, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void DrawConector(Graphics g){        
        try {
            DrawConector(g, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void CalcConector(){
        try {
            MiddlePt=CalcMiddlePt();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void DrawConector(Graphics g, boolean arrow) throws Exception{
        
        Point InitialPt=Orig.GetPositionPt(OrigPos);
        Point AfterInitialPt=Orig.GetSidePositionPt(OrigPos);
        Point BeforeFinalPt=Dest.GetSidePositionPt(DestPos);
        Point FinalPt=Dest.GetPositionPt(DestPos);
        
        g.setColor(Color.black);        
        g.drawLine(InitialPt.x, InitialPt.y , AfterInitialPt.x, AfterInitialPt.y);
        g.drawLine(AfterInitialPt.x, AfterInitialPt.y , MiddlePt.x, MiddlePt.y);
        g.drawLine(MiddlePt.x, MiddlePt.y, BeforeFinalPt.x, BeforeFinalPt.y);
        g.drawLine(BeforeFinalPt.x, BeforeFinalPt.y , FinalPt.x, FinalPt.y);
        
        if (!arrow)
            return;
       
       //desenhar texto
       if ( this != Orig.Next ){
            
            //Font font = new Font("Serif", Font.PLAIN, 15);
            //g.setFont(font);
            String str;
            if ( this == Orig.IfTrue )
                str="sim";
            else
                str="não";
            
            Point aux;
            if ( AfterInitialPt.distance(MiddlePt) > MiddlePt.distance(BeforeFinalPt) )
                aux=AfterInitialPt;
            else
                aux=BeforeFinalPt;
            
            if (aux.x==MiddlePt.x){
                if ( this == Orig.IfTrue )
                    g.drawString(str, MiddlePt.x+5, (aux.y + MiddlePt.y + g.getFontMetrics().getHeight())/2 );
                else
                    g.drawString(str, MiddlePt.x-4-g.getFontMetrics().stringWidth(str), (aux.y + MiddlePt.y + g.getFontMetrics().getHeight())/2 );
            }else
                g.drawString(str, (aux.x + MiddlePt.x - g.getFontMetrics().stringWidth(str))/2, MiddlePt.y-5);
            
        }        
        
        //desenhar setas
        int []xPoints=new int[3];
        int []yPoints=new int[3];
        xPoints[0]=FinalPt.x;
        yPoints[0]=FinalPt.y;
        
        if ( DestPos == Forma.FIRST ){            
            xPoints[1] = FinalPt.x - ARROW_SIZE;
            yPoints[1] = FinalPt.y - ARROW_SIZE;
            xPoints[2] = FinalPt.x + ARROW_SIZE;
            yPoints[2] = FinalPt.y - ARROW_SIZE;
            
        }else if ( DestPos == Forma.SECOND ){
            xPoints[1] = FinalPt.x - ARROW_SIZE;
            yPoints[1] = FinalPt.y + ARROW_SIZE;
            xPoints[2] = FinalPt.x + ARROW_SIZE;
            yPoints[2] = FinalPt.y + ARROW_SIZE;
            
        }else if ( DestPos == Forma.THIRD ){
            xPoints[1] = FinalPt.x - ARROW_SIZE;
            yPoints[1] = FinalPt.y - ARROW_SIZE;
            xPoints[2] = FinalPt.x - ARROW_SIZE;
            yPoints[2] = FinalPt.y + ARROW_SIZE;
            
        }else if ( DestPos == Forma.FOURTH ){
            xPoints[1] = FinalPt.x + ARROW_SIZE;
            yPoints[1] = FinalPt.y - ARROW_SIZE;
            xPoints[2] = FinalPt.x + ARROW_SIZE;
            yPoints[2] = FinalPt.y + ARROW_SIZE;
        }
        
        g.fillPolygon(xPoints, yPoints, 3);
    }
    
    public Conector clone() {
        
        Conector newConector = new Conector(Orig);        
        newConector.Orig = Orig;
        newConector.Dest = Dest;
        newConector.OrigPos = OrigPos;
        newConector.DestPos = DestPos;
        newConector.MiddlePt = new Point(MiddlePt);
        
        return newConector;
    }
    
}
