package de.therazzerapp.abs;

import de.therazzerapp.abs.content.PDFtoTXTThread;
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
        } else {
            boolean keepTXT = false;
            if (args.length > 1){
                if (args[1].equals("true")){
                    keepTXT = true;
                }
            }

            File file = new File(args[0]);
            if (!file.exists()){
                ErrorManager.writeError("Die Datei: \"" + ABS.getArgs()[0] + "\" wurde nicht gefunden!");
                System.exit(0);
            }

            if (!Utils.pdftotxtExists()){
                ErrorManager.writeError("pdftotext.exe wurde nicht gefunden");
                System.exit(0);
            }

            run(file, keepTXT);
        }
    }

    public static void run(File file, boolean keepTXT){

        PDFtoTXTThread pdFtoTXTThread = new PDFtoTXTThread(file);
        pdFtoTXTThread.start();
        File txt = new File(file.getAbsolutePath().replaceAll("\\.pdf",".txt"));
        try {
            pdFtoTXTThread.join();

            MitarbeiterManager.load(MitarbeiterLoader.readContent(FileReader.getFileContent(txt)));
            MitarbeiterManager.exportCSV(txt.getAbsolutePath());
        } catch (InterruptedException ignored) {

        }

        if (!keepTXT){
            txt.delete();
        }
    }

    public static String[] getArgs() {
        return args;
    }
}
