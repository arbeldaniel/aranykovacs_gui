import javax.swing.*;

/**
 * Egyfajta mező, védőfelszereléseket tárol.
 */
public class Shelter extends Field{
    private Equipment e;
    public Shelter(Equipment e) {
        this.e = e;
    }

    @Override
    public boolean takeStuff(Virologist v) {
        boolean ret = v.addEquipment(e);
        e = null;
        return ret;
    }

    @Override
    public void accept(Virologist v, JLabel jl) {
        virologists.add(v);
        jl.setText("shelter");
    }
}
