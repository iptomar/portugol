/*
 * ScrollArea.java
 *
 * Created on 1 de Julho de 2006, 0:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package VisualFluxogram.GUI;


import VisualFluxogram.Patterns.Forma;
import VisualFluxogram.Patterns.Processo;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;
import java.util.Vector;
import javax.swing.JPanel;

/**
 *
 * @author Eduardo
 */
public class ScrollArea extends JPanel{
    
    Rectangle VisibleWindow=this.getVisibleRect();
    
    Vector AllForms = new Vector();    
    Vector SelectedForms = new Vector();
    Forma Selected = null;
    private Color SelectedColor = Color.GREEN;
    
    float alpha = 1.0f;
    
    int FontSize = 20;
    Font font = new Font("Papyrus", Font.BOLD, 20);
    private Color FontColor = Color.BLACK;
    
    int GridHoriz=50;
    int GridVert=30;
    boolean GridOn=false;
    /** Creates a new instance of ScrollArea */
    public ScrollArea() {
        this.setBackground(new Color(244,225,147));
        
    }
    
    public void setPatterns( Vector All, Vector Selected){
        AllForms = All;
        SelectedForms = Selected;
    }
    
    public void setAlphaComposite(float ac){
        alpha=ac;
    }
    public float getAlphaComposite(){
        return alpha;
    }
    
    public void setGridSize(int Horiz, int Vert){
        GridHoriz=Horiz;
        GridVert=Vert;
    }
    
    public void setGridEnabled(boolean b){
        GridOn=b;
    }
    
    public boolean isGridEnabled(){
        return GridOn;
    }
    
    public void setFont(String type, int style, int size){
        FontSize = size;
        font = new Font(type, style, size);
    }
    
    public Font getFont(){
        return font;
    }
    
    public void setFontColor(Color color){
        FontColor = color;
    }
    public Color getFontColor(){
        return FontColor;
    }
    
    public int getFontSize(){
        return FontSize;
    }
    
    public void setFontSize(int size){
        FontSize = size;
        font = new Font(font.getFamily(), font.getStyle(), size);
    }
    
    public void setFontFamily(String family){
        font = new Font(family, font.getStyle(), font.getSize());
    }
    
    public void setFontStyle(int style){
        font = new Font(font.getFamily(), style, font.getSize());
    }
    
    public void ScaleFontSize(double s){
        font = new Font(font.getFamily(), font.getStyle(), (int)(FontSize*s) );
    }
    
    public void setSelected(Forma selected){
        Selected = selected;
    }
    
    public void setSelectedColor(Color color){
        SelectedColor = color;
    }
    
    /*public void update( Graphics g ) {
        paint(g);
    }*/
         
    public void paint( Graphics g ) {   
        
        g.setColor(this.getBackground());
        VisibleWindow=this.getVisibleRect();
        
        //GradientPaint gradient = new GradientPaint(VisibleWindow.x, VisibleWindow.y, Color.BLUE, VisibleWindow.x+VisibleWindow.width, VisibleWindow.y+VisibleWindow.height, Color.GREEN);
        //((Graphics2D)g).setPaint(gradient);
        //g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.fillRect(VisibleWindow.x, VisibleWindow.y, VisibleWindow.width, VisibleWindow.height);
        
        if (GridOn)
            DrawGrid(g);
        
        // Set alpha.  0.0f is 100% transparent and 1.0f is 100% opaque.
        if (alpha!=1.0f)
            ((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        
        
        

        g.setFont(font);        
        for(int i=0; i<AllForms.size(); i++){
            
            if ( AllForms.get(i) == Selected )
                ((Forma)(AllForms.get(i))).Draw(g, SelectedColor);
            else
                ((Forma)(AllForms.get(i))).Draw(g);            
            ((Forma)(AllForms.get(i))).DrawConectors(g);
            g.setColor(FontColor);
            ((Forma)(AllForms.get(i))).DrawSimpleText(g);
        }
        
        //if (Selected != null)
        //    Selected.DrawShadow(g, SelectedColor);
        
        
        for(int i=0; i<SelectedForms.size(); i++)
            ((Forma)SelectedForms.get(i)).DrawComponents(g);         
       
    }
    
    private void DrawGrid(Graphics g){
        
        //retirar o multiplos do espacamento ficando o resto na parte fraccionaria
        double razao=VisibleWindow.x/GridHoriz;            
        //calcular sitio onde comeca a desenhar
        int HorizIni=(int)( VisibleWindow.x-((int)razao*GridHoriz) );
        //comecar 1 coluna antes
        HorizIni=VisibleWindow.x-HorizIni;
        //sitio onde termina o desenho na Horizontal
        int HorizFin=VisibleWindow.x+VisibleWindow.width;
           
        //retirar o multiplos do espacamento ficando o resto na parte fraccionaria
        razao=VisibleWindow.x/GridHoriz;            
        //calcular sitio onde comeca a desenhar
        int VertIni=(int)( VisibleWindow.y-((int)razao*GridVert) );
        //comecar 1 linha antes
        VertIni=VisibleWindow.y-VertIni;
        //sitio onde termina o desenho na Vertical
        int VertFin=VisibleWindow.y+VisibleWindow.height;
        
        g.setColor(Color.LIGHT_GRAY);
        for (int i=HorizIni; i<HorizFin; i+=GridHoriz)
            g.drawLine(i, VisibleWindow.y, i, VertFin);
        
        for (int i=VertIni;i<VertFin;i+=GridVert)
            g.drawLine(VisibleWindow.x, i, HorizFin, i);
    }
    
    /*public Dimension getPreferredSize() {
        return size;
    }*/
    
}
