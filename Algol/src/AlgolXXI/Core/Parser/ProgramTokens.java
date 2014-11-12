/*****************************************************************************/
/****     Copyright (C) 2008                                              ****/
/****     Antonio Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politecnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/*****************************************************************************/
/**
 * Created : 23/Mai/2008 - 15:30:08
 * @author Antonio M@nso
 */
package AlgolXXI.Core.Parser;

import java.util.Vector;

public class ProgramTokens {

    private Vector<LineTokens> lines;
    private int programType;

    public ProgramTokens() {
        lines = new Vector<LineTokens>();
    }

    public void addLine(LineTokens l) {
        // so com um elemento é o \n
     
        
        l.calculateInstructionType();
        if (l.getElements().size() > 1) {
            lines.add(l);
        }
    }

    public void classifyLines() {
        for (LineTokens line : lines) {
            line.calculateInstructionType();
        }

    }

    public Vector<LineTokens> getLines() {
        if (lines.size() > 0) {
            setProgramType(lines.get(0).getTypeIntruction());
        }
        return lines;
    }
    
    public LineTokens getLastLine(){
        return lines.get(lines.size()-1);
    }
    public LineTokens getLine(int index){
        return lines.get(index);
    }

    @Override
    public String toString() {
        StringBuffer txt = new StringBuffer();
        for (LineTokens l : lines) {
            txt.append(l.toString() + "\n");
        }
        return txt.toString();
    }

    public int getProgramType() {
        return lines.get(0).getTypeIntruction();
    }

    public void setProgramType(int programType) {
        this.programType = programType;
    }
}
