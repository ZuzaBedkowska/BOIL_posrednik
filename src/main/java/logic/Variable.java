package logic;
import java.util.Formatter;

public class Variable {
    private int stock;
    private int required;
    private double value;
    public Variable(){
        this.stock = 0;
        this.required = 0;
    }
    public Variable(int stock, int required){
        this.stock = stock;
        this.required = required;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public int getRequired() {
        return required;
    }
    public void setRequired(int required) {
        this.required = required;
    }
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }
    @Override
    public String toString() {
        Formatter f = new Formatter();
        f.format("x[%d,%d]=%f", this.stock+1, this.required+1, this.value);
        return f.toString();
    }
}