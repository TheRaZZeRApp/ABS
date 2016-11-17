package de.therazzerapp.abs.manager;

import de.therazzerapp.abs.content.Mitarbeiter;
import de.therazzerapp.abs.content.MonatType;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public class MitarbeiterManager {
    private static Set<Mitarbeiter> mitarbeiters = new LinkedHashSet<>();

    public static void load(Set<Mitarbeiter> m){
        mitarbeiters = m;
    }

    public static void addMitarbeiter(Mitarbeiter mitarbeiter){
        if (mitarbeiters.contains(mitarbeiter)){
            //todo mitarbeiter erweitern
        } else {
            mitarbeiters.add(mitarbeiter);
        }
    }

    public static void removeMitarbeiter(Mitarbeiter mitarbeiter){
        mitarbeiters.remove(mitarbeiter);
    }

    public static boolean containsMitarbeiter(Mitarbeiter mitarbeiter){
        return mitarbeiters.contains(mitarbeiter);
    }

    /**
     * Returns all employees who started working in the given month.
     * @param monatType
     * @return
     */
    public static Set<Mitarbeiter> getByFirstMonth(MonatType monatType){
        Set<Mitarbeiter> m = new LinkedHashSet<>();
        for (Mitarbeiter mitarbeiter : mitarbeiters) {
            if (mitarbeiter.getFirstMont().equals(monatType)){
                m.add(mitarbeiter);
            }
        }
        return m;
    }


    public static Mitarbeiter getByName(String name){
        for (Mitarbeiter mitarbeiter : mitarbeiters) {
            if (mitarbeiter.getNachname().equals(name)){
                return mitarbeiter;
            }
        }
        return null;
    }

    public static Mitarbeiter getByVorname(String name){
        for (Mitarbeiter mitarbeiter : mitarbeiters) {
            if (mitarbeiter.getVorname().equals(name)){
                return mitarbeiter;
            }
        }
        return null;
    }

    public static void createMitarbeiter(){
        for (Mitarbeiter mitarbeiter : mitarbeiters) {
            System.out.println(mitarbeiter.createMitarbeiter());
        }
    }

}
