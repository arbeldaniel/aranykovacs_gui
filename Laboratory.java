import javax.swing.*;

public class Laboratory extends Field {
    private Code code;

    public Laboratory(Code c) {
        code = c;
    }

    @Override
    public boolean takeStuff(Virologist v) {
        return v.addCode(code);
    }

    @Override
    public void accept(Virologist v, JLabel jl) {
        virologists.add(v);
        jl.setText("laboratory");
    }
}
