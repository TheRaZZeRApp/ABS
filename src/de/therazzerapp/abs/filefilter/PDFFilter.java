package de.therazzerapp.abs.filefilter;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class PDFFilter extends FileFilter{
    @Override
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".pdf");
    }

    @Override
    public String getDescription() {
        return "Pdf File";
    }
}
