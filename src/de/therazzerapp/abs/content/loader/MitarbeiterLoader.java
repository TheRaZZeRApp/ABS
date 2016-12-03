package de.therazzerapp.abs.content.loader;

import de.therazzerapp.abs.content.Mitarbeiter;
import de.therazzerapp.abs.content.MonatType;
import de.therazzerapp.abs.manager.MitarbeiterManager;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class MitarbeiterLoader {
    public static Set<Mitarbeiter> readContent(List<String> content){

        String nachname = "";
        String vorname = "";
        String belastung = "";
        String monate = "";
        String korrekturen = "";
        int mitarbeiterNr = 0;
        Matcher matcher;

        Set<Mitarbeiter> m = new LinkedHashSet<>();

        for (String s : content) {

            matcher = Pattern.compile("Name: (?<name>.*), (?<vorname>.*)Pers\\.Nr\\.: (?<mnr>[0-9]*)").matcher(s);

            while (matcher.find()){
                nachname = matcher.group("name");
                vorname = matcher.group("vorname");
                mitarbeiterNr = Integer.parseInt(matcher.group("mnr"));
            }

            matcher = Pattern.compile("\\(korrigiert im\\) (?<korrekturen>.*)").matcher(s);
            while (matcher.find()){
                korrekturen = matcher.group("korrekturen");
            }

            matcher = Pattern.compile("Arbeitgeberbelastung (?<belastungen>.*,.+)").matcher(s);

            while (matcher.find()){
                belastung = matcher.group("belastungen");
            }

            matcher = Pattern.compile("Monat: (?<monate>.*)").matcher(s);

            while (matcher.find()){
                monate = matcher.group("monate");
            }

            matcher = Pattern.compile("Industrieschutz Walter GmbH").matcher(s);
            if (matcher.find()){

                Mitarbeiter duplicate = null;
                Mitarbeiter mitarbeiter = new Mitarbeiter(nachname,vorname,mitarbeiterNr,formatBelastung(belastung,monate),korrekturen);
                for (Mitarbeiter check : m) {
                    if (Objects.equals(check.getID(), mitarbeiter.getID())){
                        duplicate = check;
                    }
                }
                if (duplicate != null){
                    m.remove(duplicate);
                    m.add(new Mitarbeiter(nachname,vorname,mitarbeiterNr,mergeBelastung(mitarbeiter,duplicate),duplicate.getKorrekturen() + korrekturen));
                    duplicate = null;
                } else {
                    m.add(mitarbeiter);
                }
                korrekturen = "";
            }
        }

        for (Mitarbeiter mitarbeiter : m) {
            System.out.println(mitarbeiter.createMitarbeiter());
        }

        return m;
    }

    private static List<Map<MonatType, String>> mergeBelastung(Mitarbeiter m, Mitarbeiter n){
        List<Map<MonatType, String>> mergedBelastung = new LinkedList<>();
            for (MonatType monatType : MonatType.values()) {
                if (monatType.equals(MonatType.ERROR) || monatType.equals(MonatType.MAERZ)){
                    continue;
                }
                Map<MonatType, String> t = new HashMap<>();
                if (m.hasMonth(monatType) && !n.hasMonth(monatType)){
                    t.put(monatType,m.getMonthBelatungs(monatType));
                    mergedBelastung.add(t);
                } else if (!m.hasMonth(monatType) && n.hasMonth(monatType)){
                    t.put(monatType,n.getMonthBelatungs(monatType));
                    mergedBelastung.add(t);
                } else if (m.hasMonth(monatType) && n.hasMonth(monatType)){
                    t.put(monatType,m.getMonthBelatungs(monatType));
                    mergedBelastung.add(t);
                }else {
                    t.put(monatType, "0,0");
                    mergedBelastung.add(t);
                }
            }
        return mergedBelastung;
    }

    private static List<Map<MonatType, String>> formatBelastung(String belastung, String monat){
        List<Map<MonatType, String>> ab = new LinkedList<>();

        List<MonatType> monatTypeList = new LinkedList<>();
        for (String s : monat.split(" ")) {
            if (!s.isEmpty() || s.equals(" ")){
                try {
                    monatTypeList.add(MonatType.valueOf(s.toUpperCase()));
                } catch (IllegalArgumentException e){
                    monatTypeList.add(MonatType.ERROR);
                }
            }
        }

        List<String> belastungsList = new LinkedList<>();
        for (String s : belastung.split(" ")) {
            if (!s.isEmpty() || s.equals(" ")){
                belastungsList.add(s);
            }
        }

        if (monatTypeList.size() != belastungsList.size()){
            //todo Error
        } else {
            for (int i = 0; i < monatTypeList.size(); i++) {
                Map<MonatType, String> temp = new LinkedHashMap<>();
                temp.put(monatTypeList.get(i),belastungsList.get(i));
                ab.add(temp);
            }
        }
        return ab;
    }

}
