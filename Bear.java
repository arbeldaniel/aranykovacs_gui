public class Bear extends Unction{
    public Bear() {
        name = "bear";
    }

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
