package pl.mn.mncustomenchants.ItemMethods;



import java.io.Serializable;


public class LoreComponent implements Serializable {

    private static final long serialVersionUID = 1L;


    private String[] text;
    private int[] r;
    private int[] g;
    private int[] b;



    public int[] getR() {
        return r;
    }

    public void setR(int[] r) {
        this.r = r;
    }

    public void appendR(int r, int i) { this.r[i] = r; }

    public int[] getG() {
        return g;
    }

    public void setG(int[] g) {
        this.g = g;
    }

    public void appendG(int g, int i) { this.g[i] = g; }


    public int[] getB() {
        return b;
    }

    public void setB(int[] b) {
        this.b = b;
    }

    public void appendB(int b, int i) { this.b[i] = b; }



    public LoreComponent (String[] text, int[] r, int[] g, int[] b){
        this.text = text;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public String[] getText() {
        return text;
    }

    public void setText(String[] text) {
        this.text = text;
    }

    public void appendText(String text, int i){
        this.text[i] = text;
    }

    /*
    public TextColor getColor(){
        return TextColor.color(r,g,b);
    }
    */

}
