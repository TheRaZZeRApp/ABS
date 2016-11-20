package de.therazzerapp.abs.content;

import java.util.*;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class Mitarbeiter {
    private String nachname;
    private String vorname;
    private int mitarbeiterNr;
    private List<Map<MonatType, String>> arbeiterbelastung = new LinkedList<>();
    private String korrekturen;

    public Mitarbeiter(String nachname, String vorname, int mitarbeiterNr, List<Map<MonatType, String>> arbeiterbelastung, String korrekturen) {
        this.nachname = nachname;
        this.vorname = vorname;
        this.mitarbeiterNr = mitarbeiterNr;
        this.arbeiterbelastung = arbeiterbelastung;
        this.korrekturen = korrekturen;
    }

    public int getMitarbeiterNr() {
        return mitarbeiterNr;
    }

    private String convertBelastung(){
        StringBuilder sb = new StringBuilder();
        for (Map<MonatType, String> anArbeiterbelastung : arbeiterbelastung) {
            for (Map.Entry<MonatType, String> monatTypeStringEntry : anArbeiterbelastung.entrySet()) {
                sb.append(monatTypeStringEntry.getKey().getName() + ": " + monatTypeStringEntry.getValue() + " ");
            }
        }
        return sb.toString();
    }

    public MonatType getFirstMont(){
        return arbeiterbelastung.get(0).keySet().iterator().next();
    }

    public String getNachname() {
        return nachname;
    }

    public String getVorname() {
        return vorname;
    }

    public List<Map<MonatType, String>> getArbeiterbelastung() {
        return arbeiterbelastung;
    }

    public String createMitarbeiter(){
        return mitarbeiterNr + ", " + nachname + ", " + vorname + ", Monate: " + arbeiterbelastung.size();
    }

    public String getID(){
        return nachname+ "," + vorname + "," + mitarbeiterNr;
    }

    public String getKorrekturen() {
        return korrekturen;
    }
}
