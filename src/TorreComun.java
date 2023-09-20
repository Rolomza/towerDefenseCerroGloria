import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TorreComun extends Torre{

    private static final AtomicInteger contador = new AtomicInteger(0);
    public TorreComun(Coordenada coordenadaTorre){
        this.id = contador.incrementAndGet();
        this.danio = 40;
        this.coordenadaTorre = coordenadaTorre;
        this.alcanceAtaque = 1;
        this.costeTorre = 400;
    }

    public TorreComun(){
        this.danio = 40;
        this.alcanceAtaque = 1;
        this.costeTorre = 400;
    }


//    public void atacar(Casillero casillero, Nivel nivelActual) {
//        listaAtaqueEnemigos = prioridadEnemigo(casillero);
//
//        for (Enemigo enemigoActual : listaAtaqueEnemigos){
//            enemigoActual.restarVida(this.danio);
//            if (enemigoActual.getVida() <= 0){
//                casillero.eliminarEnemigo(enemigoActual);
//                nivelActual.aumentarPuntosMagia(enemigoActual.getRecompensaEnemigo());
//                casillero.getEnemigosListosParaMoverse().remove(enemigoActual);
//                System.out.println("La torre " + this.toString() + " ha asesinado a " + enemigoActual.toString() + " | +" + enemigoActual.getRecompensaEnemigo() + " PtosMagia.");
//            } else {
//                System.out.println(enemigoActual.toString() + " fue atacado por "+this.toString()+" y ahora tiene "+enemigoActual.getVida()+" de vida");
//            }
//        }
//    }




    public String toString() {
        return "Tc"+this.id;
    }

}
