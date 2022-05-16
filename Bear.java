/**
 * Medvevírus osztály. Arra kényszeríti a virológust, hogy véletlenszerűen mozogjon, és
 * a raktár típusú mezőkön megsemmisítse a mezőn lévő anyagokat.
 */
public class Bear extends Unction{
    /**
     * Konstruktor
     */
    public Bear() {
        name = "bear";
    }

    /**
     *
     * @param v1    -kenő virológus
     * @param v2    -target virológus
     * @return  -sikeres volt-e a kenés
     */
    @Override
    public boolean apply(Virologist v1, Virologist v2) {
        for(Equipment equipment : v2.getEquipments()) {
            if(equipment.getName().equals("axe")) {
                v2.removeEquipment(equipment);
                return false;
            }
        }
        return super.apply(v1, v2);
    }
}
