import java.util.concurrent.atomic.AtomicInteger;

public class Barrera {
    private static final AtomicInteger contador = new AtomicInteger(0);
    private int id;
    private double vida;
    private int precioBarrera;
    public Barrera() {
        this.id = contador.incrementAndGet();
        this.vida = 650;
        this.precioBarrera = 100;
    }

    public String toString() {
        return "Barr"+this.id;
    }

    public int getPrecioBarrera() {
        return precioBarrera;
    }

    public double getVida() {
        return vida;
    }
    public void restarVida(double danio){
        this.vida=this.vida -danio;
    }
}
