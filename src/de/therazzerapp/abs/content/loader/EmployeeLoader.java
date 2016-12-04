package de.therazzerapp.abs.content.loader;

import de.therazzerapp.abs.content.Employee;
import de.therazzerapp.abs.content.MonthType;
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
        int dublicates = 0;

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
                    dublicates++;
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

        System.out.println("Dopplungen: " + dublicates);

        return m;
    }

    private static List<Map<MonthType, String>> mergeBelastung(Employee m, Employee n){
        List<Map<MonthType, String>> mergedBelastung = new LinkedList<>();
            for (MonthType monthType : MonthType.values()) {
                if (monthType.equals(MonthType.ERROR) || monthType.equals(MonthType.MAERZ)){
                    continue;
                }
                Map<MonthType, String> t = new HashMap<>();
                if (m.hasMonth(monthType) && !n.hasMonth(monthType)){
                    t.put(monthType,m.getMonthBelatungs(monthType));
                    mergedBelastung.add(t);
                } else if (!m.hasMonth(monthType) && n.hasMonth(monthType)){
                    t.put(monthType,n.getMonthBelatungs(monthType));
                    mergedBelastung.add(t);
                } else if (m.hasMonth(monthType) && n.hasMonth(monthType)){
                    t.put(monthType,m.getMonthBelatungs(monthType));
                    mergedBelastung.add(t);
                }else {
                    t.put(monthType, "0,0");
                    mergedBelastung.add(t);
                }
            }
        return mergedBelastung;
    }

    private static List<Map<MonthType, String>> formatBelastung(String belastung, String monat, int mnr){
        List<Map<MonthType, String>> ab = new LinkedList<>();

        List<MonthType> monthTypeList = new LinkedList<>();
        for (String s : monat.split(" ")) {
            if (!s.isEmpty() || s.equals(" ")){
                try {
                    monthTypeList.add(MonthType.valueOf(s.toUpperCase()));
                } catch (IllegalArgumentException e){
                    ErrorManager.writeError("Pers.Nr.: " + mnr + " Unbekannter Monat: " + s);
                    monthTypeList.add(MonthType.ERROR);
                }
            }
        }

        List<String> belastungsList = new LinkedList<>();
        for (String s : belastung.split(" ")) {
            if (!s.isEmpty() || s.equals(" ")){
                belastungsList.add(s);
            }
        }

        if (monthTypeList.size() != belastungsList.size()){
            ErrorManager.writeError("Pers.Nr.: " + mnr + " hat eine ungültige Anzahl an Monaten (M" + monthTypeList.size() + " / B" + belastungsList.size() + ")");
        } else {
            for (int i = 0; i < monthTypeList.size(); i++) {
                Map<MonthType, String> temp = new LinkedHashMap<>();
                temp.put(monthTypeList.get(i),belastungsList.get(i));
                ab.add(temp);
            }
        }
        return ab;
    }

}
