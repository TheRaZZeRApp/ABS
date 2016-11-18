package de.therazzerapp.abs;

import de.therazzerapp.abs.manager.ErrorManager;
import de.therazzerapp.abs.manager.MitarbeiterManager;
import de.therazzerapp.abs.content.loader.MitarbeiterLoader;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;


/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class ABS {
    private static String[] args;
    public static void main(String[] a) {
        args = a;
        if (args.length <= 0){
            System.exit(0);
        }

        File file = new File(args[0]);
        if (!file.exists()){
            ErrorManager.writeError("Die Datei: \"" + ABS.getArgs()[0] + "\" wurde nicht gefunden!");
            System.exit(0);
        }

        Runtime rt = Runtime.getRuntime();
        try {
            String path = ABS.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            path.replaceAll("ABS.jar","");
            path.replaceAll("/C:/","C:/");
            try {
                Process process = rt.exec(path + "pdftotxt.exe -raw -nopgbrk " + args[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (URISyntaxException e) {

        }

        MitarbeiterManager.load(MitarbeiterLoader.readContent(FileReader.getFileContent(file)));
        MitarbeiterManager.export(args[0]);
        MitarbeiterManager.createMitarbeiter();
    }

    public static String[] getArgs() {
        return args;
    }
}
