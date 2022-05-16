import java.util.ArrayList;

/**
 * Ágenst reprezentál, tárolja a nevét és, hogy meddig lehet felhasználni vagy meddig
 * hatásos. Absztrakt osztály.
 */
public abstract class Unction {
    protected String name;
    protected int timeCounter;
    private Material cost;

    public Unction() {
        timeCounter = 3;
        cost = new Material(5, 5);
    }
    public String getName() {
        return name;
    }

    public Material getCost() {
        return cost;
    }

    public boolean apply(Virologist v) {
        v.removeUnction(this);
        return v.addEffect(this);
    }

    public boolean apply(Virologist v1, Virologist v2) {
        ArrayList<Unction> u = v2.getEffects();
        for(Unction unction : u) {
            if(unction.getName().equals("protection"))
                return false;
        }

        ArrayList<Equipment> e = v2.getEquipments();
        for(Equipment equipment : e) {
            if(equipment.getName().equals("cloak"))
                if(equipment.apply(this, v1, v2))
                    return true;
        }
        for(Equipment equipment : e) {
            if(equipment.getName().equals("glove"))
                if(equipment.apply(this, v1, v2))
                    return true;
        }
        v1.removeUnction(this);
        return v2.addEffect(this);
    }

    public void setTime(int t) {
        timeCounter = t;
    }

    public int getTime() {
        return timeCounter;
    }

}
