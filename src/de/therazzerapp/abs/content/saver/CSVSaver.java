package de.therazzerapp.abs.content.saver;

import de.therazzerapp.abs.FileSaver;
import de.therazzerapp.abs.content.CSVFile;

import java.io.File;
import java.util.List;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class CSVSaver {
    public static void save(CSVFile csvFile, String path){
        path = path.replaceAll("\\.txt",".csv");
        FileSaver.save(convertCSV(csvFile),new File(path));
        System.out.println("Tabelle erstellt: " + path);
    }

    private static String convertCSV(CSVFile csvFile){
        if (csvFile == null){
            return "";
        }
        if (csvFile.getHeader() == null){
            return "";
        }
        if (csvFile.getContentList() == null){
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (String h : csvFile.getHeader()) {
            stringBuilder.append(h + ";");
        }
        stringBuilder.replace(stringBuilder.length()-1,stringBuilder.length(),"\n");

        for (List<String> strings : csvFile.getContentList()) {
            for (String string : strings) {
                stringBuilder.append(string + ";");
            }
            stringBuilder.replace(stringBuilder.length()-1,stringBuilder.length(),"\n");
        }

        return stringBuilder.toString();
    }
}
