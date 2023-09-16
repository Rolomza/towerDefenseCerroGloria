import java.util.concurrent.atomic.AtomicInteger;

public class Barrera {
    private static final AtomicInteger contador = new AtomicInteger(0);
    private int id;
    private int puntosResistencia;

    public Barrera() {
        this.id = contador.incrementAndGet();
    }

    public String toString() {
        return "Barr"+this.id;
    }
}
