import javax.swing.*;
import java.util.ArrayList;

/**
 * Virológusokat, anyagokat, védőfelszereléseket, genetikai kódokat tárol. Ismeri a
 * szomszédjait.
 */
public class Field {
    /**
     * A szomszédos mezők listája.
     */
    private final ArrayList<Field> neighbours;
    /**
     * A mezőn lévő virológusok listája.
     */
    protected ArrayList<Virologist> virologists;

    /**
     * Konstruktor
     */
    public Field() {
        neighbours = new ArrayList<>();
        virologists = new ArrayList<>();
    }

    /**
     * A kapott mezőt a szomszédok listájába teszi
     * @param f -mező
     */
    public void setNeighbour(Field f) {
        neighbours.add(f);
    }

    /**
     * Hozzáadja a virológust a virológusok listájához
     * @param v -virológus
     * @param jl    -kiirandó
     */
    public void accept(Virologist v, JLabel jl) {
        virologists.add(v);
        jl.setText("regular field");
    }

    /**
     * Virológus eltávolítása
     * @param v -virológus
     */
    public void remove(Virologist v) {
        virologists.remove(v);
    }

    /**
     * Visszaad egy false-t.
     * @param v -virológus
     * @return  -false
     */
    public boolean takeStuff(Virologist v) {
        return false;
    }

    /**
     * Getter
     * @return  -szomszédos mezők
     */
    public ArrayList<Field> getNeighbours() {
        return neighbours;
    }

    /**
     * Getter
     * @return  -virológusok
     */
    public ArrayList<Virologist> getVirologists() {
        return virologists;
    }
}
