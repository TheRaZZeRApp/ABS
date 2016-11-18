package de.therazzerapp.abs.manager;

import de.therazzerapp.abs.content.Mitarbeiter;
import de.therazzerapp.abs.content.MonatType;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class MitarbeiterManager {
    private static Set<Mitarbeiter> employees = new LinkedHashSet<>();

    public static void load(Set<Mitarbeiter> m){
        employees = m;
        orderByName();
    }

    private static void orderByName(){
        //todo order by name

    }

    public static void updateMitarbeiter(Mitarbeiter mitarbeiter){
        if(!employees.contains(mitarbeiter)){
            employees.add(mitarbeiter);
        } else if (containsMitarbeiter(mitarbeiter.getNachname())){

        }

        orderByName();
    }

    public static boolean containsMitarbeiter(String name){
        if (getByName(name) == null){
            return false;
        } else {
            return true;
        }
    }

    public static void addMitarbeiter(Mitarbeiter mitarbeiter){
        if (employees.contains(mitarbeiter)){
            //todo mitarbeiter erweitern
        } else {
            employees.add(mitarbeiter);
        }
        orderByName();
    }

    public static void removeMitarbeiter(Mitarbeiter mitarbeiter){
        employees.remove(mitarbeiter);
        orderByName();
    }

    public static boolean containsMitarbeiter(Mitarbeiter mitarbeiter){
        return employees.contains(mitarbeiter);
    }

    /**
     * Returns all employees who started working in the given month.
     * @param monatType
     * @return
     */
    public static Set<Mitarbeiter> getByFirstMonth(MonatType monatType){
        Set<Mitarbeiter> m = new LinkedHashSet<>();
        for (Mitarbeiter mitarbeiter : employees) {
            if (mitarbeiter.getFirstMont().equals(monatType)){
                m.add(mitarbeiter);
            }
        }
        return m;
    }


    public static Mitarbeiter getByName(String name){
        for (Mitarbeiter mitarbeiter : employees) {
            if (mitarbeiter.getNachname().equals(name)){
                return mitarbeiter;
            }
        }
        return null;
    }

    public static Mitarbeiter getByVorname(String name){
        for (Mitarbeiter mitarbeiter : employees) {
            if (mitarbeiter.getVorname().equals(name)){
                return mitarbeiter;
            }
        }
        return null;
    }

    public static void createMitarbeiter(){
        for (Mitarbeiter mitarbeiter : employees) {
            System.out.println(mitarbeiter.createMitarbeiter());
        }
    }

}
