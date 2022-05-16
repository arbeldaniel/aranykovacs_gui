/**
 * Védőfelszerelés, mely megnöveli a virológus anyaggyűjtő képességét, anyagot
 * tárolhat.
 */
public class Bag extends Equipment{
    /**
     * Lista, ami az anyagokat tárolja.
     */
    private final Material material;

    /**
     * Konstruktor
     */
    public Bag() {
        name = "bag";
        material = new Material();
    }

    /**
     * Anyagot tesz a tárolóba,
     * @param m -bemeneti Material
     * @return  -sikerült-e (false, ha tele van a tároló, amúgy true).
     */
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

    /**
     * Getter
     * @return  -tárolt Material
     */
    @Override
    public Material getMaterial() {
        return material;
    }
}
