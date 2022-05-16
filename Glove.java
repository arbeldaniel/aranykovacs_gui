import java.util.Random;

/**
 * A birtokló virológus visszadobja a rákent ágenseket.
 */
public class Glove extends Equipment{
    private int counter;
    public Glove() {
        name = "glove";
        counter = 3;
    }

    @Override
    public boolean apply(Unction u, Virologist from, Virologist to) {
        u.apply(to, from);
        if(--counter == 0)
            to.removeEquipment(this);
        return false;
    }
}
