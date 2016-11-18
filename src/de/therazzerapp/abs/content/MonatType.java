package de.therazzerapp.abs.content;

/**
 * <description>
 *
 * @author The RaZZeR App <rezzer101@googlemail.com; e-mail@therazzerapp.de>
 * @since <version>
 */
public enum MonatType {

    JANUAR("Januar"),
    FEBRUAR("Februar"),
    MÄRZ("M\u00e4rz"),
    MAERZ("M\u00e4rz"),
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

    MonatType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
