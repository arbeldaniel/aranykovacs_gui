/**
 * Genetikai kódot reprezentál. Tartalmazza a kód leírását. A virológus a kódok
 * segítségével tud ágenst előállítani.
 */
public class Code {
    /**
     * Genetikai kód
     */
    private final String codeID;

    /**
     * Konstruktor
     * @param codeid    -kód
     */
    public Code(String codeid) {
        codeID = codeid;
    }

    /**
     * Getter
     * @return  -kód
     */
    public String getCodeID() {
        return codeID;
    }
}
