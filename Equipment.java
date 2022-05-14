import java.io.UncheckedIOException;

public class Equipment {
    protected String name;

    public Equipment() {
    }

    public String getName() {
        return name;
    }
    public boolean addMaterial(Material m) {
        return false;
    }

    public boolean apply(Unction u, Virologist from, Virologist to) {
        return false;
    }

    public Material getMaterial() {
        return null;
    }
}
