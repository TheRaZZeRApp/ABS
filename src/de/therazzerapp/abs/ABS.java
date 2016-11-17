package de.therazzerapp.abs;

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
    public static void main(String[] args) {
        if (args.length <= 0){
            //System.exit(0);
        }
        //String pdfFile = args[0];

        //Convert PDF to TXT
        Runtime rt = Runtime.getRuntime();

        /*
        try {
            Process process = rt.exec("pdftotxt.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        //Set<String> content = FileReader.getFileContent(new File(args[0]));
        File file = new File("C:\\Users\\Computer\\Desktop\\Script\\Script\\Belastung.txt");

        MitarbeiterManager.load(MitarbeiterLoader.readContent(FileReader.getFileContent(file)));
        MitarbeiterManager.createMitarbeiter();
    }


}
