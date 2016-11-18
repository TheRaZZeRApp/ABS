package de.therazzerapp.abs;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class FileSaver {
    public static void save(String content, File file){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            file.createNewFile();
            outputStreamWriter.write(content);
            outputStreamWriter.close();

        } catch (IOException e) {
        }
    }
}
