import javax.swing.*;
import java.util.ArrayList;

public class Field {
    private ArrayList<Field> neighbours;
    protected ArrayList<Virologist> virologists;

    public Field() {
        neighbours = new ArrayList<>();
        virologists = new ArrayList<>();
    }

    public void setNeighbour(Field f) {
        neighbours.add(f);
    }

    public void accept(Virologist v, JLabel jl) {
        virologists.add(v);
        jl.setText("regular field");
    }

    public void remove(Virologist v) {
        virologists.remove(v);
    }

    public boolean takeStuff(Virologist v) {
        return false;
    }

    public ArrayList<Field> getNeighbours() {
        return neighbours;
    }

    public ArrayList<Virologist> getVirologists() {
        return virologists;
    }
}
