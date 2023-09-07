import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Enemigo extends Entidad implements Ataque {
    protected double vida;
    protected double daño; // cantidad de daño que realiza a barreras o Cerro Gloria
    protected String inmunidad; // Puede ser hielo, fuego o ninguno, esto reducirá el ataque sufrido por la torre correspondiente
    protected int alcanceAtaque; // Define si un enemigo puede atacar mas alla de su propio casillero.
    protected int velocidadDesplazamiento; // Determina cada cuantas iteraciones avanza un casillero
    protected int recompensaEnemigo; // Puntos de magia ganados al eliminar el enemigo
    protected ArrayList<Coordenada> camino = new ArrayList<>(); //lista de posiciones que debe recorrer cada enemigo por mapa

    public void obtenerCaminoNivel(Nivel nivel){
        //camino = nivel.getCamino();

    }
    @Override
    public void atacar(Mapa mapa) {
        // El mapa es un array de arrays (veremos que tipo de array), el enemigo consulta si en el mismo casillero hay barrera o Cerro Gloria
        // Si el enemigo tiene alcance != 1, debe poder iterar mas alla de su propio casillero para atacar.
        // Si hay barrera, el enemigo resta daño a barrera
        // Si hay cerro Gloria, el enemigo resta daño a Cerro y desaparece.
        // Ver de borrar objeto enemigo cuando ataque Cerro.
        System.out.println("Estoy atacando!!!");
    }

    public void entregarPuntosMagia(Juego juego) {
        // Cuando una torre elimina al enemigo, antes de ser eliminado el objeto, este devuelve al juego puntos de magia, para mejoras y/o compras.
        juego.aumentarPuntosMagia(recompensaEnemigo);
    }
}
