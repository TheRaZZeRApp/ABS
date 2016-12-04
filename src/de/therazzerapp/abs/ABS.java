package de.therazzerapp.abs;

import de.therazzerapp.abs.content.PDFtoTXTThread;
import de.therazzerapp.abs.gui.StartUI;
import de.therazzerapp.abs.manager.ErrorManager;
import de.therazzerapp.abs.manager.EmployeeManager;
import de.therazzerapp.abs.content.loader.EmployeeLoader;


import java.io.File;


/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class ABS {
    private static String[] args;
    private final static String version = "0.1";

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
                System.out.println("Die Datei: \"" + ABS.getArgs()[0] + "\" wurde nicht gefunden!");
                System.exit(0);
            }

            if (!Utils.pdftotxtExists()){
                ErrorManager.writeError("pdftotext.exe wurde nicht gefunden");
                System.out.println("pdftotext.exe wurde nicht gefunden");
                System.exit(0);
            }

            run(file, keepTXT, args[0]);
        }
    }

    public static void run(File file, boolean keepTXT, String path){

        if (!file.getName().endsWith(".pdf")){
            System.out.println("\nError: Angegebene Datei ist keine PDF");
            ErrorManager.writeError("Angegebene Datei ist keine PDF");
            return;
        }

        PDFtoTXTThread pdFtoTXTThread = new PDFtoTXTThread(file);
        pdFtoTXTThread.start();
        File txt = new File(file.getAbsolutePath().replaceAll("\\.pdf",".txt"));
        try {
            pdFtoTXTThread.join();

            EmployeeManager.load(EmployeeLoader.readContent(FileReader.getFileContent(txt)));
            EmployeeManager.exportCSV(txt.getAbsolutePath());
        } catch (InterruptedException ignored) {

        }

        if (!keepTXT){
            txt.delete();
        }

        if (ErrorManager.isError()){
            ErrorManager.saveError(path);
        }
    }

    public static String[] getArgs() {
        return args;
    }
}
