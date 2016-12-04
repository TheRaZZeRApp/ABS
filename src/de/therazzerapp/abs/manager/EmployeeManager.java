package de.therazzerapp.abs.manager;

import de.therazzerapp.abs.content.CSVFile;
import de.therazzerapp.abs.content.Employee;
import de.therazzerapp.abs.content.MonthType;
import de.therazzerapp.abs.content.saver.CSVSaver;

import java.util.*;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class EmployeeManager {

    /**
     * All loaded eployees.
     */
    private static Set<Employee> employees = new LinkedHashSet<>();

    /**
     * Load new employees. Old list gets overwritten.
     * @param m
     */
    public static void load(Set<Employee> m){
        employees = m;
        orderById();
    }

    /**
     * Orders all loaded employees by there unice id.
     */
    private static void orderById(){
        List<String> names = new LinkedList<>();
        List<Employee> m = new LinkedList<>();

        for (Employee employee : employees) {
            names.add(employee.getID());
        }

        Collections.sort(names);

        for (String id : names) {
            m.add(getById(id));
        }

        employees.clear();
        employees.addAll(m);
    }

    public static void updateMitarbeiter(List<Employee> employees){
        for (Employee employee : employees) {
            updateMitarbeiter(employee);
        }
    }

    public static void updateMitarbeiter(Employee mitarbeiter){
        Employee m = null;
        for (Employee employee : employees) {
            if (employee.getNachname().equals(mitarbeiter.getNachname())){
                m = employee;
                employees.remove(employee);
            }
        }
        if (m == null){
            addMitarbeiter(mitarbeiter);
        } else {
            if (mitarbeiter.getArbeiterbelastung().size() > m.getArbeiterbelastung().size()){
                //todo update
            } else if (mitarbeiter.getArbeiterbelastung().size() == m.getArbeiterbelastung().size()){
                addMitarbeiter(mitarbeiter);
            }
        }
    }

    public static boolean containsMitarbeiter(String name){
        return getByName(name) != null;
    }

    public static void addMitarbeiter(List<Employee> employees){
        for (Employee employee : employees) {
            addMitarbeiter(employee);
        }
    }

    private static void addMitarbeiter(Employee employee){
        if (employees.contains(employee)){
            return;
        } else if (getByName(employee.getNachname()) != null){
            updateMitarbeiter(employee);
        } else {
            employees.add(employee);
            orderById();
        }
    }

    public static void removeMitarbeiter(Employee employee){
        employees.remove(employee);
    }

    /**
     * Exports all loaded employees into a csv file.
     * @param path
     */
    public static void exportCSV(String path){
        orderById();
        List<String> header = new LinkedList<>();
        header.add("Nachname");
        header.add("Vorname");
        header.add("Pers.Nr.");
        for (MonthType monthType : MonthType.values()) {
            if (monthType == MonthType.ERROR || monthType == MonthType.MAERZ){
                continue;
            }
            header.add(monthType.getName());
        }
        header.add("Korrekturen");

        List<List<String>> content = new LinkedList<>();

        for (Employee employee : employees) {
            List<String> e = new LinkedList<>();

            e.add(employee.getNachname());
            e.add(employee.getVorname());
            e.add(employee.getMNr() + "");

            for (int i = 0; i < MonthType.values().length-2; i++) {
                e.add("0,0");
            }

            for (Map<MonthType, String> monatTypeStringMap : employee.getArbeiterbelastung()) {
                MonthType monthType = monatTypeStringMap.keySet().iterator().next();
                int j = header.indexOf(monthType.getName());
                if (j > 0){
                    e.set(j,monatTypeStringMap.values().iterator().next());
                } else {
                    e.add("Fehler bei: " + monatTypeStringMap.values().iterator().next());
                }
            }
            e.add(employee.getKorrekturen());
            content.add(e);
        }

        CSVFile csvFile = new CSVFile(header,content);
        CSVSaver.save(csvFile,path);

        System.out.println("Es wurden: " + content.size() + " Mitarbeiter aufgelistet.");

    }

    /**
     * Prints out all loaded employees
     */
    public static void printEmployees(){
        for (Employee employee : employees) {
            System.out.println(employee.createMitarbeiter());
        }
    }


    /**
     * Returns all employees who started working in the given month.
     * @param monthType
     * @return
     */
    public static Set<Employee> getByFirstMonth(MonthType monthType){
        Set<Employee> m = new LinkedHashSet<>();
        for (Employee employee : employees) {
            if (employee.getFirstMont().equals(monthType)){
                m.add(employee);
            }
        }
        return m;
    }

    /**
     * Returns the amount of loaded employees.
     * @return
     */
    public static int getEmployeeSize(){
        return employees.size();
    }

    public static Employee getByVorname(String name){
        for (Employee employee : employees) {
            if (employee.getVorname().equals(name)){
                return employee;
            }
        }
        return null;
    }

    public static Employee getById(String id){
        for (Employee employee : employees) {
            if (employee.getID().equalsIgnoreCase(id)){
                return employee;
            }
        }
        return null;
    }

    public static Employee getByName(String name){
        for (Employee employee : employees) {
            if (employee.getNachname().equals(name)){
                return employee;
            }
        }
        return null;
    }

}
