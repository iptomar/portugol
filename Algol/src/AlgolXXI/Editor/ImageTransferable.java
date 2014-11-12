/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;

/**
 *
 * @author Apocas
 */
public class ImageTransferable implements Transferable {

    private String fileName;
    private Image imagef = null;

    public ImageTransferable(String fileName) {
        if (!new File(fileName).exists()) {
            throw new IllegalArgumentException("Can't find image");
        }

        this.fileName = fileName;
    }

    public ImageTransferable(Image imaged) {
        this.imagef = imaged;
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (!isDataFlavorSupported(flavor)) {
            throw new UnsupportedFlavorException(flavor);
        }

        if (imagef != null) {
            return imagef;
        }
        return Toolkit.getDefaultToolkit().createImage(fileName);
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return in(flavor, getTransferDataFlavors());
    }

    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{
                    DataFlavor.imageFlavor
                };
    }

    private boolean in(DataFlavor flavor, DataFlavor[] flavors) {
        int f = 0;

        while ((f < flavors.length) && !flavor.equals(flavors[f])) {
            f++;
        }

        return f < flavors.length;
    }
}