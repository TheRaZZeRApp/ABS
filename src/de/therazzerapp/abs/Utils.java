package de.therazzerapp.abs;

import java.io.File;
import java.net.URISyntaxException;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class Utils {

    public static boolean pdftotxtExists(){
        String path = null;
        try {
            path = ABS.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        path = path.replaceAll("[^\\/]+\\.jar","");
        return new File(path + "pdftotext.exe").exists();
    }
}
