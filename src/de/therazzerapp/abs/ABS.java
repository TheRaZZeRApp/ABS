package de.therazzerapp.abs;

import de.therazzerapp.abs.manager.ErrorManager;
import de.therazzerapp.abs.manager.MitarbeiterManager;
import de.therazzerapp.abs.content.loader.MitarbeiterLoader;


import java.io.File;


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

        /*
        try {
            Process process = rt.exec("pdftotxt.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        MitarbeiterManager.load(MitarbeiterLoader.readContent(FileReader.getFileContent(file)));
        MitarbeiterManager.export(args[0]);
        MitarbeiterManager.createMitarbeiter();
    }

    public static String[] getArgs() {
        return args;
    }
}
