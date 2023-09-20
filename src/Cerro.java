import java.util.concurrent.atomic.AtomicInteger;

public class Cerro {
    private double vida = 1500;
    public double getVida() {
        return vida;
    }

    public void restarVida(double danio) {
        this.vida = vida - danio;
    }
    public void mostrarVida(){
        System.out.println("La vida actual del Cerro Gloria es: "+vida);
    }
}
