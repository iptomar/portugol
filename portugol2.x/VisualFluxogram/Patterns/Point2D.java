/*
 * Point2D.java
 *
 * Created on 10 de Abril de 2006, 1:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package VisualFluxogram.Patterns;

import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author Eduardo
 */
public class Point2D  implements Serializable {
    
    public double x;
    public double y;
    /** Creates a new instance of Point2D */
    public Point2D() {
        this.x=0;
        this.y=0;
    }
    
    public Point2D(double x, double y){
        this.x=x;
        this.y=y;
    }
    
    public Point2D(Point2D p){
        this.x = p.x;
        this.y = p.y;
    }
//-------------------------------------------------------
    public double X(){
        return this.x;
    }
        
    public double Y(){
        return this.y;
    }

    public void SetX(double x){
        this.x = x;
    }

    public void SetY(double y){
        this.y = y;
    }

    public void SetLocation(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public void SetLocation(Point2D p){
        this.x = p.x;
        this.y = p.y;
    }
    
   public void SetLocation(Point p){
        this.x = p.x;
        this.y = p.y;
    }

//-------------------------------------------------------
    public void Translate(double dx, double dy){
        this.x+=dx;
        this.y+=dy;
    }
    
    public void Scale(double sx, double sy){
        this.x*=sx;
        this.y*=sy;
    }

    public void Rotate(double angle){
        double x0 = this.x;
        double y0 = this.y;
        this.x = x0 * Math.cos(angle) - y0 * Math.sin(angle);
        this.y = x0 * Math.sin(angle) + y0 * Math.cos(angle);
    }

    public double DistanceTo(Point2D p){
        return Math.sqrt( Math.pow( this.x- p.X() , 2.0) + Math.pow(this.y-p.Y(), 2.0));
    }

//------------------------------------------------------
    public double Modulus(){
        return Math.sqrt(x*x + y*y);
    }

    public double Angle(){
        return Math.atan2(y, x);
    }

//------------------------------------------------------
    public Point toPoint(){
        return new Point((int)this.x, (int)this.y);    
    }
//------------------------------------------------------
    public Point2D clone(){
        return new Point2D(this.x, this.y);    
    }
//------------------------------------------------------
    public String toString(){
        return new String(""+this.x+", "+this.y);    
    }
//---------------------------------------------------------------
    public boolean equals(Object obj) { 
        if ((obj != null) & (obj instanceof Point2D)){
            return this.toString().equals(obj.toString());
        }
        return false;
    }
    
}
