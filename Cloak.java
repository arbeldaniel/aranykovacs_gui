import java.util.Random;

/**
 * 82,3%-os valószínűséggel megakadályozza, hogy ágenst kenjenek a viselő
 * virológusra.
 */
public class Cloak extends Equipment{
    /**
     * Konstruktor
     */
    public Cloak() {
        name = "cloak";
    }

    /**
     * generál egy
     * véletlenszámot 1 és 1000 között, és ha ez 823 alatt van, akkor false-t ad vissza,
     * egyébként true-t.
     * @param u -kent ágens
     * @param from  -kenő virológus
     * @param to    -kent virológus
     * @return  -ha 823 alatt van, akkor false-t ad vissza, egyébként true-t.
     */
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
