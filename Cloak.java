import java.util.Random;

public class Cloak extends Equipment{
    public Cloak() {
        name = "cloak";
    }

    @Override
    public boolean apply(Unction u, Virologist from, Virologist to) {
        int chance = new Random().nextInt(1000);
        if(chance > 823) {
            to.addEffect(u);
            return true;
        }
        return false;
    }
}
