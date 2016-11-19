package de.therazzerapp.abs;

import de.therazzerapp.abs.gui.StartUI;
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
    private static String version = "0.1";

    public static String getVersion() {
        return version;
    }

    public static void main(String[] a) {
        args = a;
        if (args.length <= 0){
            StartUI.run();
        }

        File file = new File(args[0]);
        if (!file.exists()){
            ErrorManager.writeError("Die Datei: \"" + ABS.getArgs()[0] + "\" wurde nicht gefunden!");
            System.exit(0);
        }

        run(file);

    }

    public static void run(File file){
        Runtime rt = Runtime.getRuntime();
        try {
            String path = ABS.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            path = path.replaceAll("[^\\/]+\\.jar","");
            path = path.replaceFirst("/","");
            try {
                Process process = rt.exec(path + "pdftotext.exe -raw -nopgbrk " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (URISyntaxException ignored) {

        }

        MitarbeiterManager.load(MitarbeiterLoader.readContent(FileReader.getFileContent(file)));
        MitarbeiterManager.exportCSV(file.getAbsolutePath().replaceAll("\\.pdf",".txt"));
        MitarbeiterManager.createMitarbeiter();
    }

    public static String[] getArgs() {
        return args;
    }
}
