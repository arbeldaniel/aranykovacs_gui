import java.io.UncheckedIOException;

/**
 * Absztrakt osztály, mely védőfelszereléseket reprezentál. Tárolja a védőfelszerelés
 * nevét.
 */
public abstract class Equipment {
    /**
     * A felszerelés neve
     */
    protected String name;

    /**
     * Konstruktor
     */
    public Equipment() {
    }

    /**
     * Getter
     * @return  -név
     */
    public String getName() {
        return name;
    }

    public boolean addMaterial(Material m) {
        return false;
    }

    public boolean apply(Unction u, Virologist from, Virologist to) {
        return false;
    }

    /**
     * Getter
     * @return  -null
     */
    public Material getMaterial() {
        return null;
    }
}
