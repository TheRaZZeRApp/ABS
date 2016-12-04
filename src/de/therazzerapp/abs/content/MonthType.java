package de.therazzerapp.abs.content;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public enum MonthType {

    JANUAR("Januar"),
    FEBRUAR("Februar"),
    MÄRZ("März"),
    MAERZ("März"),
    APRIL("April"),
    MAI("Mai"),
    JUNI("Juni"),
    JULI("Juli"),
    AUGUST("August"),
    SEPTEMBER("September"),
    OKTOBER("Oktober"),
    NOVEMBER("November"),
    DEZEMBER("Dezember"),
    GESAMT("Gesamt"),
    ERROR("Error");

    private final String name;

    MonthType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
