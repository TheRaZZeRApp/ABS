package de.therazzerapp.abs.content;

import de.therazzerapp.abs.ABS;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class PDFtoTXTThread extends Thread {

    private File file;
    private Process process;

    public PDFtoTXTThread(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        Runtime rt = Runtime.getRuntime();
        try {
            String path = ABS.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            path = path.replaceAll("[^\\/]+\\.jar","");
            path = path.replaceFirst("/","");
            try {
                process = rt.exec(path + "pdftotext.exe -raw -nopgbrk " + file.getAbsolutePath());
                BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while (br.read() != -1) {
                }
                br.close();
            } catch (IOException e) {
                process.destroy();
                return;
            }
        } catch (URISyntaxException ignored) {
        }
    }

    @Override
    public void interrupt() {
        if (process != null) {
            process.destroy();
        }
        super.interrupt();
    }
}
