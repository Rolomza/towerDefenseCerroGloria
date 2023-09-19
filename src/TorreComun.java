import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TorreComun extends Torre{

    private static final AtomicInteger contador = new AtomicInteger(0);
    public TorreComun(Coordenada coordenadaTorre){
        this.id = contador.incrementAndGet();
        this.danio = 30;
        this.coordenadaTorre = coordenadaTorre;
        this.alcanceAtaque = 1;
        this.costeTorre = 200;
    }

    public void getCoordenadaTorre() {
        this.coordenadaTorre.mostrarCoordenada();
    }

    @Override
    public void atacar(Casillero casillero, Nivel nivelActual) {
        //
        listaAtaqueEnemigos = prioridadEnemigo(casillero);

        for (Enemigo enemigoActual : listaAtaqueEnemigos){
            enemigoActual.restarVida(this.danio);
            if (enemigoActual.getVida() <= 0){
                casillero.eliminarEnemigo(enemigoActual);
                nivelActual.aumentarPuntosMagia(enemigoActual.getRecompensaEnemigo());
                casillero.getEnemigosListosParaMoverse().remove(enemigoActual);
                System.out.println("La torre " + this.toString() + " ha asesinado a " + enemigoActual.toString() + " | +" + enemigoActual.getRecompensaEnemigo() + " PtosMagia.");
            } else {
                System.out.println(enemigoActual.toString() + " fue atacado por "+this.toString()+" y ahora tiene "+enemigoActual.getVida()+" de vida");
            }
        }
    }


    public ArrayList<Enemigo> prioridadEnemigo(Casillero casilleroEnemigo){
        // Dependiendo de la torre es la cantidad de enemigos que devuelve y a quien focusea

        ArrayList<Enemigo> listaEnemigosPorPrioridad = new ArrayList<>();
        //Este caso es para la torre comun, que focusea primero a los humanos
        for (ArrayList<Enemigo> listaEnemigos : casilleroEnemigo.getEnemigosCasillero().values()){
            if (!listaEnemigos.isEmpty()){
                listaEnemigosPorPrioridad.add(listaEnemigos.get(0));
            }
        }
        return listaEnemigosPorPrioridad;
    }

    public String toString() {
        return "Tc"+this.id;
    }


}
