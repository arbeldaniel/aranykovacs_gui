public class Bag extends Equipment{
    private Material material;
    public Bag() {
        name = "bag";
        material = new Material();
    }
    @Override
    public boolean addMaterial(Material m) {
        material.setNucletoid(material.getNucletoid() + m.getNucletoid());
        material.setAminoacid(material.getAminoacid() + m.getAminoacid());
        if(material.getAminoacid() > 10) {
            material.setAminoacid(10);
        }
        if(material.getNucletoid() > 10)
            material.setNucletoid(10);
        return true;
    }

    @Override
    public Material getMaterial() {
        return material;
    }
}
