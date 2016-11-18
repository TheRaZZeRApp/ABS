package de.therazzerapp.abs.content.saver;

import de.therazzerapp.abs.content.CSVFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class CSVSaver {
    public static void save(CSVFile csvFile, String path){
        File file = new File(path);

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String convertCSV(CSVFile csvFile){
        StringBuilder stringBuilder = new StringBuilder();

        for (String h : csvFile.getHeader()) {
            stringBuilder.append(h + ";");
        }

        stringBuilder.replace(stringBuilder.length()-2,stringBuilder.length()-1,"\n");

        for (List<String> stringList : csvFile.getContentList()) {
            for (String s : stringList) {
                stringBuilder.append(s + ";");
            }
            stringBuilder.replace(stringBuilder.length()-2,stringBuilder.length()-1,"\n");
        }
        return stringBuilder.toString();
    }
}
