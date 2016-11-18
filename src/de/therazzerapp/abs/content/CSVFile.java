package de.therazzerapp.abs.content;

import java.util.LinkedList;
import java.util.List;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class CSVFile {
    private List<String> header;
    private List<List<String>> content;


    public CSVFile(List<String> header, List<List<String>> content) {
        this.header = header;
        this.content = content;
    }

    public List<String> getHeader() {
        return header;
    }

    public List<List<String>> getContentList() {
        return content;
    }

    public String getContent(int zeile, int spalte){
        return content.get(zeile).get(spalte);
    }

    public List<String> getSpalte(int spalte){
        List<String> z = new LinkedList<>();
        for (List<String> list : content) {
            z.add(list.get(spalte));
        }
        return z;

    }

    public List<String> getZeile(int zeile){
        return content.get(zeile);
    }

}
