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

    private static final String errorLog =
            "Mitarbeiterbelastungs Skript Error Log:\n";

    public static void writeError(String error){
        saveError("Error: " + error + "\n");
    }

    public static void writeError(List<String> errors){
        StringBuilder sb = new StringBuilder();
        sb.append(errorLog);
        for (String error : errors) {
            sb.append("Error: " + error + "\n");
        }
        saveError(sb.toString());
    }

    private static void saveError(String t){
        FileSaver.save(t,new File(ABS.getArgs()[0] + "_ERROR.log"));
    }
}
