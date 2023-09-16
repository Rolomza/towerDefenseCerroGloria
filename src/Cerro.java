import java.util.concurrent.atomic.AtomicInteger;

public class Cerro {
    private double vida = 10000;
    public double getVida() {
        return vida;
    }

    public void restarVida(double danio) {
        this.vida = vida - danio;
    }
}
