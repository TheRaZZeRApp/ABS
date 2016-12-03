package de.therazzerapp.abs.content.loader;

import de.therazzerapp.abs.content.Employee;
import de.therazzerapp.abs.content.MonatType;
import de.therazzerapp.abs.manager.ErrorManager;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class EmployeeLoader {
    public static Set<Employee> readContent(List<String> content){

        String nachname = "";
        String vorname = "";
        String belastung = "";
        String monate = "";
        String korrekturen = "";
        int mitarbeiterNr = 0;
        Matcher matcher;

        Set<Employee> m = new LinkedHashSet<>();

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

                Employee duplicate = null;
                Employee employee = new Employee(nachname,vorname,mitarbeiterNr,formatBelastung(belastung,monate, mitarbeiterNr),korrekturen);
                for (Employee check : m) {
                    if (Objects.equals(check.getID(), employee.getID())){
                        duplicate = check;
                    }
                }
                if (duplicate != null){
                    m.remove(duplicate);
                    m.add(new Employee(nachname,vorname,mitarbeiterNr,mergeBelastung(employee,duplicate),duplicate.getKorrekturen() + korrekturen));
                    duplicate = null;
                } else {
                    m.add(employee);
                }
                korrekturen = "";
            }
        }

        for (Employee employee : m) {
            System.out.println(employee.createMitarbeiter());
        }

        return m;
    }

    private static List<Map<MonatType, String>> mergeBelastung(Employee m, Employee n){
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

    private static List<Map<MonatType, String>> formatBelastung(String belastung, String monat, int mnr){
        List<Map<MonatType, String>> ab = new LinkedList<>();

        List<MonatType> monatTypeList = new LinkedList<>();
        for (String s : monat.split(" ")) {
            if (!s.isEmpty() || s.equals(" ")){
                try {
                    monatTypeList.add(MonatType.valueOf(s.toUpperCase()));
                } catch (IllegalArgumentException e){
                    ErrorManager.writeError("Pers.Nr.: " + mnr + " Unbekannter Monat: " + s);
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
            ErrorManager.writeError("Pers.Nr.: " + mnr + " hat eine ungültige Anzahl an Monaten (M" + monatTypeList.size() + " / B" + belastungsList.size() + ")");
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
