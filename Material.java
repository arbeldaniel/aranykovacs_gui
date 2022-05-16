/**
 * Anyagot reprezentál, tárolja, hogy nukleotid vagy aminosav.
 */
public class Material {
    int aminoacid;
    int nucletoid;

    public Material() {
        aminoacid = 0;
        nucletoid = 0;
    }
    public Material(int a, int n) {
        aminoacid = a;
        nucletoid = n;
    }

    public int getNucletoid() {
        return nucletoid;
    }

    public void setNucletoid(int nucletoid) {
        this.nucletoid = nucletoid;
    }

    public int getAminoacid() {
        return aminoacid;
    }

    public void setAminoacid(int aminoacid) {
        this.aminoacid = aminoacid;
    }
}
