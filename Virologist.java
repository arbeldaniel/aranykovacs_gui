import javax.swing.*;
import java.util.ArrayList;

public class Virologist {
    private Field field;
    private ArrayList<Unction> unctions;
    private ArrayList<Unction> effects;
    private Material material;
    private ArrayList<Code> codes;
    private ArrayList<Equipment> equipments;
    private boolean dead;

    public Virologist() {
        material = new Material(5, 5);
        unctions = new ArrayList<>();
        effects = new ArrayList<>();
        codes = new ArrayList<>();
        equipments = new ArrayList<>();
        dead = false;
    }
    public void setField(Field f, JLabel jl) {
        f.remove(this);
        field = f;
        f.accept(this, jl);
    }

    public void setDead(boolean b) {
        dead = b;
    }

    public boolean getDead() {
        return dead;
    }
    public boolean takeStuff() {
        return field.takeStuff(this);
    }

    public boolean addMaterial(Material m) {
        material.setNucletoid(material.getNucletoid() + m.getNucletoid());
        material.setAminoacid(material.getAminoacid() + m.getAminoacid());
        if(material.getAminoacid() > 20) {
            for (Equipment equipment : equipments) {
                if (equipment.getName().equals("bag"))
                    equipment.addMaterial(new Material(material.getAminoacid() - 20, 0));
            }
            material.setAminoacid(20);
        }
        if(material.getNucletoid() > 20) {
            for (Equipment equipment : equipments) {
                if (equipment.getName().equals("bag"))
                    equipment.addMaterial(new Material(0, material.getNucletoid() - 20));
            }
            material.setNucletoid(20);
        }
        return true;
    }

    public boolean addEquipment(Equipment e) {
        if(e == null)
            return false;
        equipments.add(e);
        return true;
    }

    public boolean addCode(Code c) {
        if(codes.contains(c))
            return false;
        codes.add(c);
        return true;
    }

    public ArrayList<Code> getCodes() {
        return codes;
    }

    public Field getField() {
        return field;
    }

    public ArrayList<Unction> getUnctions() {
        return unctions;
    }

    public ArrayList<Unction> getEffects() {
        return effects;
    }

    public ArrayList<Equipment> getEquipments() {
        return equipments;
    }

    public Material getMaterial() {
        return material;
    }

    public boolean addEffect(Unction u) {
        for(Unction e : effects) {
            if(e.getName().equals(u.getName())) {
                e.setTime(u.getTime());
                return true;
            }
        }
        effects.add(u);
        return true;
    }

    public void removeEquipment(Equipment e) {
        equipments.remove(e);
    }

    public void addUnction(Unction u) {
        Material cost = u.getCost();
        if(material.getNucletoid() < cost.getNucletoid() || material.getAminoacid() < cost.getAminoacid())
            return;
        unctions.add(u);
        for(Equipment equipment : equipments) {
            if(equipment.getName().equals("bag")) {
                if(equipment.getMaterial().getAminoacid() > cost.getAminoacid()) {
                    equipment.getMaterial().setAminoacid(equipment.getMaterial().getAminoacid() - cost.getAminoacid());
                    cost.setAminoacid(0);
                }
                else {
                    cost.setAminoacid(cost.getAminoacid() - equipment.getMaterial().getAminoacid());
                    equipment.getMaterial().setAminoacid(0);
                }
                if(equipment.getMaterial().getNucletoid() > cost.getNucletoid()) {
                    equipment.getMaterial().setNucletoid(equipment.getMaterial().getNucletoid() - cost.getNucletoid());
                    cost.setNucletoid(0);
                }
                else {
                    cost.setNucletoid(cost.getNucletoid() - equipment.getMaterial().getNucletoid());
                    equipment.getMaterial().setNucletoid(0);
                }
            }
        }

        material.setNucletoid(material.getNucletoid() - cost.getNucletoid());
        material.setAminoacid(material.getAminoacid() - cost.getAminoacid());
    }

    public void removeUnction(Unction u) {
        for(int i = 0; i < unctions.size(); ++i) {
            if(unctions.get(i).getName().equals(u.getName()))
                unctions.remove(i);
        }
    }

    public void removeEffect(Unction u) {
        effects.remove(u);
    }
}
