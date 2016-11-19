package de.therazzerapp.abs;


import java.io.*;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
class FileReader {
    static List<String> getFileContent(File file){
        List<String> contentList = new LinkedList<>();
        BufferedReader br;
        InputStream is = null;
        InputStreamReader isr;
        try {
            is = new FileInputStream(file);
            isr = new InputStreamReader(is, Charset.forName("Cp1252"));
            br = new BufferedReader(isr);

            String line = br.readLine();
            while (line != null) {
                contentList.add(line);
                line = br.readLine();
            }
        } catch (IOException ignored) {
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ignored) {
            }
        }
        return contentList;
    }
}
