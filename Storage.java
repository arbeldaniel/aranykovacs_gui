import javax.swing.*;
import java.util.Random;

/**
 * Egyfajta mező, anyagot tárol.
 */
public class Storage extends Field{
    private Material m;

    public Storage() {
        m = new Material();
        Random rand = new Random();
        m.setAminoacid(rand.nextInt(10));
        m.setNucletoid(rand.nextInt(10));
    }

    @Override
    public boolean takeStuff(Virologist v) {
        boolean ret = v.addMaterial(m);
        m.setAminoacid(0);
        m.setNucletoid(0);
        return ret;
    }

    @Override
    public void accept(Virologist v, JLabel jl) {
        virologists.add(v);
        jl.setText("storage");
    }
}
