/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor.Utils;

import AlgolXXI.Editor.Fluxogramas.FluxogramaPanel;
import AlgolXXI.Editor.ProgramaTopComponent;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import org.openide.windows.TopComponent;

/**
 * Classe que permite carregar as propridades referentes ao editor AlgoXXI - língua.
 * @author Apocas
 */
public class EditorUtils {

    public static int JAVA = 0;
    public static int C = 1;

    /**
     * Método que carrega o idioma do editor. NAO ESTA A SER UTILIZADO
     * @return String com o tipo do idioma seleccionado.
     */
    public static String getLanguage() {
        String FILE_PATH = "Algol/src/AlgolXXI/Editor/lang.conf";
        File file = new File(FILE_PATH);
        String lang = "";

        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            lang = input.readLine().toUpperCase();
        } catch (Exception e) {
            BufferedReader input = null;
            try {
                FILE_PATH = "lang.conf";
                file = new File(FILE_PATH);
                input = new BufferedReader(new FileReader(file));
                lang = input.readLine().toUpperCase();
            } catch (Exception ex) {
                lang = "pt";
            }
        }

        System.out.println("Idioma Conf: " + lang);
        return lang;
    }

    /**
     * Método que permite comparar duas imagens pixel e pixel.
     * @param image1 Image
     * @param image2 Image
     * @return true se as imagens forem iguais, false se não forem.
     */
    public static boolean compare_images(Image image1, Image image2) {
        int width = 73;
        int height = 73;

        int[] pixels1 = image1 != null ? new int[width * height] : null;
        if (image1 != null) {
            try {
                PixelGrabber pg = new PixelGrabber(image1, 0, 0, width, height, pixels1, 0, width);
                pg.grabPixels();
                if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
                }
            } catch (InterruptedException e) {
            }
        }

        int[] pixels2 = image2 != null ? new int[width * height] : null;
        if (image2 != null) {
            try {
                PixelGrabber pg = new PixelGrabber(image2, 0, 0, width, height, pixels2, 0, width);
                pg.grabPixels();
                if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
                }
            } catch (InterruptedException e) {
            }
        }
        for (int i = 0; i < (width * height); i++) {
            if (pixels1[i] != pixels2[i]) {
                return false;
            }
        }

        return true;
    }

    public static boolean containsChars(String s, char[] a) {
        for (int i = 0; i < s.length(); i++) {
            if (contains(s.charAt(i), a)) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(char c, char[] a) {
        for (int i = 0; i <
                a.length; i++) {
            if (c == a[i]) {
                return true;
            }

        }
        return false;
    }
    /*
    public static int findTabID(JTabbedPane fluxos, int blockid) {
    try {
    for (int i = 0; i < fluxos.getComponents().length; i++) {
    FluxogramaPanel aux = (FluxogramaPanel) fluxos.getComponent(i);
    if (aux.getBloco().getFlux().getBegin().getBlockID() == blockid) {
    return i;
    }
    }
    } catch (Exception ex) {
    }
    return -1;
    }
    
    public static FluxogramaPanel findTabComponent(JTabbedPane fluxos, int blockid) {
    try {
    for (int i = 0; i < fluxos.getComponents().length; i++) {
    FluxogramaPanel aux = (FluxogramaPanel) fluxos.getComponent(i);
    if (aux.getBloco().getMainMemory().getFlux().getStart().getBlockID() == blockid) {
    return aux;
    }
    }
    } catch (Exception ex) {
    }
    return null;
    }
     */

    public static boolean isDialogOpened() {
        for (TopComponent node : TopComponent.getRegistry().getOpened()) {
            if (node instanceof ProgramaTopComponent && ((ProgramaTopComponent) node).isDialogOpened()) {
                return true;
            }
        }
        return false;
    }

    public static void setDialogOpened(boolean flag) {
        for (TopComponent node : TopComponent.getRegistry().getOpened()) {
            if (node instanceof ProgramaTopComponent) {
                ((ProgramaTopComponent) node).setDialogOpened(flag);
            }
        }
    }

    public static ProgramaTopComponent getTopComponent() {
        Locale currentLocale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("AlgolXXI/Editor/Bundle", currentLocale);
        TopComponent tc = TopComponent.getRegistry().getActivated();
        if (tc instanceof ProgramaTopComponent) {
            return (ProgramaTopComponent) tc;
        } else {
            if (tc == null) {
                for (int i = TopComponent.getRegistry().getOpened().size() - 1; i >= 0; i--) {
                    if (TopComponent.getRegistry().getOpened().toArray()[i] instanceof ProgramaTopComponent) {
                        return (ProgramaTopComponent) TopComponent.getRegistry().getOpened().toArray()[i];
                    }
                }
            }
            Toolkit.getDefaultToolkit().beep();
            if (TopComponent.getRegistry().getOpened().size() == 0) {
                JOptionPane.showMessageDialog(null, bundle.getString("Crie_programa"));
            } else {
                JOptionPane.showMessageDialog(null, bundle.getString("Selecione_programa"));
            }

        }
        return null;
    }
}
