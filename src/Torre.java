import java.util.LinkedList;

public abstract class Torre implements Ataque {
    protected int costeTorre; //cantidad de puntos de magia que necesitaremos para comprar la torre
    protected  double daño;
    protected int velocidadAtaque;
    protected int alcanceAtaque;
    protected int nivelTorre;
    protected LinkedList<Enemigo> listaEnemigos; //listaEsperaEnemigos

    @Override
    public void atacar(Mapa mapa) {
        //

    }
}
