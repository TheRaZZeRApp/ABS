package de.therazzerapp.abs.manager;

import de.therazzerapp.abs.ABS;
import de.therazzerapp.abs.FileSaver;

import java.io.File;
import java.util.List;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class ErrorManager {

    private static final StringBuilder errorLog = new StringBuilder("Mitarbeiterbelastungs Skript Error Log:\n");

    public static void writeError(String error){
        errorLog.append("Error: " + error + "\n");
    }

    public static void writeError(List<String> errors){
        for (String error : errors) {
            writeError(error);
        }
    }

    public static void saveError(String path){
        FileSaver.save(errorLog.toString(),new File(path + "_ERROR.log"));
    }

    public static boolean isError(){
        return errorLog.length() > 42;
    }
}
